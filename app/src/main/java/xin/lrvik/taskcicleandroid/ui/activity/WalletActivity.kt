package xin.lrvik.taskcicleandroid.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_wallet.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.baselibrary.common.UserInfo
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseMvpActivity
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.view.WalletPresenter
import xin.lrvik.taskcicleandroid.presenter.view.WalletView

class WalletActivity : BaseMvpActivity<WalletPresenter>(), WalletView {
    override fun injectComponent() {
        DaggerTaskCircleComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onResult(result: String) {
        toast(result)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)
        initView()
    }

    private fun initView() {
        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "钱包"
        }

        mTvMoney.text = "¥${UserInfo.money}"
        mBtAddMoney.onClick {
            alert("提交后工作人员将与您联系进行充值", "是否提交充值申请?") {
                positiveButton("是") { mPresenter.payAdd() }
                negativeButton("否") { }
            }.show()
        }
        mBtWithdrawMoney.onClick {
            alert("提交后工作人员将与您联系进行提现工作", "是否提交提现申请?") {
                positiveButton("是") { mPresenter.withdrawAdd() }
                negativeButton("否") { }
            }.show()

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.wallet -> {
                startActivity<WalletBudgetActivity>()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.wallet, menu)
        return true
    }
}
