package xin.lrvik.taskcicleandroid.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_wallet_detail.*
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.ui.adapter.VpTaskAdapter
import xin.lrvik.taskcicleandroid.ui.fragment.CashPledgeFragment
import xin.lrvik.taskcicleandroid.ui.fragment.TransferFragment
import xin.lrvik.taskcicleandroid.ui.fragment.UserWithdrawFragment
import java.util.*

class WalletBudgetActivity : AppCompatActivity() {

    private val mFragments by lazy { Stack<Fragment>() }
    private val mTitles by lazy { Stack<String>() }
    lateinit var title:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet_detail)
        initView()
    }


    private fun initView() {
        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "零钱明细"
        }
        getData()
        mViewPager.adapter = VpTaskAdapter(supportFragmentManager, mFragments, mTitles)
        mTabLayout.setupWithViewPager(mViewPager)
    }

    private fun getData() {
        mTitles.add("消费记录")
        mTitles.add("充值提现记录")
        mTitles.add("押金记录")
        mFragments.add(TransferFragment.newInstance())
        mFragments.add(UserWithdrawFragment.newInstance())
        mFragments.add(CashPledgeFragment.newInstance())
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
