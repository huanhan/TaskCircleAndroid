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

    fun submitAudit(taskId: String) {
        if (!checkNetWork()) {
            return
        }
        taskService.submitAudit(taskId).execute(lifecycleProvider, mView, false) {
            mView.onResult(it.msg)
        }
    }

    fun cancelAudit(taskId: String) {
        if (!checkNetWork()) {
            return
        }
        taskService.cancelAudit(taskId).execute(lifecycleProvider, mView, false) {
            mView.onResult(it.msg)
        }
    }

    fun outTask(taskId: String) {
        if (!checkNetWork()) {
            return
        }
        taskService.outTask(taskId).execute(lifecycleProvider, mView, false) {
            mView.onResult(it.msg)
        }
    }

    fun upperTask(taskId: String) {
        if (!checkNetWork()) {
            return
        }
        taskService.upperTask(taskId).execute(lifecycleProvider, mView, false) {
            mView.onResult(it.msg)
        }
    }

    fun abandonTask(taskId: String) {
        if (!checkNetWork()) {
            return
        }
        taskService.abandonTask(taskId).execute(lifecycleProvider, mView, false) {
            mView.onResult(it.msg)
        }
    }

    fun cancelAbandon(taskId: String) {
        if (!checkNetWork()) {
            return
        }
        taskService.cancelAbandon(taskId).execute(lifecycleProvider, mView, false) {
            mView.onResult(it.msg)
        }
    }

    //todo 审核通过
    /*fun auditSuccess(taskId: String) {
        if (!checkNetWork()) {
            return
        }
        taskService.auditSuccess(taskId).execute(lifecycleProvider, mView, false) {
            mView.onResult(it)
        }
    }

    fun auditFailure(id: String, context: String) {
        if (!checkNetWork()) {
            return
        }
        taskService.auditFailure(id, context).execute(lifecycleProvider, mView, false) {
            mView.onResult(it)
        }
    }*/
}
