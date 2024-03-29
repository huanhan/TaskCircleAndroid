package xin.lrvik.taskcicleandroid.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_hunter_task_detail.*
import org.jetbrains.anko.*
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseMvpActivity
import xin.lrvik.taskcicleandroid.baselibrary.utils.DateUtils
import xin.lrvik.taskcicleandroid.data.protocol.HunterRunningStep
import xin.lrvik.taskcicleandroid.data.protocol.HunterTaskAndStep
import xin.lrvik.taskcicleandroid.data.protocol.TaskStep
import xin.lrvik.taskcicleandroid.data.protocol.enums.HunterTaskState
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.HunterTaskDetailPresenter
import xin.lrvik.taskcicleandroid.presenter.view.HunterTaskDetailView
import xin.lrvik.taskcicleandroid.ui.adapter.RvHunterTaskStepAdapter
import xin.lrvik.taskcicleandroid.ui.dialog.TaskStepDialog

class HunterTaskDetailActivity : BaseMvpActivity<HunterTaskDetailPresenter>(), HunterTaskDetailView {

    enum class Mode {
        MODIFY(), LOOK()
    }

    companion object {
        val MODE = "MODE"
        val TASKID = "HUNTERTASKID"
    }

    var mode: Mode = Mode.LOOK
    var huntertaskid: String = ""
    var taskid: String = ""

    lateinit var mRvTaskStepAdapter: RvHunterTaskStepAdapter

    override fun injectComponent() {
        DaggerTaskCircleComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onTaskAndStepQueryResult(data: HunterTaskAndStep) {
        huntertaskid = data.hunterTaskId!!
        taskid = data.taskId!!
        mTvTitle.text = data.name
        mTvContent.text = data.context
        data.beginTime?.let {
            mTvBeginTime.text = "${DateUtils.convertTimeToString(it)}"
        }
        data.deadline?.let {
            mTvDeadline.text = "${DateUtils.convertTimeToString(it)}"
        }

        if (mode != Mode.LOOK) {
            if (data.currStep == data.totalStep) {
                mBtSubmitAudit.visibility = View.VISIBLE
            }

            data.state?.let {

                //任务步骤
                mRvTaskStepAdapter.flag = (it == HunterTaskState.TASK_COMPLETE ||
                        it == HunterTaskState.EXECUTE ||
                        it == HunterTaskState.BEGIN)

                if (data.stop == true)
                    mBtSubmitAudit.visibility = View.GONE
                else
                    isShow(mBtSubmitAudit, it, listOf(HunterTaskState.TASK_COMPLETE,
                            HunterTaskState.ALLOW_REWORK_ABANDON_HAVE_COMPENSATE,
                            HunterTaskState.ALLOW_REWORK_ABANDON_NO_COMPENSATE,
                            HunterTaskState.NO_REWORK_NO_COMPENSATE,
                            HunterTaskState.NO_REWORK_HAVE_COMPENSATE))

                isShow(mBtBegin, it, listOf(HunterTaskState.RECEIVE))

                isShow(mBtReWork, it, listOf(HunterTaskState.ALLOW_REWORK_ABANDON_HAVE_COMPENSATE,
                        HunterTaskState.ALLOW_REWORK_ABANDON_NO_COMPENSATE))

                isShow(mBtAbandon, it, listOf(HunterTaskState.RECEIVE,
                        HunterTaskState.BEGIN,
                        HunterTaskState.EXECUTE,
                        HunterTaskState.TASK_COMPLETE,
                        HunterTaskState.ALLOW_REWORK_ABANDON_HAVE_COMPENSATE,
                        HunterTaskState.ALLOW_REWORK_ABANDON_NO_COMPENSATE,
                        HunterTaskState.NO_REWORK_NO_COMPENSATE,
                        HunterTaskState.NO_REWORK_HAVE_COMPENSATE),
                        listOf(HunterTaskState.END_NO,
                                HunterTaskState.END_OK,
                                HunterTaskState.TASK_ABANDON,
                                HunterTaskState.TASK_BE_ABANDON))

                isShow(mBtSubmitAdminAudit, it, listOf(HunterTaskState.USER_REPULSE,
                        HunterTaskState.ALLOW_REWORK_ABANDON_HAVE_COMPENSATE,
                        HunterTaskState.ALLOW_REWORK_ABANDON_NO_COMPENSATE,
                        HunterTaskState.NO_REWORK_NO_COMPENSATE,
                        HunterTaskState.NO_REWORK_HAVE_COMPENSATE))

                isShow(mBtCancelAdminAudit, it, listOf(HunterTaskState.COMMIT_TO_ADMIN,
                        HunterTaskState.WITH_ADMIN_NEGOTIATE,
                        HunterTaskState.COMMIT_ADMIN_AUDIT,
                        HunterTaskState.ADMIN_AUDIT,
                        HunterTaskState.ALLOW_REWORK_ABANDON_NO_COMPENSATE,
                        HunterTaskState.NO_REWORK_HAVE_COMPENSATE,
                        HunterTaskState.NO_REWORK_NO_COMPENSATE))

            }

            isShow(mBtAgreeAbandon, data.stop ?: false && data.state == HunterTaskState.WITH_HUNTER_NEGOTIATE)
            isShow(mBtDisAgreeAbandon, data.stop ?: false && data.state == HunterTaskState.WITH_HUNTER_NEGOTIATE)
        }

        mBtChat.onClick {
            startActivity<ChatActivity>(ChatActivity.HUNTERID to data.hunterId,
                    ChatActivity.HUNTERTASKID to data.hunterTaskId,
                    ChatActivity.USERID to data.userId)
        }

        mRvTaskStepAdapter.setNewData(data.taskSteps)
    }

    override fun onResult(result: String) {
        toast(result)
        mPresenter.query(huntertaskid)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hunter_task_detail)
        initView()
    }

    private fun initView() {
        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar?.title = "猎刃任务信息"
        }

        try {
            huntertaskid = intent.getStringExtra(TASKID)
        } catch (e: Exception) {
            toast("未传递任务id")
            finish()
        }

        mRvStep.layoutManager = LinearLayoutManager(this)

        var list = ArrayList<HunterRunningStep>()


        //判断模式
        mode = Mode.valueOf(intent.getStringExtra(HunterTaskDetailActivity.MODE))
        when (mode) {
            Mode.LOOK -> {
                mRvTaskStepAdapter = RvHunterTaskStepAdapter(list, false)
            }
            Mode.MODIFY -> {
                mRvTaskStepAdapter = RvHunterTaskStepAdapter(list, true)
            }
        }


        mRvStep.adapter = mRvTaskStepAdapter
        if (mode != Mode.LOOK) {
            mRvTaskStepAdapter.setOnItemClickListener { adapter, view, position ->
                var dialogdata = ArrayList<TaskStep>()
                for (datum in adapter.data as ArrayList<HunterRunningStep>) {
                    dialogdata.add(TaskStep(datum.hunterTaskId ?: "", datum.step
                            ?: 0, datum.taskTitle
                            ?: "", datum.taskContext ?: "", datum.taskImg ?: ""))
                }

                val dialog = TaskStepDialog.showDialog(supportFragmentManager,
                        dialogdata,
                        false, position)

                dialog.setOnCloseListener(object : TaskStepDialog.OnCloseListener {
                    override fun onClose() {
                        mRvTaskStepAdapter.notifyDataSetChanged()
                    }
                })
            }
            mTvMore.visibility = View.VISIBLE
        }

        mRvTaskStepAdapter.setOnItemChildClickListener { adapter, view, position ->
            var hunterRunningStep = adapter.data[position] as HunterRunningStep
            hunterRunningStep?.let { step ->
                when (view.id) {
                    R.id.mBtSubmit -> {
                        alert {
                            customView {
                                title = "提交步骤完成信息"
                                verticalLayout {
                                    val etContext = editText {
                                        hint = "请填写执行情况（必填）"
                                    }.lparams {
                                        leftMargin = 15
                                        rightMargin = 15
                                        width = matchParent
                                    }
                                    val etMarker = editText {
                                        hint = "备注"
                                    }.lparams {
                                        leftMargin = 15
                                        rightMargin = 15
                                        width = matchParent
                                    }
                                    positiveButton("是") {
                                        var context = etContext.text.toString().trim()
                                        if (context.isEmpty() || context.length > 255) {
                                            toast("请填写执行情况（0~255字内）")
                                            return@positiveButton
                                        }
                                        mPresenter.addTaskStep(hunterRunningStep.hunterTaskId ?: "",
                                                hunterRunningStep.step ?: 0,
                                                context,
                                                etMarker.text.toString())
                                    }
                                    negativeButton("否") { }
                                }
                            }
                        }.show()
                    }
                    R.id.mBtModify -> {
                        alert {
                            customView {
                                title = "提交步骤修改信息"
                                verticalLayout {
                                    val etContext = editText {
                                        hint = "请填写执行情况（必填）"
                                        text.append((hunterRunningStep.hunterTaskContext ?: ""))
                                    }.lparams {
                                        leftMargin = 15
                                        rightMargin = 15
                                        width = matchParent
                                    }
                                    val etMarker = editText {
                                        hint = "备注"
                                        text.append((hunterRunningStep.hunterTaskRemake ?: ""))
                                    }.lparams {
                                        leftMargin = 15
                                        rightMargin = 15
                                        width = matchParent
                                    }
                                    positiveButton("是") {
                                        var context = etContext.text.toString().trim()
                                        if (context.isEmpty() || context.length > 255) {
                                            toast("请填写执行情况（0~255字内）")
                                            return@positiveButton
                                        }
                                        mPresenter.updateTaskStep(hunterRunningStep.hunterTaskId
                                                ?: "",
                                                hunterRunningStep.step ?: 0,
                                                context,
                                                etMarker.text.toString())
                                    }
                                    negativeButton("否") { }
                                }
                            }
                        }.show()
                    }
                    else -> {
                    }
                }
            }
        }

        mBtBegin.onClick {
            alert("是否开始?") {
                positiveButton("是") { mPresenter.beginTask(huntertaskid) }
                negativeButton("否") { }
            }.show()
        }
        mBtReWork.onClick {
            alert("是否进行重做?") {
                positiveButton("是") { mPresenter.reworkTask(huntertaskid) }
                negativeButton("否") { }
            }.show()
        }
        mBtAbandon.onClick {
            alert {
                customView {
                    title = "是否放弃该任务?"
                    message = "放弃将会通知用户，用户同意放弃即可无需赔偿。强制放弃不需要经过用户同意即可放弃，若任务规定要赔偿则会进行赔偿。"
                    verticalLayout {
                        val btDisAgree = editText {
                            hint = "请输入放弃的理由"
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
                            mPresenter.abandonTask(huntertaskid, disAgreeStr)
                        }
                        neutralPressed("强制放弃") {
                            var disAgreeStr = btDisAgree.text.toString().trim()
                            if (disAgreeStr.isEmpty() || disAgreeStr.length > 255) {
                                toast("请输入0~255字内的理由")
                                return@neutralPressed
                            }
                            mPresenter.forceAbandonTask(huntertaskid, disAgreeStr)
                        }
                        negativeButton("否") { }
                    }
                }
            }.show()
        }
        mBtSubmitAdminAudit.onClick {
            alert("产生纠纷或对任务有异议，请将任务提交至管理员", "是否提交至管理员?") {
                positiveButton("是") { mPresenter.submitAdminAudit(huntertaskid) }
                negativeButton("否") { }
            }.show()
        }
        mBtCancelAdminAudit.onClick {
            alert("是否取消提交管理员?") {
                positiveButton("是") { mPresenter.cancelAdminAudit(huntertaskid) }
                negativeButton("否") { }
            }.show()
        }
        mBtAgreeAbandon.onClick {
            alert("用户提交了任务放弃申请,是否同意该申请", "是否同意用户放弃?") {
                positiveButton("是") { mPresenter.agreeAbandon(huntertaskid) }
                negativeButton("否") { }
            }.show()
        }
        mBtDisAgreeAbandon.onClick {

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
                            mPresenter.disAgreeAbandon(huntertaskid, disAgreeStr)
                        }
                        negativeButton("否") { }
                    }
                }
            }.show()
        }
        mBtSubmitAudit.onClick {
            alert("是否将任务提交给用户审核?") {
                positiveButton("是") { mPresenter.submitAudit(huntertaskid) }
                negativeButton("否") { }
            }.show()
        }

        mTvMore.onClick {
            startActivity<TaskDetailActivity>(TaskDetailActivity.TASKID to taskid)
        }



        mPresenter.query(huntertaskid)
    }

    private fun isShow(view: View, state: HunterTaskState, list: List<HunterTaskState>) {
        isShow(view, list.contains(state))
    }

    private fun isShow(view: View, state: HunterTaskState, list: List<HunterTaskState>, list2: List<HunterTaskState>) {
        isShow(view, list.contains(state) && !list2.contains(state))
    }

    private fun isShow(view: View, isVis: Boolean) {
        view.visibility = if (isVis) View.VISIBLE else View.GONE
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
