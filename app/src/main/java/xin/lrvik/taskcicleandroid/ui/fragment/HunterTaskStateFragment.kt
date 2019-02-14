package xin.lrvik.taskcicleandroid.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_hunter_task_state.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ui.fragment.BaseMvpFragment
import xin.lrvik.taskcicleandroid.data.protocol.HunterTask
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.HunterTaskStatePresenter
import xin.lrvik.taskcicleandroid.presenter.view.HunterTaskStateView
import xin.lrvik.taskcicleandroid.ui.activity.ChatActivity
import xin.lrvik.taskcicleandroid.ui.activity.HunterEvaluateActivity
import xin.lrvik.taskcicleandroid.ui.activity.HunterTaskDetailActivity
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
//            mRvHunterRunningAdapter.notifyDataSetChanged()
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
//        mRvHunterRunningAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN)
        mRvTask.adapter = mRvHunterTaskStateAdapter

        mRvHunterTaskStateAdapter.setEmptyView(R.layout.view_empty,mRvTask)
        mRvHunterTaskStateAdapter.setOnItemClickListener { adapter, view, position ->
            var task = adapter.data[position] as HunterTask
            startActivity<HunterTaskDetailActivity>(HunterTaskDetailActivity.TASKID to task.id!!,
                    HunterTaskDetailActivity.MODE to HunterTaskDetailActivity.Mode.MODIFY.name)
        }
        mRvHunterTaskStateAdapter.setOnItemChildClickListener { adapter, view, position ->
            var hunterTask = adapter.data[position] as HunterTask

            hunterTask?.let { task ->
                when (view.id) {
                    R.id.mBtBegin -> {
                        alert("是否开始?") {
                            positiveButton("是") { mPresenter.beginTask(task.id!!) }
                            negativeButton("否") { }
                        }.show()
                    }
                    R.id.mBtSubmitAudit -> {
                        alert("是否提交审核?") {
                            positiveButton("是") { mPresenter.submitAudit(task.id!!) }
                            negativeButton("否") { }
                        }.show()
                    }
                    R.id.mBtReWork -> {
                        alert("是否进行重做?") {
                            positiveButton("是") { mPresenter.reworkTask(task.id!!) }
                            negativeButton("否") { }
                        }.show()
                    }
                    R.id.mBtAbandon -> {
                        alert {

                            customView {
                                title = "是否放弃该任务?"
                                message="放弃将会通知用户，用户同意放弃即可无需赔偿。强制放弃不需要经过用户同意即可放弃，若任务规定要赔偿则会进行赔偿。"
                                verticalLayout {
                                    val btDisAgree = editText {
                                        hint = "请输入放弃的理由"
                                    }.lparams {
                                        leftMargin = 15
                                        rightMargin = 15
                                        width = matchParent
                                    }
                                    positiveButton("放弃") {
                                        var disAgreeStr = btDisAgree.text.toString().trim()
                                        if (disAgreeStr.isEmpty() || disAgreeStr.length > 255) {
                                            toast("请输入0~255字内的理由")
                                            return@positiveButton
                                        }
                                        mPresenter.abandonTask(task.id!!, disAgreeStr)
                                    }
                                    neutralPressed("强制放弃") {
                                        var disAgreeStr = btDisAgree.text.toString().trim()
                                        if (disAgreeStr.isEmpty() || disAgreeStr.length > 255) {
                                            toast("请输入0~255字内的理由")
                                            return@neutralPressed
                                        }
                                        mPresenter.forceAbandonTask(task.id!!, disAgreeStr)
                                    }
                                    negativeButton("否") { }
                                }
                            }
                        }.show()
                    }
                    R.id.mBtSubmitAdminAudit -> {
                        alert("产生纠纷或对任务有异议，请将任务提交至管理员", "是否提交至管理员?") {
                            positiveButton("是") { mPresenter.submitAdminAudit(task.id!!) }
                            negativeButton("否") { }
                        }.show()

                    }
                    R.id.mBtCancelAdminAudit -> {
                        alert("是否取消提交管理员?") {
                            positiveButton("是") { mPresenter.cancelAdminAudit(task.id!!) }
                            negativeButton("否") { }
                        }.show()

                    }
                    R.id.mBtAgreeAbandon -> {
                        alert("用户提交了任务放弃申请,是否同意该申请", "是否同意用户放弃?") {
                            positiveButton("是") { mPresenter.agreeAbandon(task.taskId!!) }
                            negativeButton("否") { }
                        }.show()
                    }
                    R.id.mBtDisAgreeAbandon -> {

                        alert {
                            customView {
                                title = "用户提交了任务放弃申请,是否拒绝该申请"
                                verticalLayout {
                                    val btDisAgree = editText {
                                        hint = "请输入拒绝的理由"

                                    }.lparams {
                                        leftMargin = 15
                                        rightMargin = 15
                                        width = matchParent
                                    }
                                    positiveButton("是") {
                                        var disAgreeStr = btDisAgree.text.toString().trim()
                                        if (disAgreeStr.isEmpty() || disAgreeStr.length > 255) {
                                            toast("请输入0~255字内的理由")
                                            return@positiveButton
                                        }
                                        mPresenter.disAgreeAbandon(task.taskId!!, disAgreeStr)
                                    }
                                    negativeButton("否") { }
                                }
                            }
                        }.show()
                    }
                    R.id.mBtChat -> {
                        startActivity<ChatActivity>(ChatActivity.HUNTERID to task.hunterId!!,
                                ChatActivity.TASKID to task.taskId!!,
                                ChatActivity.USERID to task.userId!!)
                    }
                    R.id.mBtWarning -> {
                        var mes = ""
                        if (!task.auditContext.isNullOrEmpty()) {
                            mes = mes.plus("理由：${task.auditContext}")
                        }
                        alert(mes, "用户回复") {
                            positiveButton("确定") { }
                        }.show()

                    }
                    R.id.mBtEva -> {
                        startActivity<HunterEvaluateActivity>(HunterEvaluateActivity.HUNTERTASKID to task.id!!)
                        isRefresh = true
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
