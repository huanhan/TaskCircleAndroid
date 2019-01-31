package xin.lrvik.taskcicleandroid.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.MenuItem
import android.view.View
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.utils.DistanceUtil
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback
import kotlinx.android.synthetic.main.activity_task_detail.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.toast
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.isShow
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseMvpActivity
import xin.lrvik.taskcicleandroid.baselibrary.utils.DateUtils
import xin.lrvik.taskcicleandroid.common.UserInfo
import xin.lrvik.taskcicleandroid.data.protocol.TaskDetail
import xin.lrvik.taskcicleandroid.data.protocol.TaskStep
import xin.lrvik.taskcicleandroid.data.protocol.enums.TaskState
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.TaskDetailPresenter
import xin.lrvik.taskcicleandroid.presenter.view.TaskDetailView
import xin.lrvik.taskcicleandroid.ui.adapter.RvAddTaskStepAdapter
import xin.lrvik.taskcicleandroid.ui.dialog.TaskStepDialog

class TaskDetailActivity : BaseMvpActivity<TaskDetailPresenter>(), TaskDetailView {

    lateinit var mRvTaskStepAdapter: RvAddTaskStepAdapter

    lateinit var taskid: String

    companion object {
        val TASKID = "TASKID"
    }

    override fun injectComponent() {
        DaggerTaskCircleComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onTaskDetailResult(data: TaskDetail) {
        var classstr = ""
        data.taskClassifyAppDtos?.let { taskClass ->
            if (taskClass.size > 0) {
                taskClass.forEach {
                    classstr = classstr.plus("${it.name},")
                }
                //移除最后一位
                classstr = classstr.removeRange(classstr.length - 1, classstr.length)
                mTvClass.text = classstr
            }
        }
        mTvTitle.text = data.name
        mTvContent.text = data.context
        mRvTaskStepAdapter.setNewData(data.taskSteps)

        mSwTaskRework.isEnabled = false
        mSwCompensate.isEnabled = false

        data.peopleNumber?.let {
            mTvPeoNum.text = "$it"
        }

        if (data.money != null && data.peopleNumber != null) {
            mTvMoneyNum.text = "${data.money!!.toBigDecimal().divide(data.peopleNumber!!.toBigDecimal())}元/人"
        }
        data.beginTime?.let {
            mTvBeginTime.text = "${DateUtils.convertTimeToString(it)}"
        }
        data.deadline?.let {
            mTvDeadline.text = "${DateUtils.convertTimeToString(it)}"
        }
        data.permitAbandonMinute?.let {
            mTvPermitAbandonMinute.text = "${it}分钟"
        }
        data.taskRework?.let {
            mSwTaskRework.isChecked = it
            mTvCompensateMoney.visibility = if (it) View.VISIBLE else View.GONE
            mTvCompensateMoney.text = if (it) "${data.compensateMoney.toString()}元" else "0元"
        }
        data.compensate?.let {
            mSwCompensate.isChecked = it
        }

        if (data.latitude != null && data.longitude != null) {

            var distance = DistanceUtil.getDistance(LatLng(UserInfo.latitude, UserInfo.longitude), LatLng(data.latitude
                    ?: 0.0, data.longitude ?: 0.0)).toInt()
            var dis = if (distance < 1000) "$distance 米" else "${distance / 1000} 千米"

            mTvLocation.text = "${data.address} 距您($dis)"
        }

        //如果用户id不是自己则展示接单按钮
        mBtnAccept.visibility = if (UserInfo.userId != data.userId) View.VISIBLE else View.GONE

        //如果任务的用户id是自己则展示查看接取猎刃
        mBtnQueryHunter.visibility = if (UserInfo.userId === data.userId) View.VISIBLE else View.GONE

        data.state?.let {
            if (data.userId==UserInfo.userId) {
                //发布
                isShow(mBtRelease, it, listOf(TaskState.AUDIT_SUCCESS,
                        TaskState.OK_ISSUE))

                //撤回
                isShow(mBtOut, it, listOf(TaskState.ISSUE))

                //上架
                isShow(mBtUpper, it, listOf(TaskState.OUT))

                //放弃任务
                isShow(mBtAbandon, it, listOf(TaskState.FORBID_RECEIVE, TaskState.OUT))

                //取消放弃
                isShow(mBtCancelAbandon, it, listOf(TaskState.ABANDON_COMMIT))
            }

        }


    }


    override fun onResult(result: String) {
        toast(result)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)
        initView()
    }

    private fun initView() {
        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar?.title = "查看任务"
        }

        try {
            taskid = intent.getStringExtra(TASKID)
        } catch (e: Exception) {
            toast("未传递任务id")
            finish()
        }

        //步骤
        mRvStep.layoutManager = LinearLayoutManager(this)

        var list = ArrayList<TaskStep>()

        mRvTaskStepAdapter = RvAddTaskStepAdapter(list)
        val mItemDragAndSwipeCallback = ItemDragAndSwipeCallback(mRvTaskStepAdapter)
        val mItemTouchHelper = ItemTouchHelper(mItemDragAndSwipeCallback)
        mItemTouchHelper.attachToRecyclerView(mRvStep)

        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START or ItemTouchHelper.END)

        mRvTaskStepAdapter.disableSwipeItem()
        mRvTaskStepAdapter.disableDragItem()

        mRvStep.adapter = mRvTaskStepAdapter

        mRvTaskStepAdapter.setOnItemClickListener { adapter, view, position ->
            val dialog = TaskStepDialog.showDialog(supportFragmentManager,
                    adapter.data as ArrayList<TaskStep>,
                    false, position)

            dialog.setOnCloseListener(object : TaskStepDialog.OnCloseListener {
                override fun onClose() {
                    mRvTaskStepAdapter.notifyDataSetChanged()
                }
            })
        }

        mBtnAccept.onClick {
            mPresenter.acceptTask(taskid)
        }

        mBtnQueryHunter.onClick {
            startActivity<HunterRunningActivity>(HunterRunningActivity.TASKID to taskid)
        }

        mBtRelease.onClick {
            startActivity<ReleaseTaskActivity>(ReleaseTaskActivity.TASKID to taskid)
        }
        mBtOut.onClick {
            alert("撤回后任务不能被接取", "是否撤回任务?") {
                positiveButton("是") { mPresenter.outTask(taskid) }
                negativeButton("否") { }
            }.show()
        }
        mBtUpper.onClick {
            alert("上架后任务可正常被接取", "是否上架任务?") {
                positiveButton("是") { mPresenter.upperTask(taskid) }
                negativeButton("否") { }
            }.show()
        }
        mBtAbandon.onClick {
            alert("任务将被放弃，猎刃同意后即可放弃该任务", "是否放弃任务?") {
                positiveButton("是") { mPresenter.abandonTask(taskid) }
                negativeButton("否") { }
            }.show()
        }
        mBtCancelAbandon.onClick {
            alert("是否取消放弃任务?") {
                positiveButton("是") { mPresenter.cancelAbandon(taskid) }
                negativeButton("否") { }
            }.show()
        }

        mPresenter.queryTaskDetail(taskid)
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
