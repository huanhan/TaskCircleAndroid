package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.presenter.view.HunterTaskDetailView
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

    fun addTaskStep(id: String, step: Int, context: String, remake: String) {
        if (!checkNetWork()) {
            return
        }
        hunterTaskService.addTaskStep(id, step, context, remake).execute(lifecycleProvider, mView, false) {
            mView.onResult(it.msg)
        }
    }

    fun updateTaskStep(id: String, step: Int, context: String, remake: String) {
        if (!checkNetWork()) {
            return
        }
        hunterTaskService.updateTaskStep(id, step, context, remake).execute(lifecycleProvider, mView, false) {
            mView.onResult(it.msg)
        }
    }


    fun submitAudit(htId: String) {
        if (!checkNetWork()) {
            return
        }
        hunterTaskService.submitAudit(htId).execute(lifecycleProvider, mView, false) {
            mView.onResult(it.msg)
        }
    }
}
