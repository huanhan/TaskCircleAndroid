package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.presenter.view.ReleaseTaskView
import xin.lrvik.taskcicleandroid.service.TaskService
import javax.inject.Inject

class ReleaseTaskPresenter @Inject constructor() : BasePresenter<ReleaseTaskView>() {
    @Inject
    lateinit var taskService: TaskService

    fun issueTask(id: String, money: Float, peopleNumber: Int, beginTime: Long, deadline: Long, permitAbandonMinute: Int, longitude: Double, latitude: Double, address: String, taskRework: Boolean, compensate: Boolean, compensateMoney: Float) {
        if (!checkNetWork()) {
            return
        }

        mView.showLoading()
        taskService.issueTask(id, money, peopleNumber, beginTime, deadline, permitAbandonMinute, longitude, latitude, address, taskRework, compensate, compensateMoney)
                .execute(lifecycleProvider, mView, true) {
                    mView.onReleaseTaskResult(it)
                }
    }


}
