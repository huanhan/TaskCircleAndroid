package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.presenter.view.HomeView
import xin.lrvik.taskcicleandroid.service.HomeService
import javax.inject.Inject

class HomePresenter @Inject constructor() : BasePresenter<HomeView>() {
    @Inject
    lateinit var homeService: HomeService

    fun homeData() {
        if (!checkNetWork()) {
            return
        }

        homeService.getHomeData().execute(lifecycleProvider, mView, false) {
            mView.onHomeDataResult(it)
        }
    }
}
