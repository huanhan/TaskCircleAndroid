package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.presenter.view.HunterTaskStateView
import xin.lrvik.taskcicleandroid.service.HunterTaskService
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

    fun forceAbandonTask(taskid: String, auditContext: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        hunterTaskService.forceAbandonTask(taskid, auditContext).execute(lifecycleProvider, mView, true) {
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
