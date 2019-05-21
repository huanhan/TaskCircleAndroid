package xin.lrvik.taskcicleandroid.presenter.view

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.presenter.view.ClassView
import xin.lrvik.taskcicleandroid.presenter.view.UserWithdrawView
import xin.lrvik.taskcicleandroid.service.TaskService
import xin.lrvik.taskcicleandroid.service.WalletService
import javax.inject.Inject

class WalletPresenter @Inject constructor() : BasePresenter<WalletView>() {
    @Inject
    lateinit var walletService: WalletService

    fun withdrawAdd() {
        if (!checkNetWork()) {
            return
        }
        walletService.withdrawAdd().execute(lifecycleProvider, mView, false) {
            mView.onResult(it.msg)
        }
    }

    fun payAdd() {
        if (!checkNetWork()) {
            return
        }
        walletService.payAdd().execute(lifecycleProvider, mView, false) {
            mView.onResult(it.msg)
        }
    }
}
