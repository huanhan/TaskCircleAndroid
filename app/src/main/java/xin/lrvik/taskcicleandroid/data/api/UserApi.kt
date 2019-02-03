package xin.lrvik.taskcicleandroid.data.api

import io.reactivex.Observable
import retrofit2.http.*
import xin.lrvik.taskcicleandroid.data.protocol.*

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
interface UserApi {

    @GET("/app/user/{id}")
    fun detail(@Path("id") id: Long): Observable<User>

    @PUT("/app/user/{id}")
    fun update(@Body info: ModifyUser,@Path("id") id: Long): Observable<Result>

    @PUT("/app/user/header/{id}")
    fun updateIcon(@Body info: ModifyUserHeader,@Path("id") id: Long): Observable<Result>

    @POST("/app/user/upAudit/{id}")
    fun upAudit(@Path("id") id: Long): Observable<Result>

    @POST("app/user/register")
    fun register(@Body user: RegisterUser): Observable<Result>

    @GET("app/user/code/Image")
    fun validateCode(): Observable<Result>


}