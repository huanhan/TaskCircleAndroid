package xin.lrvik.taskcicleandroid.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_class.*
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseMvpActivity
import xin.lrvik.taskcicleandroid.data.protocol.TaskClass
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.ClassPresenter
import xin.lrvik.taskcicleandroid.presenter.view.ClassView
import xin.lrvik.taskcicleandroid.ui.adapter.VpAdapter
import xin.lrvik.taskcicleandroid.ui.dialog.ClassificationDialog
import xin.lrvik.taskcicleandroid.ui.fragment.TaskClassFragment
import java.util.*

class ClassActivity : BaseMvpActivity<ClassPresenter>(), ClassView {

    var mDialog: ClassificationDialog = ClassificationDialog()

    private val mFragments by lazy { Stack<Fragment>() }
    private val mTitles by lazy { Stack<String>() }
    lateinit var mTitle: String
    var mPPos = 0
    var mCPos = 0
    lateinit var mViewPagerAdapter: VpAdapter

    override fun injectComponent() {
        DaggerTaskCircleComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    fun initViewPagerData(data: List<TaskClass>, pPos: Int, cPos: Int) {
        mPPos = pPos
        mCPos = cPos

        var parent: TaskClass? = null
        //遍历所有数据，找到标题的大分类
        for ((index, taskClass) in data.withIndex()) {
            if (index == pPos) {
                parent = taskClass

                val actionBar = supportActionBar
                if (actionBar != null) {
                    actionBar.title = taskClass.name
                }
            }
        }
        mTitles.clear()
        mFragments.clear()
        parent?.let {
            parent.taskClassifies?.let {
                it.forEach {
                    mTitles.add("${it.name}")
                    mFragments.add(TaskClassFragment.newInstance(it.id))
                }
                mViewPagerAdapter.notifyDataSetChanged()
            }
            mViewPager.setCurrentItem(cPos, true)
        }
    }

    override fun onTaskClassResult(data: List<TaskClass>) {
        mViewPagerAdapter = VpAdapter(supportFragmentManager, mFragments, mTitles)
        mViewPager.adapter = mViewPagerAdapter
        //关联ViewPager
        mTabLayout.setupWithViewPager(mViewPager)


        mDialog.setData(data)
        //遍历所有数据，找到标题的大分类
        for ((indx, taskClass) in data.withIndex()) {
            if (taskClass.name == mTitle) {
                mPPos = indx
            }
        }
        initViewPagerData(data, mPPos, 0)

        mDialog!!.listener = object : ClassificationDialog.OnClassificationClickListener {
            override fun onClassClick(pPos: Int, cPos: Int, taskClass: TaskClass) {
                mDialog.dismissDialog()

                if (mTitles.contains(taskClass.name)) {
                    //是在此分类下，移动到分类
                    var indexOf = mTitles.indexOf(taskClass.name)
                    mCPos = indexOf
                    mViewPager.setCurrentItem(indexOf, true)
                } else {
                    //不在此分类，获取数据
                    initViewPagerData(data, pPos, cPos)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class)
        initView()
    }

    private fun initView() {
        mTitle = intent.getStringExtra("CLASSTYPE")

        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = mTitle
        }

        mIvBt.onClick {
            if (mDialog.getData().isNotEmpty()) {
                mDialog.showDialog(supportFragmentManager)
                mDialog.setCurrData(mPPos, mCPos)
            }
        }
        //让dialog弹出初始化一次
        mDialog.showDialog(supportFragmentManager)
        mDialog.dismiss()

        mPresenter.classData()
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
