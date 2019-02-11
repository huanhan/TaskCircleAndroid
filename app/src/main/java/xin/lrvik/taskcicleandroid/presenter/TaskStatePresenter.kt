package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.presenter.view.TaskStateView
import xin.lrvik.taskcicleandroid.service.TaskService
import javax.inject.Inject

class TaskStatePresenter @Inject constructor() : BasePresenter<TaskStateView>() {
    @Inject
    lateinit var taskService: TaskService

    fun getStateTaskData(state: String, page: Int, size: Int) {
        if (!checkNetWork()) {
            return
        }
        taskService.queryByState(state, page, size).execute(lifecycleProvider, mView, false) {
            mView.onTaskStateResult(it)
        }
    }


    fun deleteTask(taskId: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        taskService.deleteTask(taskId).execute(lifecycleProvider, mView, true) {
            mView.onResult(it.msg)
        }
    }

    fun submitAudit(taskId: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        taskService.submitAudit(taskId).execute(lifecycleProvider, mView, true) {
            mView.onResult(it.msg)
        }
    }

    fun cancelAudit(taskId: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        taskService.cancelAudit(taskId).execute(lifecycleProvider, mView, true) {
            mView.onResult(it.msg)
        }
    }

    fun outTask(taskId: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        taskService.outTask(taskId).execute(lifecycleProvider, mView, true) {
            mView.onResult(it.msg)
        }
    }

    fun upperTask(taskId: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        taskService.upperTask(taskId).execute(lifecycleProvider, mView, true) {
            mView.onResult(it.msg)
        }
    }

    fun abandonTask(taskId: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        taskService.abandonTask(taskId).execute(lifecycleProvider, mView, true) {
            mView.onResult(it.msg)
        }
    }

    fun cancelAbandon(taskId: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        taskService.cancelAbandon(taskId).execute(lifecycleProvider, mView, true) {
            mView.onResult(it.msg)
        }
    }
}
