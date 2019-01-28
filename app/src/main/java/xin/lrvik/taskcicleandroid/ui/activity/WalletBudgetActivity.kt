package xin.lrvik.taskcicleandroid.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_wallet_detail.*
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.data.protocol.Budget
import xin.lrvik.taskcicleandroid.ui.adapter.RvWalletDetailAdapter
import java.sql.Timestamp
import java.util.*

class WalletBudgetActivity : AppCompatActivity() {

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
        var lm = LinearLayoutManager(this)
        lm.orientation = LinearLayoutManager.VERTICAL
        mRvDetail.layoutManager = lm

        var data = ArrayList<Budget>()
        for (i in 1..10) {
            data.add(Budget(Timestamp(Date().time + 1000 * 60 * 60 * 24 * i), 125.00F * i, "收入"))
            data.add(Budget(Timestamp(Date().time + 1000 * 60 * 60 * 24 * i), -125.00F * i, "支出"))
        }
        mRvDetail.adapter = RvWalletDetailAdapter(data)

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
