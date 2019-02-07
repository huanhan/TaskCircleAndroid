package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.presenter.view.UserEvaluateView
import xin.lrvik.taskcicleandroid.service.CommentService
import javax.inject.Inject

class UserEvaluatePresenter @Inject constructor() : BasePresenter<UserEvaluateView>() {
    @Inject
    lateinit var commentService: CommentService

    fun evaHunter(content: String, start: Float, hunterTaskId: String) {
        if (!checkNetWork()) {
            return
        }

        commentService.evaHunter(content, start, hunterTaskId).execute(lifecycleProvider, mView, false) {
            mView.onResult(it.msg)
        }

    }

}
