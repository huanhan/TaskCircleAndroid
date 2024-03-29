package xin.lrvik.taskcicleandroid.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_hunter_task.*
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseActivity
import xin.lrvik.taskcicleandroid.ui.adapter.VpAdapter
import xin.lrvik.taskcicleandroid.ui.fragment.HunterTaskStateFragment
import java.util.*

class HunterTaskActivity : BaseActivity() {

    private val mFragments by lazy { Stack<Fragment>() }
    private val mTitles by lazy { Stack<String>() }
    lateinit var title:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hunter_task)
        initView()
    }

    private fun initView() {
        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "猎刃任务管理"
        }

        getData()
        mViewPager.adapter = VpAdapter(supportFragmentManager, mFragments, mTitles)
        mTabLayout.setupWithViewPager(mViewPager)
    }

    private fun getData() {

        mTitles.add("全部")
        mTitles.add("未开始")
        mTitles.add("进行中")
        mTitles.add("审核中")
        mTitles.add("已完成")

        listOf("ALL", "NEW", "RUNNING", "AUDIT", "FINISH").forEach {
            mFragments.add(HunterTaskStateFragment.newInstance(it))
        }
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
