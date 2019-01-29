package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.presenter.view.CashPledgeView
import xin.lrvik.taskcicleandroid.presenter.view.ClassView
import xin.lrvik.taskcicleandroid.presenter.view.UserWithdrawView
import xin.lrvik.taskcicleandroid.service.TaskService
import xin.lrvik.taskcicleandroid.service.WalletService
import javax.inject.Inject

class CashPledgePresenter @Inject constructor() : BasePresenter<CashPledgeView>() {
    @Inject
    lateinit var walletService: WalletService

    fun cashPledgeList() {
        if (!checkNetWork()) {
            return
        }
        walletService.cashPledgeList().execute(lifecycleProvider, mView, false) {
            mView.onRecordResult(it)
        }
    }
}
