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
        mView.showLoading()
        hunterTaskService.query(htId).execute(lifecycleProvider, mView, true) {
            mView.onTaskAndStepQueryResult(it)
        }
    }

    fun addTaskStep(id: String, step: Int, context: String, remake: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        hunterTaskService.addTaskStep(id, step, context, remake).execute(lifecycleProvider, mView, true) {
            mView.onResult(it.msg)
        }
    }

    fun updateTaskStep(id: String, step: Int, context: String, remake: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        hunterTaskService.updateTaskStep(id, step, context, remake).execute(lifecycleProvider, mView, true) {
            mView.onResult(it.msg)
        }
    }


    fun beginTask(htId: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        hunterTaskService.beginTask(htId).execute(lifecycleProvider, mView, true) {
            mView.onResult(it.msg)
        }
    }

    fun submitAudit(htId: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        hunterTaskService.submitAudit(htId).execute(lifecycleProvider, mView, true) {
            mView.onResult(it.msg)
        }
    }

    fun reworkTask(htId: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        hunterTaskService.reworkTask(htId).execute(lifecycleProvider, mView, true) {
            mView.onResult(it.msg)
        }
    }

    fun abandonTask(taskid: String, auditContext: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        hunterTaskService.abandonTask(taskid, auditContext).execute(lifecycleProvider, mView, true) {
            mView.onResult(it.msg)
        }
    }

    fun submitAdminAudit(htId: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        hunterTaskService.submitAdminAudit(htId).execute(lifecycleProvider, mView, true) {
            mView.onResult(it.msg)
        }
    }

    fun cancelAdminAudit(htId: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        hunterTaskService.cancelAdminAudit(htId).execute(lifecycleProvider, mView, true) {
            mView.onResult(it.msg)
        }
    }

    fun agreeAbandon(taskId: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        hunterTaskService.agreeAbandon(taskId).execute(lifecycleProvider, mView, true) {
            mView.onResult(it.msg)
        }
    }

    fun disAgreeAbandon(taskid: String, auditContext: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        hunterTaskService.disAgreeAbandon(taskid, auditContext).execute(lifecycleProvider, mView, true) {
            mView.onResult(it.msg)
        }
    }
}
