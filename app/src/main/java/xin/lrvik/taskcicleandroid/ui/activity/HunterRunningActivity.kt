package xin.lrvik.taskcicleandroid.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_post_task.*
import kotlinx.android.synthetic.main.fragment_hunter_task_state.*
import org.jetbrains.anko.*
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.common.BaseApplication.Companion.context
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseMvpActivity
import xin.lrvik.taskcicleandroid.data.protocol.HunterTask
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.HunterRunningPresenter
import xin.lrvik.taskcicleandroid.presenter.view.HunterRunningView
import xin.lrvik.taskcicleandroid.ui.adapter.RvHunterRunningAdapter
import java.util.ArrayList

class HunterRunningActivity : BaseMvpActivity<HunterRunningPresenter>(), HunterRunningView {

    var curPage: Int = 0
    var pageSize: Int = 20
    var taskid: String = ""
    lateinit var mRvHunterRunningAdapter: RvHunterRunningAdapter

    override fun injectComponent() {
        DaggerTaskCircleComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onTaskRunningResult(data: Page<HunterTask>) {
        mSwipeRefresh?.let {
            //下拉刷新
            if (mSwipeRefresh.isRefreshing) {
                mSwipeRefresh.isRefreshing = false
                mRvHunterRunningAdapter.setNewData(data.content)
                if (data.pageNum == data.totalPage - 1) {
                    mRvHunterRunningAdapter.loadMoreEnd()
                }
//            mRvHunterRunningAdapter.notifyDataSetChanged()
            } else {//上拉加载数据
                if (data.pageNum == data.totalPage - 1) {//到底了
                    mRvHunterRunningAdapter.loadMoreEnd()
                } else {//还可以上拉
                    mRvHunterRunningAdapter.loadMoreComplete()
                }
                mRvHunterRunningAdapter.addData(data.content)
            }
            curPage = data.pageNum
        }
    }

    override fun onResult(result: String) {
        toast(result)
        curPage = 0
        mSwipeRefresh.isRefreshing = true
        mPresenter.hunterRunning(taskid, curPage, pageSize)
    }

    companion object {
        val TASKID = "TASKID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hunter_running)
        initView()
    }

    private fun initView() {
        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar?.title = "猎刃执行列表"
        }
        try {
            taskid = intent.getStringExtra(TASKID)
        } catch (e: Exception) {
            toast("未传递任务id")
            finish()
        }

        var linearLayoutManager = LinearLayoutManager(context)
        mRvTask.layoutManager = linearLayoutManager
        linearLayoutManager.orientation = OrientationHelper.VERTICAL
        var list = ArrayList<HunterTask>()
        mRvHunterRunningAdapter = RvHunterRunningAdapter(list)
//        mRvHunterRunningAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN)
        mRvTask.adapter = mRvHunterRunningAdapter

        mRvHunterRunningAdapter.setEmptyView(R.layout.view_empty,mRvTask)
        mRvHunterRunningAdapter.setOnItemClickListener { adapter, view, position ->
            var task = adapter.data[position] as HunterTask
            startActivity<HunterTaskDetailActivity>(HunterTaskDetailActivity.TASKID to task.id!!,
                    HunterTaskDetailActivity.MODE to HunterTaskDetailActivity.Mode.LOOK.name)
        }
        mRvHunterRunningAdapter.setOnItemChildClickListener { adapter, view, position ->
            var hunterTask = adapter.data[position] as HunterTask

            hunterTask?.let { task ->
                when (view.id) {
                    R.id.mBtAuditSuccess -> {
                        alert("任务任务已完成,是否满足预期结果?") {
                            positiveButton("是") { mPresenter.auditSuccess(task.id!!) }
                            negativeButton("否") { }
                        }.show()
                    }
                    R.id.mBtAuditFailure -> {
                        alert {
                            customView {
                                title = "审核失败，请提交失败理由"
                                verticalLayout {
                                    val btDisAgree = editText {
                                        hint = "请输入失败理由"
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
                                        mPresenter.auditFailure(task.id!!, disAgreeStr)
                                    }
                                    negativeButton("否") { }
                                }
                            }
                        }.show()
                    }
                    R.id.mBtAbandonPass -> {
                        alert("猎刃提交了放弃任务申请，是否通过？") {
                            positiveButton("是") { mPresenter.abandonPass(task.id!!) }
                            negativeButton("否") { }
                        }.show()
                    }
                    R.id.mBtAbandonNotPass -> {
                        alert {
                            customView {
                                title = "猎刃提交了放弃任务申请，是否拒绝？"
                                verticalLayout {
                                    val btDisAgree = editText {
                                        hint = "请输入拒绝理由"
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
                                        mPresenter.abandonNotPass(task.id!!, disAgreeStr)
                                    }
                                    negativeButton("否") { }
                                }
                            }
                        }.show()
                    }
                    R.id.mBtChat -> {
                        startActivity<ChatActivity>(ChatActivity.HUNTERID to hunterTask.hunterId,
                                ChatActivity.TASKID to hunterTask.taskId,
                                ChatActivity.USERID to hunterTask.userId)
                    }
                    R.id.mBtWarning -> {
                        var mes = ""
                        if (!task.context.isNullOrEmpty()) {
                            mes = mes.plus("放弃理由：${task.context} \n")
                        }
                        if (!task.hunterRejectContext.isNullOrEmpty()) {
                            mes = mes.plus("拒绝理由：${task.hunterRejectContext}")
                        }
                        alert(mes, "猎刃回复") {
                            positiveButton("确定") { }
                        }.show()

                    }
                    R.id.mBtEva -> {
                        startActivity<UserEvaluateActivity>(UserEvaluateActivity.HUNTERTASKID to hunterTask.id!!)
                        isRefresh = true
                    }
                    else -> {

                    }
                }

            }
        }

        mSwipeRefresh.setOnRefreshListener {
            curPage = 0
            mPresenter.hunterRunning(taskid, curPage, pageSize)
        }

        mRvHunterRunningAdapter.setOnLoadMoreListener({
            mPresenter.hunterRunning(taskid, ++curPage, pageSize)
        }, mRvTask)
        mSwipeRefresh.isRefreshing = true
        mPresenter.hunterRunning(taskid, curPage, pageSize)

    }

    var isRefresh = false

    override fun onResume() {
        super.onResume()
        if (isRefresh) {
            isRefresh = false
            mSwipeRefresh?.let {
                curPage = 0
                mSwipeRefresh.isRefreshing = true
                mPresenter.hunterRunning(taskid, curPage, pageSize)
            }
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
