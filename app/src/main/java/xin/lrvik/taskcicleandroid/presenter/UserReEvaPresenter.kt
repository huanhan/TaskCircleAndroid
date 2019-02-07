package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.presenter.view.UserReEvaView
import xin.lrvik.taskcicleandroid.service.CommentService
import javax.inject.Inject

class UserReEvaPresenter @Inject constructor() : BasePresenter<UserReEvaView>() {
    @Inject
    lateinit var commentService: CommentService

    fun evaUserByUser(page: Int, size: Int) {
        if (!checkNetWork()) {
            return
        }

        commentService.evaUserByUser(page, size).execute(lifecycleProvider, mView, false) {
            mView.onListResult(it)
        }

    }

}
