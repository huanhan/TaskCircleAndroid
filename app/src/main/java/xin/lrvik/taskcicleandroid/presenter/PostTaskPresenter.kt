package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.data.protocol.TaskStep
import xin.lrvik.taskcicleandroid.presenter.view.PostTaskView
import xin.lrvik.taskcicleandroid.service.TaskService
import javax.inject.Inject

class PostTaskPresenter @Inject constructor() : BasePresenter<PostTaskView>() {
    @Inject
    lateinit var taskService: TaskService

    fun classData() {
        if (!checkNetWork()) {
            return
        }

        taskService.getTaskClassData().execute(lifecycleProvider, mView, false) {
            mView.onTaskClassResult(it)
        }
    }

    fun addTask(classs: List<Long>, text: String, contentText: String, data: MutableList<TaskStep>) {
        if (!checkNetWork()) {
            return
        }
        taskService.addTask(classs,text,contentText,data).execute(lifecycleProvider, mView, false) {
            mView.onAddTaskResult(it)
        }
    }
}
