package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.presenter.view.ClassView
import xin.lrvik.taskcicleandroid.service.TaskService
import javax.inject.Inject

class ClassPresenter @Inject constructor() : BasePresenter<ClassView>() {
    @Inject
    lateinit var taskService: TaskService

    fun classData() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        taskService.getTaskClassData().execute(lifecycleProvider, mView, true) {
            mView.onTaskClassResult(it)
        }
    }
}
