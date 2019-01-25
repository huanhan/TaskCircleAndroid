package xin.lrvik.taskcicleandroid.data.api

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import xin.lrvik.taskcicleandroid.data.protocol.*

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
interface TaskApi {

    @GET("app/task/tcl")
    fun taskClass(): Observable<List<TaskClass>>

    //增加任务
    @POST("app/task/{id}")
    fun addTask(@Path("id") id: Long, @Body req: AddTaskReq): Observable<TaskDetail>

    //修改任务
    @POST("app/task/modify/{id}")
    fun modifyTask(@Path("id") id: Long, @Body req: ModifyTaskReq): Observable<TaskDetail>

    //发布任务
    @POST("app/task/issue/{id}")
    fun issueTask(@Path("id") id: Long, @Body req: IssueTaskReq): Observable<TaskDetail>

    //根据任务id查询任务详细
    @GET("app/task/{id}")
    fun queryTaskDetail(@Path("id") id: String): Observable<TaskDetail>

    //查询分类下所有的已发布任务 http://localhost:8080/app/task/issue/9/0/2
    @GET("app/task/issue/{classid}/{page}/{size}")
    fun queryByClassid(@Path("classid") classid: Long,
                       @Path("page") page: Int,
                       @Path("size") size: Int): Observable<Page<Task>>

    //查根据状态获取指定用户的任务列表 http://localhost:8080/app/task/user/ALL/0/5/6
    @GET("app/task/user/{state}/{page}/{size}/{id}")
    fun queryByState(@Path("state") state: String,
                     @Path("page") page: Int,
                     @Path("size") size: Int,
                     @Path("id") id: Long): Observable<Page<Task>>

    //将用户的任务提交给管理员审核
    @GET("app/task/user/upAudit/{taskId}/{id}")
    fun submitAudit(@Path("taskId") taskId: String, @Path("id") id: Long): Observable<Result>

    //取消审核
    @GET("app/task/user/di/upAudit/{taskId}/{id}")
    fun cancelAudit(@Path("taskId") taskId: String, @Path("id") id: Long): Observable<Result>

    //撤回我的任务
    @GET("app/task/out/{taskId}/{id}")
    fun outTask(@Path("taskId") taskId: String, @Path("id") id: Long): Observable<Result>

    //用户点击重新上架按钮功能
    @GET("app/task/put/{taskId}/{id}")
    fun upperTask(@Path("taskId") taskId: String, @Path("id") id: Long): Observable<Result>

    //用户点击放弃任务
    @GET("app/task/user/abandon/{taskId}/{id}")
    fun abandonTask(@Path("taskId") taskId: String, @Path("id") id: Long): Observable<Result>

    //用户点击取消放弃任务
    @GET("app/task/user/di/abandon/{taskId}/{id}")
    fun cancelAbandon(@Path("taskId") taskId: String, @Path("id") id: Long): Observable<Result>

    //用户点击审核猎刃任务通过   htId 猎刃任务id
    @GET("app/task/audit/success/{htId}/{id}")
    fun auditSuccess(@Path("htId") htId: String, @Path("id") id: Long): Observable<Result>

    //用户点击审核猎刃任务不通过   htId 猎刃任务id
    @GET("app/task/audit/failure/{id}")
    fun auditFailure(@Body req: AuditContextReq, @Path("id") id: Long): Observable<Result>


}