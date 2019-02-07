package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.presenter.view.UserSdEvaView
import xin.lrvik.taskcicleandroid.service.CommentService
import javax.inject.Inject

class UserSdEvaPresenter @Inject constructor() : BasePresenter<UserSdEvaView>() {
    @Inject
    lateinit var commentService: CommentService

    fun evaHunterByUser(page: Int, size: Int) {
        if (!checkNetWork()) {
            return
        }

        commentService.evaHunterByUser(page, size).execute(lifecycleProvider, mView, false) {
            mView.onListResult(it)
        }

    }

}
