package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.presenter.view.ClassView
import xin.lrvik.taskcicleandroid.presenter.view.TransferView
import xin.lrvik.taskcicleandroid.presenter.view.UserWithdrawView
import xin.lrvik.taskcicleandroid.service.TaskService
import xin.lrvik.taskcicleandroid.service.WalletService
import javax.inject.Inject

class TransferPresenter @Inject constructor() : BasePresenter<TransferView>() {
    @Inject
    lateinit var walletService: WalletService

    fun transferList(page: Int, size: Int) {
        if (!checkNetWork()) {
            return
        }
        walletService.transferList(page, size).execute(lifecycleProvider, mView, false) {
            mView.onRecordResult(it)
        }
    }
}
