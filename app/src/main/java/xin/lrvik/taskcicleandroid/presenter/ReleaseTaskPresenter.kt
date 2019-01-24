package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.presenter.view.ReleaseTaskView
import xin.lrvik.taskcicleandroid.service.TaskService
import javax.inject.Inject

class ReleaseTaskPresenter @Inject constructor() : BasePresenter<ReleaseTaskView>() {
    @Inject
    lateinit var taskService: TaskService

    fun issueTask(id: String, money: Float, peopleNumber: Int, beginTime: Long, deadline: Long, permitAbandonMinute: Int, longitude: Double, latitude: Double, taskRework: Boolean, compensate: Boolean, compensateMoney: Float) {
        if (!checkNetWork()) {
            return
        }

        taskService.issueTask(id, money, peopleNumber, beginTime, deadline, permitAbandonMinute, longitude, latitude, taskRework, compensate, compensateMoney)
                .execute(lifecycleProvider, mView, false) {
                    mView.onReleaseTaskResult(it)
                }
    }


}
