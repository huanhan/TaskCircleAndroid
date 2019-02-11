package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.presenter.view.ClassView
import xin.lrvik.taskcicleandroid.presenter.view.MyView
import xin.lrvik.taskcicleandroid.service.TaskService
import xin.lrvik.taskcicleandroid.service.UserService
import javax.inject.Inject

class MyPresenter @Inject constructor() : BasePresenter<MyView>() {
    @Inject
    lateinit var userService: UserService

    fun detail() {
        if (!checkNetWork()) {
            return
        }

        mView.showLoading()
        userService.detail().execute(lifecycleProvider, mView, true) {
            mView.onUserResult(it)
        }
    }
}
