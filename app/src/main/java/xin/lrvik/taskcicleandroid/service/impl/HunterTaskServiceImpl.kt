package xin.lrvik.taskcicleandroid.service.impl

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.data.protocol.*
import xin.lrvik.taskcicleandroid.data.repository.HunterTaskRepository
import xin.lrvik.taskcicleandroid.service.HunterTaskService
import javax.inject.Inject

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
class HunterTaskServiceImpl @Inject constructor() : HunterTaskService {

    @Inject
    lateinit var hunterTaskRepository: HunterTaskRepository


    override fun queryByState(state: String,
                              page: Int,
                              size: Int): Observable<Page<HunterTask>> {
        return hunterTaskRepository.queryByState(state, page, size)
    }

    override fun acceptTask(taskId: String): Observable<Result> {
        return hunterTaskRepository.acceptTask(taskId)

    }

    override fun beginTask(htId: String): Observable<Result> {
        return hunterTaskRepository.beginTask(htId)
    }

    override fun addTaskStep(step: HunterTaskStep): Observable<Result> {
        return hunterTaskRepository.addTaskStep(step)
    }

    override fun updateTaskStep(step: HunterTaskStep): Observable<Result> {
        return hunterTaskRepository.updateTaskStep(step)
    }

    override fun submitAudit(htId: String): Observable<Result> {
        return hunterTaskRepository.submitAudit(htId)
    }

    override fun reworkTask(htId: String): Observable<Result> {
        return hunterTaskRepository.reworkTask(htId)
    }

    override fun abandonTask(taskid: String, auditContext: String): Observable<Result> {
        return hunterTaskRepository.abandonTask(taskid,auditContext)
    }

    override fun submitAdminAudit(htId: String): Observable<Result> {
        return hunterTaskRepository.submitAdminAudit(htId)
    }

    override fun cancelAdminAudit(htId: String): Observable<Result> {
        return hunterTaskRepository.cancelAdminAudit(htId)
    }

    override fun agreeAbandon(taskId: String): Observable<Result> {
        return hunterTaskRepository.agreeAbandon(taskId)
    }

    override fun disAgreeAbandon(taskid: String, auditContext: String): Observable<Result> {
        return hunterTaskRepository.disAgreeAbandon(taskid,auditContext)
    }

}