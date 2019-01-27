package xin.lrvik.taskcicleandroid.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_task_state.*
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import xin.lrvik.taskcicleandroid.R
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
//            mRvHunterTaskStateAdapter.notifyDataSetChanged()
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
//        mRvHunterTaskStateAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN)
        mRvTask.adapter = mRvTaskStateAdapter
        mRvTask.isNestedScrollingEnabled = false

        mRvTaskStateAdapter.setOnItemClickListener { adapter, view, position ->
            var task = adapter.data[position] as Task
            startActivity<PostTaskActivity>(PostTaskActivity.MODE to PostTaskActivity.Mode.LOOK.name, PostTaskActivity.TASKID to task.id!!)
        }
        mRvTaskStateAdapter.setOnItemChildClickListener { adapter, view, position ->
            var task = adapter.data[position] as Task
            task.id?.let { taskId ->
                when (view.id) {
                    R.id.mBtModify -> {
                        startActivity<PostTaskActivity>(PostTaskActivity.MODE to PostTaskActivity.Mode.MODIFY.name, PostTaskActivity.TASKID to taskId)
                    }
                    R.id.mBtSubmitAudit -> {
                        alert("是否提交审核?") {
                            positiveButton("是") { mPresenter.submitAudit(taskId) }
                            negativeButton("否") { }
                        }.show()
                    }
                    R.id.mBtCancelAudit -> {
                        alert("是否取消审核?") {
                            positiveButton("是") { mPresenter.cancelAudit(taskId) }
                            negativeButton("否") { }
                        }.show()
                    }
                    R.id.mBtRelease -> {
                        startActivity<ReleaseTaskActivity>(ReleaseTaskActivity.TASKID to task.id!!)
                        isRefresh = true
                    }
                    R.id.mBtOut -> {
                        alert("撤回后任务不能被接取", "是否撤回任务?") {
                            positiveButton("是") { mPresenter.outTask(taskId) }
                            negativeButton("否") { }
                        }.show()
                    }
                    R.id.mBtUpper -> {
                        alert("上架后任务可正常被接取", "是否上架任务?") {
                            positiveButton("是") { mPresenter.upperTask(taskId) }
                            negativeButton("否") { }
                        }.show()
                    }
                    R.id.mBtAbandon -> {
                        alert("任务将被放弃，猎刃同意后即可放弃该任务", "是否放弃任务?") {
                            positiveButton("是") { mPresenter.abandonTask(taskId) }
                            negativeButton("否") { }
                        }.show()
                    }
                    R.id.mBtCancelAbandon -> {
                        alert("是否取消放弃任务?") {
                            positiveButton("是") { mPresenter.cancelAbandon(taskId) }
                            negativeButton("否") { }
                        }.show()
                    }
                    else -> {
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
