package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.data.protocol.TaskStep
import xin.lrvik.taskcicleandroid.presenter.view.HunterTaskDetailView
import xin.lrvik.taskcicleandroid.presenter.view.PostTaskView
import xin.lrvik.taskcicleandroid.presenter.view.TaskDetailView
import xin.lrvik.taskcicleandroid.service.HunterTaskService
import xin.lrvik.taskcicleandroid.service.TaskService
import javax.inject.Inject

class HunterTaskDetailPresenter @Inject constructor() : BasePresenter<HunterTaskDetailView>() {
    @Inject
    lateinit var taskService: TaskService

    @Inject
    lateinit var hunterTaskService: HunterTaskService

    fun query(htId: String) {
        if (!checkNetWork()) {
            return
        }
        hunterTaskService.query(htId).execute(lifecycleProvider, mView, false) {
            mView.onTaskAndStepQueryResult(it)
        }
    }
}
