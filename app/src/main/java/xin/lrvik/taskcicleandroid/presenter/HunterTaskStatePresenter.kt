package xin.lrvik.taskcicleandroid.presenter

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.data.protocol.AuditContext
import xin.lrvik.taskcicleandroid.data.protocol.HunterTaskStep
import xin.lrvik.taskcicleandroid.data.protocol.Result
import xin.lrvik.taskcicleandroid.presenter.view.HunterTaskStateView
import xin.lrvik.taskcicleandroid.presenter.view.TaskStateView
import xin.lrvik.taskcicleandroid.service.HunterTaskService
import xin.lrvik.taskcicleandroid.service.TaskService
import javax.inject.Inject

class HunterTaskStatePresenter @Inject constructor() : BasePresenter<HunterTaskStateView>() {
    @Inject
    lateinit var hunterTaskService: HunterTaskService

    fun getStateTaskData(state: String, page: Int, size: Int) {
        if (!checkNetWork()) {
            return
        }
        hunterTaskService.queryByState(state, page, size).execute(lifecycleProvider, mView, false) {
            mView.onTaskStateResult(it)
        }
    }


    fun beginTask(htId: String) {
        if (!checkNetWork()) {
            return
        }
        hunterTaskService.beginTask(htId).execute(lifecycleProvider, mView, false) {
            mView.onResult(it.msg)
        }
    }

    fun addTaskStep(step: HunterTaskStep) {
        if (!checkNetWork()) {
            return
        }
        hunterTaskService.addTaskStep(step).execute(lifecycleProvider, mView, false) {
            mView.onResult(it.msg)
        }
    }

    fun updateTaskStep(step: HunterTaskStep) {
        if (!checkNetWork()) {
            return
        }
        hunterTaskService.updateTaskStep(step).execute(lifecycleProvider, mView, false) {
            mView.onResult(it.msg)
        }
    }

    fun submitAudit(htId: String) {
        if (!checkNetWork()) {
            return
        }
        hunterTaskService.submitAudit(htId).execute(lifecycleProvider, mView, false) {
            mView.onResult(it.msg)
        }
    }

    fun reworkTask(htId: String) {
        if (!checkNetWork()) {
            return
        }
        hunterTaskService.reworkTask(htId).execute(lifecycleProvider, mView, false) {
            mView.onResult(it.msg)
        }
    }

    fun abandonTask(auditContext: AuditContext) {
        if (!checkNetWork()) {
            return
        }
        hunterTaskService.abandonTask(auditContext).execute(lifecycleProvider, mView, false) {
            mView.onResult(it.msg)
        }
    }

    fun submitAdminAudit(htId: String) {
        if (!checkNetWork()) {
            return
        }
        hunterTaskService.submitAdminAudit(htId).execute(lifecycleProvider, mView, false) {
            mView.onResult(it.msg)
        }
    }

    fun cancelAdminAudit(htId: String) {
        if (!checkNetWork()) {
            return
        }
        hunterTaskService.cancelAdminAudit(htId).execute(lifecycleProvider, mView, false) {
            mView.onResult(it.msg)
        }
    }

    fun agreeAbandon(taskId: String) {
        if (!checkNetWork()) {
            return
        }
        hunterTaskService.agreeAbandon(taskId).execute(lifecycleProvider, mView, false) {
            mView.onResult(it.msg)
        }
    }

    fun disAgreeAbandon(auditContext: AuditContext) {
        if (!checkNetWork()) {
            return
        }
        hunterTaskService.disAgreeAbandon(auditContext).execute(lifecycleProvider, mView, false) {
            mView.onResult(it.msg)
        }
    }

}
