package xin.lrvik.taskcicleandroid.data.repository

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import xin.lrvik.taskcicleandroid.baselibrary.data.net.RetrofitFactory
import xin.lrvik.taskcicleandroid.common.UserInfo
import xin.lrvik.taskcicleandroid.data.api.HunterTaskApi
import xin.lrvik.taskcicleandroid.data.protocol.*
import javax.inject.Inject

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/27.
 *
 */
class HunterTaskRepository @Inject constructor() {

    //todo 注意修改用户id
    fun queryByState(state: String, page: Int, size: Int): Observable<Page<HunterTask>> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).queryByState(state, page, size, 13)
    }

    fun acceptTask(taskId: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).acceptTask(taskId, 13)
    }

    fun beginTask(htId: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).beginTask(htId, 13)
    }

    fun addTaskStep(step: HunterTaskStep): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).addTaskStep(step, 13)
    }

    fun updateTaskStep(step: HunterTaskStep): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).updateTaskStep(step, 13)
    }

    fun submitAudit(htId: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).submitAudit(htId, 13)
    }

    fun reworkTask(htId: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).reworkTask(htId, 13)
    }

    fun abandonTask(taskid: String, auditContext: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).abandonTask(AuditContext(taskid, auditContext), 13)
    }

    fun submitAdminAudit(htId: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).submitAdminAudit(htId, 13)
    }

    fun cancelAdminAudit(htId: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).cancelAdminAudit(htId, 13)
    }

    fun agreeAbandon(taskId: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).agreeAbandon(taskId, 13)
    }

    fun disAgreeAbandon(taskid: String, auditContext: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).disAgreeAbandon(AuditContext(taskid, auditContext), 13)
    }
}