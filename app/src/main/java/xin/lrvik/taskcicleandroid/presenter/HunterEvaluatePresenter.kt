package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.presenter.view.HunterEvaluateView
import xin.lrvik.taskcicleandroid.service.CommentService
import javax.inject.Inject

class HunterEvaluatePresenter @Inject constructor() : BasePresenter<HunterEvaluateView>() {
    @Inject
    lateinit var commentService: CommentService

    fun evaTaskAndTask(taskContext: String, taskStart: Float, hunterTaskId: String, userContext: String, userStart: Float) {
        if (!checkNetWork()) {
            return
        }

        mView.showLoading()
        commentService.evaTaskAndTask(taskContext, taskStart, hunterTaskId, userContext, userStart).execute(lifecycleProvider, mView, true) {
            mView.onResult(it.msg)
        }

    }

}
