package xin.lrvik.taskcicleandroid.data.api

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*
import xin.lrvik.taskcicleandroid.data.protocol.*

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
interface HomeApi {

    @GET("app/index")
    fun home(): Observable<Home>

    @GET("app/resource/osstoken")
    fun osstoken(): Call<OssToken>

    @GET("app/message/{page}/{size}/{id}")
    fun message(
            @Path("page") page: Int,
            @Path("size") size: Int,
            @Path("id") id: Long): Observable<Page<Message>>


}