package xin.lrvik.taskcicleandroid.data.api

import io.reactivex.Observable
import retrofit2.http.*
import xin.lrvik.taskcicleandroid.baselibrary.data.protocol.TokenResult
import xin.lrvik.taskcicleandroid.data.protocol.*

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
interface UserApi {

    @GET("/app/user/{id}")
    fun detail(@Path("id") id: Long): Observable<User>

    @PUT("/app/user/{id}")
    fun update(@Body info: ModifyUser, @Path("id") id: Long): Observable<Result>

    @PUT("/app/user/header/{id}")
    fun updateIcon(@Body info: ModifyUserHeader, @Path("id") id: Long): Observable<Result>

    @POST("app/user/register")
    fun register(@Body user: RegisterUser): Observable<Result>

    @GET("app/user/code/Image")
    fun validateCode(): Observable<Result>

    @GET("app/user/hunterAudit/{id}")
    fun hunterAudit(@Path("id") id: Long): Observable<HunterAudit>

    @POST("app/user/upAudit/{id}")
    fun upAudit(@Body req: HunterAuditReq, @Path("id") id: Long): Observable<Result>

    //登录
    //http://192.168.2.209:8080/authentication/form  //Content-Type:application/x-www-form-urlencoded;
    @FormUrlEncoded
    @POST("authentication/form")
    @Headers("Authorization:Basic dGNBcHA6dGNhcHBzZWNyZXQ=")
    fun login(@Field("username") username: String, @Field("password") password: String): Observable<TokenResult>

    //刷新token
    @FormUrlEncoded
    @POST("oauth/token")
    @Headers("Authorization:Basic dGNBcHA6dGNhcHBzZWNyZXQ=")
    fun refreshToken(@Field("grant_type") grant_type: String = "refresh_token",
                     @Field("refresh_token") refresh_token: String,
                     @Field("scop") scop: String = "all"): Observable<TokenResult>

}