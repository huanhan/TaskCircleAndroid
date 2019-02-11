package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.presenter.view.LoginView
import xin.lrvik.taskcicleandroid.service.UserService
import javax.inject.Inject

class LoginPresenter @Inject constructor() : BasePresenter<LoginView>() {
    @Inject
    lateinit var userService: UserService

    fun login(username: String, password: String) {
        if (!checkNetWork()) {
            return
        }

        mView.showLoading()
        userService.login(username, password).execute(lifecycleProvider, mView, true) {
            mView.onResult(it)
        }
    }
}
