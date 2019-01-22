package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.presenter.view.HomeView
import xin.lrvik.taskcicleandroid.presenter.view.PostTaskView
import xin.lrvik.taskcicleandroid.service.HomeService
import xin.lrvik.taskcicleandroid.service.TaskService
import javax.inject.Inject

class PostTaskPresenter @Inject constructor() : BasePresenter<PostTaskView>() {
    @Inject
    lateinit var taskService: TaskService

    fun homeData() {
        if (!checkNetWork()) {
            return
        }

        taskService.getTaskClassData().execute(lifecycleProvider, mView, false) {
            mView.onTaskClassResult(it)
        }
    }
}
