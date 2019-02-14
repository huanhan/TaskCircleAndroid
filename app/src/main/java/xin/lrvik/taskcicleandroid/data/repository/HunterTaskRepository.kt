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
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).queryByState(state, page, size, UserInfo.userId)
    }

    fun acceptTask(taskId: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).acceptTask(taskId, UserInfo.userId)
    }

    fun beginTask(htId: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).beginTask(htId, UserInfo.userId)
    }

    fun query(htId: String): Observable<HunterTaskAndStep> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).query(htId, UserInfo.userId)
    }

    fun addTaskStep(id: String, step: Int, context: String, remake: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).addTaskStep(HunterTaskStep(id, step, context, remake), UserInfo.userId)
    }

    fun updateTaskStep(id: String, step: Int, context: String, remake: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).updateTaskStep(HunterTaskStep(id, step, context, remake), UserInfo.userId)
    }

    fun submitAudit(htId: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).submitAudit(htId, UserInfo.userId)
    }

    fun reworkTask(htId: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).reworkTask(htId, UserInfo.userId)
    }

    fun abandonTask(taskid: String, auditContext: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).abandonTask(AuditContext(taskid, auditContext), UserInfo.userId)
    }

    fun forceAbandonTask(taskid: String, auditContext: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).forceAbandonTask(AuditContext(taskid, auditContext), UserInfo.userId)
    }

    fun submitAdminAudit(htId: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).submitAdminAudit(htId, UserInfo.userId)
    }

    fun cancelAdminAudit(htId: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).cancelAdminAudit(htId, UserInfo.userId)
    }

    fun agreeAbandon(taskId: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).agreeAbandon(taskId, UserInfo.userId)
    }

    fun disAgreeAbandon(taskid: String, auditContext: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).disAgreeAbandon(AuditContext(taskid, auditContext), UserInfo.userId)
    }
}