package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.presenter.view.HunterTaskDetailView
import xin.lrvik.taskcicleandroid.service.HunterTaskService
import xin.lrvik.taskcicleandroid.service.TaskService
import javax.inject.Inject

class HunterTaskDetailPresenter @Inject constructor() : BasePresenter<HunterTaskDetailView>() {
    @Inject
    lateinit var taskService: TaskService

    @Inject
    lateinit var hunterTaskService: HunterTaskService

    fun query(htId: String) {
        if (!checkNetWork()) {
            return
        }
        hunterTaskService.query(htId).execute(lifecycleProvider, mView, false) {
            mView.onTaskAndStepQueryResult(it)
        }
    }

    fun addTaskStep(id: String, step: Int, context: String, remake: String) {
        if (!checkNetWork()) {
            return
        }
        hunterTaskService.addTaskStep(id, step, context, remake).execute(lifecycleProvider, mView, false) {
            mView.onResult(it.msg)
        }
    }

    fun updateTaskStep(id: String, step: Int, context: String, remake: String) {
        if (!checkNetWork()) {
            return
        }
        hunterTaskService.updateTaskStep(id, step, context, remake).execute(lifecycleProvider, mView, false) {
            mView.onResult(it.msg)
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

    fun abandonTask(taskid: String, auditContext: String) {
        if (!checkNetWork()) {
            return
        }
        hunterTaskService.abandonTask(taskid, auditContext).execute(lifecycleProvider, mView, false) {
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

    fun disAgreeAbandon(taskid: String, auditContext: String) {
        if (!checkNetWork()) {
            return
        }
        hunterTaskService.disAgreeAbandon(taskid, auditContext).execute(lifecycleProvider, mView, false) {
            mView.onResult(it.msg)
        }
    }
}
