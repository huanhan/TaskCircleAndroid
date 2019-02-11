package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.presenter.view.RegistView
import xin.lrvik.taskcicleandroid.service.UserService
import javax.inject.Inject

class RegistPresenter @Inject constructor() : BasePresenter<RegistView>() {
    @Inject
    lateinit var userService: UserService

    fun register(username: String, password: String, imageCode: String) {
        if (!checkNetWork()) {
            return
        }

        mView.showLoading()
        userService.register(username, password,imageCode).execute(lifecycleProvider, mView, true) {
            mView.onResult(it.msg)
        }
    }
}
