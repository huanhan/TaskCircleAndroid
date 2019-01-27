package xin.lrvik.taskcicleandroid.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_hunter_task_state.*
import org.jetbrains.anko.support.v4.toast
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ui.fragment.BaseMvpFragment
import xin.lrvik.taskcicleandroid.data.protocol.HunterTask
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.HunterTaskStatePresenter
import xin.lrvik.taskcicleandroid.presenter.view.HunterTaskStateView
import xin.lrvik.taskcicleandroid.ui.adapter.RvHunterTaskStateAdapter
import java.util.*

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/3.
 *
 */

class HunterTaskStateFragment : BaseMvpFragment<HunterTaskStatePresenter>(), HunterTaskStateView {

    lateinit var type: String
    lateinit var mRvHunterTaskStateAdapter: RvHunterTaskStateAdapter
    var curPage: Int = 0
    var pageSize: Int = 20

    override fun injectComponent() {
        DaggerTaskCircleComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onTaskStateResult(data: Page<HunterTask>) {
        mSwipeRefresh?.let {
            //下拉刷新
            if (mSwipeRefresh.isRefreshing) {
                mSwipeRefresh.isRefreshing = false
                mRvHunterTaskStateAdapter.setNewData(data.content)
                if (data.pageNum == data.totalPage - 1) {
                    mRvHunterTaskStateAdapter.loadMoreEnd()
                }
//            mRvHunterTaskStateAdapter.notifyDataSetChanged()
            } else {//上拉加载数据
                if (data.pageNum == data.totalPage - 1) {//到底了
                    mRvHunterTaskStateAdapter.loadMoreEnd()
                } else {//还可以上拉
                    mRvHunterTaskStateAdapter.loadMoreComplete()
                }
                mRvHunterTaskStateAdapter.addData(data.content)
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
        return inflater.inflate(R.layout.fragment_hunter_task_state, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.type = arguments!!.getString(TYPE)

        var linearLayoutManager = LinearLayoutManager(context)
        mRvTask.layoutManager = linearLayoutManager
        linearLayoutManager.orientation = OrientationHelper.VERTICAL
        var list = ArrayList<HunterTask>()
        mRvHunterTaskStateAdapter = RvHunterTaskStateAdapter(list)
//        mRvHunterTaskStateAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN)
        mRvTask.adapter = mRvHunterTaskStateAdapter
        mRvTask.isNestedScrollingEnabled = false

        mRvHunterTaskStateAdapter.setOnItemClickListener { adapter, view, position ->
            var task = adapter.data[position] as HunterTask
        }
        mRvHunterTaskStateAdapter.setOnItemChildClickListener { adapter, view, position ->
            var task = adapter.data[position] as HunterTask

            task?.let {
                when (view.id) {
                    R.id.mBtBegin -> {
                        mPresenter.beginTask(it.id!!)
                    }
                    R.id.mBtSubmitAudit -> {
                        mPresenter.submitAudit(it.id!!)
                    }
                    R.id.mBtReWork -> {
                        mPresenter.reworkTask(it.id!!)
                    }
                    R.id.mBtAbandon -> {
                        //mPresenter.abandonTask()
                    }
                    R.id.mBtSubmitAdminAudit -> {
                        mPresenter.submitAdminAudit(it.id!!)
                    }
                    R.id.mBtCancelAdminAudit -> {
                        mPresenter.cancelAdminAudit(it.id!!)
                    }
                    R.id.mBtAgreeAbandon -> {
                        mPresenter.agreeAbandon(it.taskId!!)
                    }
                    R.id.mBtDisAgreeAbandon -> {
                        //mPresenter.disAgreeAbandon()
                    }
                }
            }
        }

        mSwipeRefresh.setOnRefreshListener {
            curPage = 0
            mPresenter.getStateTaskData(type, curPage, pageSize)
        }

        mRvHunterTaskStateAdapter.setOnLoadMoreListener({
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

        fun newInstance(type: String): HunterTaskStateFragment {
            var taskClassFragment = HunterTaskStateFragment()
            var bundle = Bundle()
            bundle.putString(TYPE, type)
            taskClassFragment.arguments = bundle
            return taskClassFragment
        }

    }
}
