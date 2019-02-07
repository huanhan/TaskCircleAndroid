package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.presenter.view.TaskEvaView
import xin.lrvik.taskcicleandroid.presenter.view.UserSdEvaView
import xin.lrvik.taskcicleandroid.service.CommentService
import javax.inject.Inject

class TaskEvaPresenter @Inject constructor() : BasePresenter<TaskEvaView>() {
    @Inject
    lateinit var commentService: CommentService

    fun taskComment(taskid: String, page: Int, size: Int) {
        if (!checkNetWork()) {
            return
        }

        commentService.taskComment(taskid, page, size).execute(lifecycleProvider, mView, false) {
            mView.onListResult(it)
        }

    }

}
