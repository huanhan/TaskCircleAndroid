package xin.lrvik.taskcicleandroid.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_task_state.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.R.id.mRvTask
import xin.lrvik.taskcicleandroid.baselibrary.common.BaseApplication.Companion.context
import xin.lrvik.taskcicleandroid.baselibrary.ui.fragment.BaseFragment
import xin.lrvik.taskcicleandroid.baselibrary.ui.fragment.BaseMvpFragment
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.data.protocol.Task
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.TaskStatePresenter
import xin.lrvik.taskcicleandroid.presenter.view.TaskStateView
import xin.lrvik.taskcicleandroid.ui.activity.PostTaskActivity
import xin.lrvik.taskcicleandroid.ui.activity.ReleaseTaskActivity
import xin.lrvik.taskcicleandroid.ui.adapter.RvTaskStateAdapter
import java.util.*

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/3.
 *
 */

class TaskStateFragment : BaseMvpFragment<TaskStatePresenter>(), TaskStateView {

    lateinit var type: String
    lateinit var mRvTaskStateAdapter: RvTaskStateAdapter
    var curPage: Int = 0
    var pageSize: Int = 20

    override fun injectComponent() {
        DaggerTaskCircleComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onTaskStateResult(data: Page<Task>) {
        mSwipeRefresh?.let {
            //下拉刷新
            if (mSwipeRefresh.isRefreshing) {
                mSwipeRefresh.isRefreshing = false
                mRvTaskStateAdapter.setNewData(data.content)
                if (data.pageNum == data.totalPage - 1) {
                    mRvTaskStateAdapter.loadMoreEnd()
                }
//            mRvTaskStateAdapter.notifyDataSetChanged()
            } else {//上拉加载数据
                if (data.pageNum == data.totalPage - 1) {//到底了
                    mRvTaskStateAdapter.loadMoreEnd()
                } else {//还可以上拉
                    mRvTaskStateAdapter.loadMoreComplete()
                }
                mRvTaskStateAdapter.addData(data.content)
            }
            curPage = data.pageNum
        }
    }

    override fun onResult(result: String) {
        toast(result)
        curPage = 0
        mSwipeRefresh.isRefreshing = true
        mPresenter.getStateTaskData(type, curPage, pageSize)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_task_state, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.type = arguments!!.getString(TYPE)

        var linearLayoutManager = LinearLayoutManager(context)
        mRvTask.layoutManager = linearLayoutManager
        linearLayoutManager.orientation = OrientationHelper.VERTICAL
        var list = ArrayList<Task>()
        mRvTaskStateAdapter = RvTaskStateAdapter(list)
//        mRvTaskStateAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN)
        mRvTask.adapter = mRvTaskStateAdapter
        mRvTask.isNestedScrollingEnabled = false

        mRvTaskStateAdapter.setOnItemClickListener { adapter, view, position ->
            var task = adapter.data[position] as Task
            startActivity<PostTaskActivity>(PostTaskActivity.MODE to PostTaskActivity.Mode.LOOK.name, PostTaskActivity.TASKID to task.id!!)
        }
        mRvTaskStateAdapter.setOnItemChildClickListener { adapter, view, position ->
            var task = adapter.data[position] as Task
            task.id?.let {
                when (view.id) {
                    R.id.mBtModify -> {
                        startActivity<PostTaskActivity>(PostTaskActivity.MODE to PostTaskActivity.Mode.MODIFY.name, PostTaskActivity.TASKID to it)
                    }
                    R.id.mBtSubmitAudit -> {
                        mPresenter.submitAudit(it)
                    }
                    R.id.mBtCancelAudit -> {
                        mPresenter.cancelAudit(it)
                    }
                    R.id.mBtRelease -> {
                        startActivity<ReleaseTaskActivity>(ReleaseTaskActivity.TASKID to task.id!!)
                        isRefresh = true
                    }
                    R.id.mBtOut -> {
                        mPresenter.outTask(it)
                    }
                    R.id.mBtUpper -> {
                        mPresenter.upperTask(it)
                    }
                    R.id.mBtAbandon -> {
                        mPresenter.abandonTask(it)
                    }
                    R.id.mBtCancelAbandon -> {
                        mPresenter.cancelAbandon(it)
                    }
                }

            }
        }

        mSwipeRefresh.setOnRefreshListener {
            curPage = 0
            mPresenter.getStateTaskData(type, curPage, pageSize)
        }

        mRvTaskStateAdapter.setOnLoadMoreListener({
            mPresenter.getStateTaskData(type, ++curPage, pageSize)
        }, mRvTask)
        mSwipeRefresh.isRefreshing = true
        mPresenter.getStateTaskData(type, curPage, pageSize)

    }

    var isRefresh = false

    override fun onResume() {
        super.onResume()
        if (isRefresh) {
            isRefresh = false
            mSwipeRefresh?.let {
                curPage = 0
                mSwipeRefresh.isRefreshing = true
                mPresenter.getStateTaskData(type, curPage, pageSize)
            }
        }
    }

    companion object {
        val TYPE = "TYPE"

        fun newInstance(type: String): TaskStateFragment {
            var taskClassFragment = TaskStateFragment()
            var bundle = Bundle()
            bundle.putString(TYPE, type)
            taskClassFragment.arguments = bundle
            return taskClassFragment
        }

    }
}
