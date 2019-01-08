package xin.lrvik.taskcicleandroid.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_task_manager.*
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ui.fragment.BaseFragment
import xin.lrvik.taskcicleandroid.ui.adapter.VpTaskAdapter
import java.util.*


class TaskManagerFragment : BaseFragment(){

    private val mFragments by lazy { Stack<Fragment>() }
    private val mTitles by lazy { Stack<String>() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_task_manager, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mTitles.add("全部")
        mTitles.add("待审核")
        mTitles.add("待发布")
        mTitles.add("待接取")
        mTitles.add("已接取")
        mTitles.add("已完成")
        mTitles.add("待评价")
        mTitles.add("已放弃")

        mTitles.forEach {
            mFragments.add(TaskStateFragment.newInstance(it))
        }
        mViewPager.adapter = VpTaskAdapter(activity!!.supportFragmentManager, mFragments, mTitles)
        mTabLayout.setupWithViewPager(mViewPager)
    }

}
