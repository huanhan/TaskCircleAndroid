package xin.lrvik.taskcicleandroid.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_user_eva_list.*
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.ui.adapter.VpAdapter
import xin.lrvik.taskcicleandroid.ui.fragment.*
import java.util.*

class UserEvaListActivity : AppCompatActivity() {

    private val mFragments by lazy { Stack<Fragment>() }
    private val mTitles by lazy { Stack<String>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_eva_list)
        initView()
    }

    private fun initView() {
        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "我的评价"
        }
        getData()
        mViewPager.adapter = VpAdapter(supportFragmentManager, mFragments, mTitles)
        mTabLayout.setupWithViewPager(mViewPager)

        mFabHunter.onClick {

        }
    }

    private fun getData() {
        mTitles.add("收到的")
        mTitles.add("发出的")
        mFragments.add(UserReEvaFragment.newInstance())
        mFragments.add(UserSdEvaFragment.newInstance())
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
