package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.presenter.view.HunterTaskStateView
import xin.lrvik.taskcicleandroid.presenter.view.TaskStateView
import xin.lrvik.taskcicleandroid.service.HunterTaskService
import xin.lrvik.taskcicleandroid.service.TaskService
import javax.inject.Inject

class HunterTaskStatePresenter @Inject constructor() : BasePresenter<HunterTaskStateView>() {
    @Inject
    lateinit var hunterTaskService: HunterTaskService

    fun getStateTaskData(state: String, page: Int, size: Int) {
        if (!checkNetWork()) {
            return
        }
        hunterTaskService.queryByState(state, page, size).execute(lifecycleProvider, mView, false) {
            mView.onTaskStateResult(it)
        }
    }


}
