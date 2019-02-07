package xin.lrvik.taskcicleandroid.data.api

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import xin.lrvik.taskcicleandroid.data.protocol.*

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/2/7.
 *
 */
interface CommentApi {

    //用户获取猎刃评论用户列表
    @GET("app/comment/evaUserByUser/{page}/{size}/{id}")
    fun evaUserByUser(@Path("page") page: Int,
                   @Path("size") size: Int,
                   @Path("id") id: Long): Observable<Page<CommentUser>>

    //用户获取用户评论猎刃列表
    @GET("app/comment/evaHunterByUser/{page}/{size}/{id}")
    fun evaHunterByUser(@Path("page") page: Int,
                   @Path("size") size: Int,
                   @Path("id") id: Long): Observable<Page<CommentHunter>>

    //猎刃获取猎刃评论用户列表
    @GET("app/comment/evaUserByHunter/{page}/{size}/{id}")
    fun evaUserByHunter(@Path("page") page: Int,
                   @Path("size") size: Int,
                   @Path("id") id: Long): Observable<Page<CommentUser>>

    //猎刃获取用户评论猎刃列表
    @GET("app/comment/evaHunterByHunter/{page}/{size}/{id}")
    fun evaHunterByHunter(@Path("page") page: Int,
                   @Path("size") size: Int,
                   @Path("id") id: Long): Observable<Page<CommentHunter>>

    //猎刃评价用户和任务
    @POST("app/comment/taskAndTask/{id}")
    fun evaTaskAndTask(@Body req: HunterCommentReq, @Path("id") id: Long): Observable<Result>

    //用户评价猎刃
    @POST("app/comment/hunter/{id}")
    fun evaHunter(@Body req: UserCommentReq, @Path("id") id: Long): Observable<Result>
}