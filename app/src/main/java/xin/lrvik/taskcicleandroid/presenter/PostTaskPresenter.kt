package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.data.protocol.TaskStep
import xin.lrvik.taskcicleandroid.presenter.view.PostTaskView
import xin.lrvik.taskcicleandroid.service.HunterTaskService
import xin.lrvik.taskcicleandroid.service.TaskService
import javax.inject.Inject

class PostTaskPresenter @Inject constructor() : BasePresenter<PostTaskView>() {
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

    fun addTask(classs: List<Long>, text: String, contentText: String, data: MutableList<TaskStep>) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        taskService.addTask(classs, text, contentText, data).execute(lifecycleProvider, mView, true) {
            mView.onResult(it.msg)
        }
    }

    fun queryTaskDetail(id: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        taskService.queryTaskDetail(id).execute(lifecycleProvider, mView, true) {
            mView.onTaskDetailResult(it)
        }
    }

    fun modifyTask(id: String, classs: List<Long>, text: String, contentText: String, data: MutableList<TaskStep>) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        taskService.modifyTask(id, classs, text, contentText, data).execute(lifecycleProvider, mView, true) {
            mView.onResult(it.msg)
        }
    }

    fun deleteTask(taskId: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        taskService.deleteTask(taskId).execute(lifecycleProvider, mView, true) {
            mView.onResult(it.msg)
        }
    }

    fun submitAudit(taskId: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        taskService.submitAudit(taskId).execute(lifecycleProvider, mView, true) {
            mView.onResult(it.msg)
        }
    }

    fun cancelAudit(taskId: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        taskService.cancelAudit(taskId).execute(lifecycleProvider, mView, true) {
            mView.onResult(it.msg)
        }
    }
}
