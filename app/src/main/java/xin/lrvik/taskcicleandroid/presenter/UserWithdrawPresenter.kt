package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.presenter.view.ClassView
import xin.lrvik.taskcicleandroid.presenter.view.UserWithdrawView
import xin.lrvik.taskcicleandroid.service.TaskService
import xin.lrvik.taskcicleandroid.service.WalletService
import javax.inject.Inject

class UserWithdrawPresenter @Inject constructor() : BasePresenter<UserWithdrawView>() {
    @Inject
    lateinit var walletService: WalletService

    fun userWithdrawList(page: Int, size: Int) {
        if (!checkNetWork()) {
            return
        }
        walletService.userWithdrawList(page, size).execute(lifecycleProvider, mView, false) {
            mView.onRecordResult(it)
        }
    }
}
