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
    @GET("app/ht/task/{state}/{page}/{size}/{id}")
    fun queryByState(@Path("state") state: String,
                     @Path("page") page: Int,
                     @Path("size") size: Int,
                     @Path("id") id: Long): Observable<Page<HunterTask>>

    //猎刃接取任务
    @GET("app/ht/accept/{taskId}/{id}")
    fun acceptTask(@Path("taskId") taskId: String, @Path("id") id: Long): Observable<Result>

    //猎刃点击按钮开始任务
    @GET("app/ht/begin/{htId}/{id}")
    fun beginTask(@Path("htId") htId: String, @Path("id") id: Long): Observable<Result>

    //查询猎刃任务详细
    @GET("app/ht/query/{htId}/{id}")
    fun query(@Path("htId") htId: String, @Path("id") id: Long): Observable<HunterTaskAndStep>

    //添加猎刃的任务步骤
    @POST("app/ht/add/step/{id}")
    fun addTaskStep(@Body step: HunterTaskStep, @Path("id") id: Long): Observable<Result>

    //修改猎刃的任务步骤
    @POST("app/ht/update/step/{id}")
    fun updateTaskStep(@Body step: HunterTaskStep, @Path("id") id: Long): Observable<Result>

    //猎刃将任务提交用户审核
    @GET("app/ht/user/audit/{htId}/{id}")
    fun submitAudit(@Path("htId") htId: String, @Path("id") id: Long): Observable<Result>

    //猎刃点击重做任务
    @GET("app/ht/rework/{htId}/{id}")
    fun reworkTask(@Path("htId") htId: String, @Path("id") id: Long): Observable<Result>

    //猎刃点击放弃任务
    @POST("app/ht/abandon/{id}")
    fun abandonTask(@Body auditContext: AuditContext, @Path("id") id: Long): Observable<Result>

    //猎刃点击提交管理员审核
    @GET("app/ht/admin/audit/{htId}/{id}")
    fun submitAdminAudit(@Path("htId") htId: String, @Path("id") id: Long): Observable<Result>

    //猎刃点击取消提交管理员审核
    @GET("app/ht/admin/di/audit/{htId}/{id}")
    fun cancelAdminAudit(@Path("htId") htId: String, @Path("id") id: Long): Observable<Result>

    //猎刃同意用户放弃任务
    @GET("app/ht/abandon/success/{taskId}/{id}")
    fun agreeAbandon(@Path("taskId") taskId: String, @Path("id") id: Long): Observable<Result>

    //猎刃点击用户的放弃申请不通过
    @POST("app/ht/abandon/failure/{id}")
    fun disAgreeAbandon(@Body auditContext: AuditContext, @Path("id") id: Long): Observable<Result>


}