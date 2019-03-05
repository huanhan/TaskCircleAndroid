package xin.lrvik.taskcicleandroid.data.api

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import xin.lrvik.taskcicleandroid.data.protocol.*

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/27.
 *
 */
interface HunterTaskApi {

    //查根据状态获取指定用户的任务列表 http://192.168.2.209:8080/app/task/hunter/ALL/0/15/6
    @GET("app/ht/task/{state}/{page}/{size}")
    fun queryByState(@Path("state") state: String,
                     @Path("page") page: Int,
                     @Path("size") size: Int): Observable<Page<HunterTask>>

    //猎刃接取任务
    @GET("app/ht/accept/{hunterTaskId}")
    fun acceptTask(@Path("hunterTaskId") taskId: String): Observable<Result>

    //猎刃点击按钮开始任务
    @GET("app/ht/begin/{htId}")
    fun beginTask(@Path("htId") htId: String): Observable<Result>

    //查询猎刃任务详细
    @GET("app/ht/query/{htId}")
    fun query(@Path("htId") htId: String): Observable<HunterTaskAndStep>

    //添加猎刃的任务步骤
    @POST("app/ht/add/step")
    fun addTaskStep(@Body step: HunterTaskStep): Observable<Result>

    //修改猎刃的任务步骤
    @POST("app/ht/update/step")
    fun updateTaskStep(@Body step: HunterTaskStep): Observable<Result>

    //猎刃将任务提交用户审核
    @GET("app/ht/user/audit/{htId}")
    fun submitAudit(@Path("htId") htId: String): Observable<Result>

    //猎刃点击重做任务
    @GET("app/ht/rework/{htId}")
    fun reworkTask(@Path("htId") htId: String): Observable<Result>

    //猎刃点击放弃任务
    @POST("app/ht/abandon")
    fun abandonTask(@Body auditContext: AuditContext): Observable<Result>

    //猎刃点击强制放弃任务
    @POST("app/ht/forceAbandon")
    fun forceAbandonTask(@Body auditContext: AuditContext): Observable<Result>

    //猎刃点击提交管理员审核
    @GET("app/ht/admin/audit/{htId}")
    fun submitAdminAudit(@Path("htId") htId: String): Observable<Result>

    //猎刃点击取消提交管理员审核
    @GET("app/ht/admin/di/audit/{htId}")
    fun cancelAdminAudit(@Path("htId") htId: String): Observable<Result>

    //猎刃同意用户放弃任务
    @GET("app/ht/abandon/success/{hunterTaskId}")
    fun agreeAbandon(@Path("hunterTaskId") taskId: String): Observable<Result>

    //猎刃点击用户的放弃申请不通过
    @POST("app/ht/abandon/failure")
    fun disAgreeAbandon(@Body auditContext: AuditContext): Observable<Result>


}