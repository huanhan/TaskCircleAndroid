package xin.lrvik.taskcicleandroid.baselibrary.data.net

import com.google.gson.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST
import xin.lrvik.taskcicleandroid.baselibrary.common.BaseConstant
import xin.lrvik.taskcicleandroid.baselibrary.common.UserInfo
import xin.lrvik.taskcicleandroid.baselibrary.data.protocol.TokenResult
import xin.lrvik.taskcicleandroid.baselibrary.rx.BaseException
import xin.lrvik.taskcicleandroid.baselibrary.util.DeviceUtil
import xin.lrvik.taskcicleandroid.baselibrary.utils.AppPrefsUtils
import xin.lrvik.taskcicleandroid.baselibrary.utils.DateUtils
import java.lang.Exception
import java.sql.Timestamp
import java.util.concurrent.TimeUnit


class RetrofitFactory private constructor() {
    companion object {
        val instance: RetrofitFactory by lazy {
            RetrofitFactory()
        }
    }

    private val retrofitGson: Retrofit
    private val interceptor: Interceptor

    init {

        val builder = GsonBuilder()
        builder.registerTypeAdapter(Timestamp::class.java, JsonDeserializer { json, typeOfT, context -> Timestamp(json.asJsonPrimitive.asLong) })
        var gson = builder.create()

        //增加过滤器，给每个请求增加请求头
        interceptor = Interceptor { chain ->
            val requestBuilder = chain.request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("charset", "utf-8")
                    .addHeader("deviceId", DeviceUtil.getDeviceId())
            if (!UserInfo.access_token.isNullOrEmpty()) {
                requestBuilder.addHeader("Authorization", "bearer ${UserInfo.access_token}")
            }

            var request = requestBuilder.build()

            var response = chain.proceed(request)
            response?.let {
                //请求操作失败异常处理
                if (it.code() == 401) {
                    //刷新令牌重试
                    UserInfo.access_token = ""
                    var tokenResult = instance.create(TokenApi::class.java)
                            .refreshToken(refresh_token = UserInfo.refresh_token)
                            .execute().body()

                    tokenResult?.let {
                        //设置refreshtoken失效时间6天
                        tokenResult.expires_out = (DateUtils.curTime + (1000 * 60 * 60 * 24 * 6))
                        AppPrefsUtils.putString("token", Gson().toJson(tokenResult))

                        UserInfo.userId = tokenResult.userId.toLong()
                        UserInfo.access_token = tokenResult.access_token
                        UserInfo.refresh_token = tokenResult.refresh_token

                        var newRequest = chain.request().newBuilder().addHeader("Authorization", "bearer ${UserInfo.access_token}").build()
                        return@Interceptor chain.proceed(newRequest)
                    }


                } else if (it.code() != 200) {

                    var errMes = ""
                    var result = it.body()?.string().toString()

                    try {
                        var errArray = JsonParser().parse(result).asJsonObject["messages"].asJsonArray
                        for ((index, ele) in errArray.withIndex()) {
                            errMes += ele.asJsonObject["message"].asString + if (errArray.size() - 1 == index) "" else ","
                        }
                    } catch (e: Exception) {
                        throw BaseException(it.code(), result)
                    }
                    throw BaseException(it.code(), errMes)
                } else {

                }
            }
            response

        }


        //创建retrofit，设置client，增加转换工厂，以及RX适配器
        retrofitGson = Retrofit.Builder()
                .baseUrl(BaseConstant.SERVICE_ADDRESS)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))//支持json解析
                .client(initClient())
                .build()

    }

    //初始化连接工具，设置日志拦截器，自定义拦截器（增加请求头），连接，读取超时
    private fun initClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(initLogInterceptor())
                .addInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()
    }

    //初始化日志拦截器，设置日志等级
    private fun initLogInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    //获取获取请求服务
    fun <T> create(service: Class<T>): T = retrofitGson.create(service)

}

interface TokenApi {
    //刷新token
    @FormUrlEncoded
    @POST("oauth/token")
    @Headers("Authorization:Basic dGNBcHA6dGNhcHBzZWNyZXQ=")
    fun refreshToken(@Field("grant_type") grant_type: String = "refresh_token",
                     @Field("refresh_token") refresh_token: String,
                     @Field("scop") scop: String = "all"): Call<TokenResult>
}