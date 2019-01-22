package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.presenter.view.TaskClassView
import xin.lrvik.taskcicleandroid.service.TaskService
import javax.inject.Inject

class TaskClassPresenter @Inject constructor() : BasePresenter<TaskClassView>() {
    @Inject
    lateinit var taskService: TaskService

    fun queryByClassid(classsId: Long,
                       page: Int,
                       size: Int) {
        if (!checkNetWork()) {
            return
        }

        taskService.queryByClassid(classsId, page, size).execute(lifecycleProvider, mView, false) {
            mView.onTaskListResult(it)
        }
    }
}
