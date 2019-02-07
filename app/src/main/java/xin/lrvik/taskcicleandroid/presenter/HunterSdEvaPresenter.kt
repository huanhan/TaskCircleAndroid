package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.presenter.view.HunterSdEvaView
import xin.lrvik.taskcicleandroid.presenter.view.UserReEvaView
import xin.lrvik.taskcicleandroid.service.CommentService
import javax.inject.Inject

class HunterSdEvaPresenter @Inject constructor() : BasePresenter<HunterSdEvaView>() {
    @Inject
    lateinit var commentService: CommentService

    fun evaUserByHunter(page: Int, size: Int) {
        if (!checkNetWork()) {
            return
        }

        commentService.evaUserByHunter(page, size).execute(lifecycleProvider, mView, false) {
            mView.onListResult(it)
        }

    }

}
