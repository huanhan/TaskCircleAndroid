package xin.lrvik.taskcicleandroid.service

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.baselibrary.data.net.RetrofitFactory
import xin.lrvik.taskcicleandroid.data.api.HunterTaskApi
import xin.lrvik.taskcicleandroid.data.protocol.*

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
interface HunterTaskService {

    //根据状态获取任务列表
    fun queryByState(state: String, page: Int, size: Int): Observable<Page<HunterTask>>

    fun acceptTask(taskId: String): Observable<Result>
    fun beginTask(htId: String): Observable<Result>
    fun addTaskStep(step: HunterTaskStep): Observable<Result>
    fun updateTaskStep(step: HunterTaskStep): Observable<Result>
    fun submitAudit(htId: String): Observable<Result>
    fun reworkTask(htId: String): Observable<Result>
    fun abandonTask(auditContext: AuditContext): Observable<Result>
    fun submitAdminAudit(htId: String): Observable<Result>
    fun cancelAdminAudit(htId: String): Observable<Result>
    fun agreeAbandon(taskId: String): Observable<Result>
    fun disAgreeAbandon(auditContext: AuditContext): Observable<Result>
}