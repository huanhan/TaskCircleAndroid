package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.data.protocol.TaskStep
import xin.lrvik.taskcicleandroid.presenter.view.PostTaskView
import xin.lrvik.taskcicleandroid.presenter.view.TaskDetailView
import xin.lrvik.taskcicleandroid.service.HunterTaskService
import xin.lrvik.taskcicleandroid.service.TaskService
import javax.inject.Inject

class TaskDetailPresenter @Inject constructor() : BasePresenter<TaskDetailView>() {
    @Inject
    lateinit var taskService: TaskService

    @Inject
    lateinit var hunterTaskService: HunterTaskService

    fun queryTaskDetail(id: String) {
        if (!checkNetWork()) {
            return
        }
        taskService.queryTaskDetail(id).execute(lifecycleProvider, mView, false) {
            mView.onTaskDetailResult(it)
        }
    }

    fun acceptTask(taskId: String) {
        if (!checkNetWork()) {
            return
        }
        hunterTaskService.acceptTask(taskId).execute(lifecycleProvider, mView, false) {
            mView.onResult(it.msg)
        }
    }
}
