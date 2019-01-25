package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.presenter.view.HomeView
import xin.lrvik.taskcicleandroid.presenter.view.TaskStateView
import xin.lrvik.taskcicleandroid.service.HomeService
import xin.lrvik.taskcicleandroid.service.TaskService
import javax.inject.Inject

class TaskStatePresenter @Inject constructor() : BasePresenter<TaskStateView>() {
    @Inject
    lateinit var taskService: TaskService

    fun getStateTaskData(state: String, page: Int, size: Int) {
        if (!checkNetWork()) {
            return
        }
        taskService.queryByState(state,page,size).execute(lifecycleProvider, mView, false) {
            mView.onTaskStateResult(it)
        }
    }
}
