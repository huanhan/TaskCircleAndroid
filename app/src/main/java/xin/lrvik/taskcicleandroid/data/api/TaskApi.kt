package xin.lrvik.taskcicleandroid.data.api

import io.reactivex.Observable
import retrofit2.http.*
import xin.lrvik.taskcicleandroid.data.protocol.*

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
interface TaskApi {

    @GET("app/task/tcl")
    fun taskClass(): Observable<List<TaskClass>>

    //增加任务
    @POST("app/task")
    fun addTask(@Body req: AddTaskReq): Observable<Result>

    //删除任务
    @DELETE("app/task/remove/{taskId}")
    fun deleteTask(@Path("taskId") taskId: String): Observable<Result>

    //修改任务
    @POST("app/task/modify")
    fun modifyTask(@Body req: ModifyTaskReq): Observable<Result>

    //发布任务
    @POST("app/task/issue")
    fun issueTask(@Body req: IssueTaskReq): Observable<TaskDetail>

    //根据任务id查询任务详细
    @GET("app/task/{taskId}")
    fun queryTaskDetail(@Path("taskId") taskId: String): Observable<TaskDetail>

    //查询分类下所有的已发布任务 http://localhost:8080/app/task/issue/9/0/2
    @GET("app/task/issue/{classid}/{page}/{size}")
    fun queryByClassid(@Path("classid") classid: Long,
                       @Path("page") page: Int,
                       @Path("size") size: Int): Observable<Page<Task>>

    //查根据状态获取指定用户的任务列表 http://localhost:8080/app/task/user/ALL/0/5/6
    @GET("app/task/user/{state}/{page}/{size}")
    fun queryByState(@Path("state") state: String,
                     @Path("page") page: Int,
                     @Path("size") size: Int): Observable<Page<Task>>

    //搜索
    @GET("app/task/search/{key}/{page}/{size}")
    fun search(@Path("key") key: String,
               @Path("page") page: Int,
               @Path("size") size: Int): Observable<Page<Task>>

    //将用户的任务提交给管理员审核
    @GET("app/task/user/upAudit/{taskId}")
    fun submitAudit(@Path("taskId") taskId: String): Observable<Result>

    //取消审核
    @GET("app/task/user/di/upAudit/{taskId}")
    fun cancelAudit(@Path("taskId") taskId: String): Observable<Result>

    //撤回我的任务
    @GET("app/task/out/{taskId}")
    fun outTask(@Path("taskId") taskId: String): Observable<Result>

    //用户点击重新上架按钮功能
    @GET("app/task/put/{taskId}")
    fun upperTask(@Path("taskId") taskId: String): Observable<Result>

    //用户点击放弃任务
    @GET("app/task/user/abandon/{taskId}")
    fun abandonTask(@Path("taskId") taskId: String): Observable<Result>

    //用户点击取消放弃任务
    @GET("app/task/user/di/abandon/{taskId}")
    fun cancelAbandon(@Path("taskId") taskId: String): Observable<Result>

    //用户点击审核猎刃任务通过   htId 猎刃任务id
    @GET("app/task/audit/success/{htId}")
    fun auditSuccess(@Path("htId") htId: String): Observable<Result>

    //用户点击审核猎刃任务不通过   htId 猎刃任务id
    @POST("app/task/audit/failure")
    fun auditFailure(@Body req: AuditContextReq): Observable<Result>

    //用户点击审核猎刃任务放弃通过   htId 猎刃任务id
    @GET("app/task/abandon/success/{htId}")
    fun abandonPass(@Path("htId") htId: String): Observable<Result>

    //用户点击猎刃任务放弃申请不通过   htId 猎刃任务id
    @POST("app/task/abandon/failure")
    fun abandonNotPass(@Body req: AuditContextReq): Observable<Result>

    //根据任务编号获取猎刃执行者列表
    @GET("app/task/hunterTask/{htId}/{page}/{size}")
    fun hunterRunning(@Path("htId") taskid: String,
                      @Path("page") page: Int,
                      @Path("size") size: Int): Observable<Page<HunterTask>>

    //用户点击审核猎刃任务放弃通过   htId 猎刃任务id
    @GET("app/task/user/abandonHunterTask/{htId}")
    fun abandonHunterTask(@Path("htId") htId: String): Observable<Result>

    //用户点击审核猎刃任务放弃通过   htId 猎刃任务id
    @GET("app/task/user/forceAbandonHunterTask/{htId}")
    fun forceAbandonHunterTask(@Path("htId") htId: String): Observable<Result>

}