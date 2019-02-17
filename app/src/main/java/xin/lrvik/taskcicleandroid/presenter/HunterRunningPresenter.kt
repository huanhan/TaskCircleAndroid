package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.presenter.view.HunterRunningView
import xin.lrvik.taskcicleandroid.service.TaskService
import javax.inject.Inject

class HunterRunningPresenter @Inject constructor() : BasePresenter<HunterRunningView>() {
    @Inject
    lateinit var taskService: TaskService

    fun hunterRunning(taskid: String, page: Int, size: Int) {
        if (!checkNetWork()) {
            return
        }
        taskService.hunterRunning(taskid, page, size).execute(lifecycleProvider, mView, false) {
            mView.onTaskRunningResult(it)
        }
    }


    fun auditSuccess(htId: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        taskService.auditSuccess(htId).execute(lifecycleProvider, mView, true) {
            mView.onResult(it.msg)
        }
    }

    fun auditFailure(id: String, context: String) {
        if (!checkNetWork()) {
            return
        }
        taskService.auditFailure(id, context).execute(lifecycleProvider, mView, true) {
            mView.onResult(it.msg)
        }
    }

    fun abandonPass(htId: String) {
        if (!checkNetWork()) {
            return
        }
        taskService.abandonPass(htId).execute(lifecycleProvider, mView, true) {
            mView.onResult(it.msg)
        }
    }

    fun abandonNotPass(htId: String, context: String) {
        if (!checkNetWork()) {
            return
        }
        taskService.abandonNotPass(htId, context).execute(lifecycleProvider, mView, true) {
            mView.onResult(it.msg)
        }
    }

    fun abandonHunterTask(htId: String) {
        if (!checkNetWork()) {
            return
        }
        taskService.abandonHunterTask(htId).execute(lifecycleProvider, mView, true) {
            mView.onResult(it.msg)
        }
    }

    fun forceAbandonHunterTask(htId: String) {
        if (!checkNetWork()) {
            return
        }
        taskService.forceAbandonHunterTask(htId).execute(lifecycleProvider, mView, true) {
            mView.onResult(it.msg)
        }
    }
}
