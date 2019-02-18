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
    @GET("app/comment/evaUserByUser/{page}/{size}")
    fun evaUserByUser(@Path("page") page: Int,
                      @Path("size") size: Int): Observable<Page<CommentUser>>

    //用户获取用户评论猎刃列表
    @GET("app/comment/evaHunterByUser/{page}/{size}")
    fun evaHunterByUser(@Path("page") page: Int,
                        @Path("size") size: Int): Observable<Page<CommentHunter>>

    //猎刃获取猎刃评论用户列表
    @GET("app/comment/evaUserByHunter/{page}/{size}")
    fun evaUserByHunter(@Path("page") page: Int,
                        @Path("size") size: Int): Observable<Page<CommentUser>>

    //猎刃获取用户评论猎刃列表
    @GET("app/comment/evaHunterByHunter/{page}/{size}")
    fun evaHunterByHunter(@Path("page") page: Int,
                          @Path("size") size: Int): Observable<Page<CommentHunter>>

    //根据任务id获取任务评价
    @GET("app/comment/task/{huntertaskid}/{page}/{size}")
    fun taskComment(@Path("huntertaskid") taskid: String,
                    @Path("page") page: Int,
                    @Path("size") size: Int): Observable<Page<CommentTask>>

    //猎刃评价用户和任务
    @POST("app/comment/taskAndTask")
    fun evaTaskAndTask(@Body req: HunterCommentReq): Observable<Result>

    //用户评价猎刃
    @POST("app/comment/hunter")
    fun evaHunter(@Body req: UserCommentReq): Observable<Result>
}