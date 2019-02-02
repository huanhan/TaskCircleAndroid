package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.presenter.view.ClassView
import xin.lrvik.taskcicleandroid.presenter.view.MesView
import xin.lrvik.taskcicleandroid.service.HomeService
import xin.lrvik.taskcicleandroid.service.TaskService
import javax.inject.Inject

class MesPresenter @Inject constructor() : BasePresenter<MesView>() {
    @Inject
    lateinit var homeService: HomeService

    fun message(page: Int, size: Int) {
        if (!checkNetWork()) {
            return
        }

        homeService.message(page, size).execute(lifecycleProvider, mView, false) {
            mView.onMesResult(it)
        }
    }
}
