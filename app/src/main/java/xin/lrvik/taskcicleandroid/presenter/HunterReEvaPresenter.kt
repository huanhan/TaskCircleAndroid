package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.presenter.view.HunterReEvaView
import xin.lrvik.taskcicleandroid.service.CommentService
import javax.inject.Inject

class HunterReEvaPresenter @Inject constructor() : BasePresenter<HunterReEvaView>() {
    @Inject
    lateinit var commentService: CommentService

    fun evaHunterByHunter(page: Int, size: Int) {
        if (!checkNetWork()) {
            return
        }

        commentService.evaHunterByHunter(page, size).execute(lifecycleProvider, mView, false) {
            mView.onListResult(it)
        }

    }

}
