package xin.lrvik.taskcicleandroid.data.repository

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.baselibrary.data.net.RetrofitFactory
import xin.lrvik.taskcicleandroid.baselibrary.common.UserInfo
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
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).queryByState(state, page, size)
    }

    fun acceptTask(taskId: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).acceptTask(taskId)
    }

    fun beginTask(htId: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).beginTask(htId)
    }

    fun query(htId: String): Observable<HunterTaskAndStep> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).query(htId)
    }

    fun addTaskStep(id: String, step: Int, context: String, remake: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).addTaskStep(HunterTaskStep(id, step, context, remake))
    }

    fun updateTaskStep(id: String, step: Int, context: String, remake: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).updateTaskStep(HunterTaskStep(id, step, context, remake))
    }

    fun submitAudit(htId: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).submitAudit(htId)
    }

    fun reworkTask(htId: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).reworkTask(htId)
    }

    fun abandonTask(taskid: String, auditContext: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).abandonTask(AuditContext(taskid, auditContext))
    }

    fun forceAbandonTask(taskid: String, auditContext: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).forceAbandonTask(AuditContext(taskid, auditContext))
    }

    fun submitAdminAudit(htId: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).submitAdminAudit(htId)
    }

    fun cancelAdminAudit(htId: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).cancelAdminAudit(htId)
    }

    fun agreeAbandon(taskId: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).agreeAbandon(taskId)
    }

    fun disAgreeAbandon(taskid: String, auditContext: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).disAgreeAbandon(AuditContext(taskid, auditContext))
    }
}