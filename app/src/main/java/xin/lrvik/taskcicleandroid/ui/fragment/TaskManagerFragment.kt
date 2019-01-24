package xin.lrvik.taskcicleandroid.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_task_manager.*
import org.jetbrains.anko.support.v4.startActivity
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.baselibrary.ui.fragment.BaseFragment
import xin.lrvik.taskcicleandroid.ui.activity.HunterTaskActivity
import xin.lrvik.taskcicleandroid.ui.adapter.VpTaskAdapter
import java.util.*


class TaskManagerFragment : BaseFragment() {

    private val mFragments by lazy { Stack<Fragment>() }
    private val mTitles by lazy { Stack<String>() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_task_manager, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mFabHunter.onClick {
            startActivity<HunterTaskActivity>()
        }

        mTitles.add("全部")
        mTitles.add("新建")
        mTitles.add("待审核")
        mTitles.add("已发布")
        mTitles.add("已完成")

        listOf("ALL", "NEW", "AUDIT", "ISSUE", "FINISH").forEach {
            mFragments.add(TaskStateFragment.newInstance(it))
        }


        mViewPager.adapter = VpTaskAdapter(activity!!.supportFragmentManager, mFragments, mTitles)
        mTabLayout.setupWithViewPager(mViewPager)
    }

}
