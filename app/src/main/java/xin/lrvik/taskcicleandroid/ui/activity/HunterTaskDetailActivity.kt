package xin.lrvik.taskcicleandroid.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_hunter_task_detail.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseMvpActivity
import xin.lrvik.taskcicleandroid.baselibrary.utils.DateUtils
import xin.lrvik.taskcicleandroid.data.protocol.TaskDetail
import xin.lrvik.taskcicleandroid.data.protocol.TaskStep
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.HunterTaskDetailPresenter
import xin.lrvik.taskcicleandroid.presenter.view.HunterTaskDetailView
import xin.lrvik.taskcicleandroid.ui.adapter.RvAddTaskStepAdapter
import xin.lrvik.taskcicleandroid.ui.dialog.TaskStepDialog

class HunterTaskDetailActivity : BaseMvpActivity<HunterTaskDetailPresenter>(), HunterTaskDetailView {

    lateinit var mRvTaskStepAdapter: RvAddTaskStepAdapter

    override fun injectComponent() {
        DaggerTaskCircleComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onTaskDetailResult(data: TaskDetail) {
        mTvTitle.text = data.name
        mTvContent.text = data.context
        mRvTaskStepAdapter.setNewData(data.taskSteps)
        data.beginTime?.let {
            mTvBeginTime.text = "${DateUtils.convertTimeToString(it)}"
        }
        data.deadline?.let {
            mTvDeadline.text = "${DateUtils.convertTimeToString(it)}"
        }
    }

    override fun onResult(result: String) {

    }

    var taskid: String = ""

    companion object {
        val TASKID = "TASKID"
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
            actionBar?.title = "任务步骤"
        }

        try {
            taskid = intent.getStringExtra(TASKID)
        } catch (e: Exception) {
            toast("未传递任务id")
            finish()
        }

        mRvStep.layoutManager = LinearLayoutManager(this)

        var list = ArrayList<TaskStep>()
        mRvTaskStepAdapter = RvAddTaskStepAdapter(list)
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
        mTvMore.onClick {
            startActivity<TaskDetailActivity>(TaskDetailActivity.TASKID to taskid)
        }

        mPresenter.queryTaskDetail(taskid)
    }
}
