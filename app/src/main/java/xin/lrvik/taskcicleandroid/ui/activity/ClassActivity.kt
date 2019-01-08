package xin.lrvik.taskcicleandroid.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_class.*
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseActivity
import xin.lrvik.taskcicleandroid.ui.adapter.VpTaskAdapter
import xin.lrvik.taskcicleandroid.ui.dialog.ClassificationDialog
import xin.lrvik.taskcicleandroid.ui.fragment.TaskStateFragment
import java.util.*

class ClassActivity : BaseActivity() {

    var mDialog: ClassificationDialog? = null

    private val mFragments by lazy { Stack<Fragment>() }
    private val mTitles by lazy { Stack<String>() }
    lateinit var title:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class)
        initView()
    }

    private fun initView() {
        title = intent.getStringExtra("CLASSTYPE")

        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = intent.getStringExtra("CLASSTYPE")
        }

        mIvBt.onClick {
            if (mDialog == null) {
                mDialog = ClassificationDialog()
                mDialog!!.showDialog(supportFragmentManager)
            } else {
                mDialog!!.showDialog(supportFragmentManager)
            }
        }

        getData()

        mViewPager.adapter = VpTaskAdapter(supportFragmentManager, mFragments, mTitles)
        mTabLayout.setupWithViewPager(mViewPager)
    }

    private fun getData() {

        for (i in 0..4) {
            mTitles.add("$title $i")
        }

        mTitles.forEach {
            mFragments.add(TaskStateFragment.newInstance(it))
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
