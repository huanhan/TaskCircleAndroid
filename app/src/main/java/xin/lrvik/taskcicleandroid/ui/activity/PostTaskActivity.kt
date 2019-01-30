package xin.lrvik.taskcicleandroid.ui.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.geocode.*
import com.baidu.mapapi.utils.DistanceUtil
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback
import com.zhihu.matisse.Matisse
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_post_task.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.margin
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseMvpActivity
import xin.lrvik.taskcicleandroid.baselibrary.utils.DateUtils
import xin.lrvik.taskcicleandroid.common.UserInfo
import xin.lrvik.taskcicleandroid.data.protocol.TaskClass
import xin.lrvik.taskcicleandroid.data.protocol.TaskDetail
import xin.lrvik.taskcicleandroid.data.protocol.TaskStep
import xin.lrvik.taskcicleandroid.data.protocol.enums.TaskState
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.PostTaskPresenter
import xin.lrvik.taskcicleandroid.presenter.view.PostTaskView
import xin.lrvik.taskcicleandroid.ui.adapter.RvAddTaskStepAdapter
import xin.lrvik.taskcicleandroid.ui.adapter.RvModifyTaskStepAdapter
import xin.lrvik.taskcicleandroid.ui.dialog.ClassificationDialog
import xin.lrvik.taskcicleandroid.ui.dialog.TaskStepDialog
import kotlin.collections.ArrayList

//startActivity<PostTaskActivity>(PostTaskActivity.MODE to PostTaskActivity.Mode.LOOK.name, PostTaskActivity.TASKID to "20190124220137236856064")
class PostTaskActivity : BaseMvpActivity<PostTaskPresenter>(), PostTaskView {

    enum class Mode {
        CREATE(), MODIFY(), LOOK()
    }

    companion object {
        val MODE = "MODE"
        val TASKID = "TASKID"
    }

    var mode: Mode = Mode.LOOK

    //是否可修改
    var isModify = false

    var mDialog: ClassificationDialog? = null

    var classList = ArrayList<TaskClass>()
    lateinit var mRvTaskStepAdapter: RvModifyTaskStepAdapter
//    var geoCoder = GeoCoder.newInstance()

    override fun injectComponent() {
        DaggerTaskCircleComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onTaskClassResult(data: List<TaskClass>) {
        mDialog?.let {
            it.setData(data)
            it.setCurrData(0, -1)
        }
    }

    override fun onResult(result: String) {
        toast(result)
        finish()
    }

    override fun onTaskDetailResult(data: TaskDetail) {

        mEtTitle.setText(data.name)
        mLevContext.contentText = data.context
        mRvTaskStepAdapter.setNewData(data.taskSteps)
        classList.addAll(data.taskClassifyAppDtos!!)
        mFlowlayout.adapter.notifyDataChanged()
        checkTipVisible()
    }

    internal var colors = intArrayOf(Color.parseColor("#90C5ED"),
            Color.parseColor("#92CED6"),
            Color.parseColor("#F69153"),
            Color.parseColor("#BFAED0"),
            Color.parseColor("#E58F8E"),
            Color.parseColor("#66CCB7"),
            Color.parseColor("#F4BB7E"),
            Color.parseColor("#90C5ED"),
            Color.parseColor("#F79153"))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_task)
        initView()
    }

    private fun initView() {
        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        //步骤
        mRvStep.layoutManager = LinearLayoutManager(this)

        var list = ArrayList<TaskStep>()
//        list.add(TaskStep("1", 1, "默认标题", "默认内容", ""))

        mRvTaskStepAdapter = RvModifyTaskStepAdapter(list)
        val mItemDragAndSwipeCallback = ItemDragAndSwipeCallback(mRvTaskStepAdapter)
        val mItemTouchHelper = ItemTouchHelper(mItemDragAndSwipeCallback)
        mItemTouchHelper.attachToRecyclerView(mRvStep)

        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START or ItemTouchHelper.END)

        mRvTaskStepAdapter.enableSwipeItem()
        mRvTaskStepAdapter.enableDragItem(mItemTouchHelper)

        mRvStep.adapter = mRvTaskStepAdapter

        mBtAddStep.onClick {
            mRvTaskStepAdapter.addData(TaskStep("", -1, "新步骤标题", "新步骤内容", ""))
        }

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

        mRvTaskStepAdapter.setOnItemChildClickListener { adapter, view, position ->
            var taskStep = adapter.data[position] as TaskStep

            when (view.id) {
                R.id.mIvIcon -> {

                }
                else -> {
                }
            }
        }

        mTvClassTip.onClick {
            createDialog()
        }

        mBtAddClass.onClick {
            createDialog()
        }

        mFlowlayout.adapter = object : TagAdapter<TaskClass>(classList) {

            override fun getView(parent: FlowLayout, position: Int, taskClass: TaskClass): View {
                //动态创建热词布局
                var tv = TextView(this@PostTaskActivity)
                var params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                params.margin = dip(4)
                tv.layoutParams = params
                tv.setPadding(dip(6), dip(4), dip(6), dip(4))
                tv.setBackgroundColor(colors[position % colors.size])
                tv.text = taskClass.name
                return tv
            }
        }

        mFlowlayout.setOnTagClickListener { view, position, parent ->
            classList.removeAt(position)
            mFlowlayout.adapter.notifyDataChanged()
            checkTipVisible()
            true
        }

        mBtnAdd.onClick {
            if (validation()) {
                var classs = classList.flatMap {
                    listOf(it.id)
                }
                mPresenter.addTask(classs, mEtTitle.text.toString(), mLevContext.contentText, mRvTaskStepAdapter.data)
            }
        }

        mBtnSave.onClick {
            var classs = classList.map {
                it.id
            }
            try {
                var taskid = intent.getStringExtra(TASKID)
                mPresenter.modifyTask(taskid, classs, mEtTitle.text.toString(), mLevContext.contentText, mRvTaskStepAdapter.data)
            } catch (e: Exception) {
                toast("未传递任务id")
            }
        }

        //判断模式
        mode = Mode.valueOf(intent.getStringExtra(MODE))

        when (mode) {
            Mode.LOOK -> {
                isModify = false

                mTvClassTip.visibility = View.GONE
                mBtAddStep.visibility = View.GONE
                mBtnAdd.visibility = View.GONE
                mBtnSave.visibility = View.GONE
                mEtTitle.isEnabled = false
                mLevContext.id_et_input.isEnabled = false
                mRvTaskStepAdapter.disableSwipeItem()
                mRvTaskStepAdapter.disableDragItem()

                try {
                    var taskid = intent.getStringExtra(TASKID)
                    mPresenter.queryTaskDetail(taskid)
                } catch (e: Exception) {
                    toast("未传递任务id")
                    return
                }

                mBtnAdd.text = "接取任务"

                actionBar?.title = "查看任务"

                mFlowlayout.setOnTagClickListener(null)
            }
            Mode.MODIFY -> {
                isModify = true

                mTvClassTip.visibility = View.VISIBLE
                mBtAddStep.visibility = View.VISIBLE
                mBtnAdd.visibility = View.GONE
                mBtnSave.visibility = View.VISIBLE
                mEtTitle.isEnabled = true
                mLevContext.id_et_input.isEnabled = true
                mRvTaskStepAdapter.enableSwipeItem()
                mRvTaskStepAdapter.enableDragItem(mItemTouchHelper)

                try {
                    var taskid = intent.getStringExtra(TASKID)
                    mPresenter.queryTaskDetail(taskid)
                } catch (e: Exception) {
                    toast("未传递任务id")
                    return
                }

                mBtnAdd.text = "保存任务"
                actionBar?.title = "保存任务"
            }
            Mode.CREATE -> {
                isModify = true
                mTvClassTip.visibility = View.VISIBLE
                mBtAddStep.visibility = View.VISIBLE
                mBtnAdd.visibility = View.VISIBLE
                mBtnSave.visibility = View.GONE
                mEtTitle.isEnabled = true
                mLevContext.id_et_input.isEnabled = true
                mRvTaskStepAdapter.enableSwipeItem()
                mRvTaskStepAdapter.enableDragItem(mItemTouchHelper)

                mBtnAdd.text = "新建任务"
                actionBar?.title = "新建任务"
            }
        }

    }

    fun validation(): Boolean {
        if (classList.size !in 1..5) {
            toast("标签数必须在1~5个")
            return false
        }

        if (mEtTitle.text.toString().isEmpty()) {
            toast("任务标题不能为空")
            return false
        }

        if (mLevContext.contentText.isEmpty()) {
            toast("任务描述不能为空")
            return false
        }

        if (mRvTaskStepAdapter.data.size !in 1..15) {
            toast("步骤至少要有1步,最多不超过15步")
            return false
        }

        for ((index, datum) in mRvTaskStepAdapter.data.withIndex()) {
            datum.step = index
            datum.step++
            if (datum.title.isEmpty() || datum.context.isEmpty()) {
                toast("步骤标题和内容不能为空")
                return false
            }
        }

        return true
    }


    lateinit var mSelected: List<Uri>

    protected override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            mSelected = Matisse.obtainResult(data!!)
            toast("选中了$mSelected")
        }
    }

    private fun createDialog() {
        if (mDialog == null) {
            mDialog = ClassificationDialog()
            mDialog!!.showDialog(supportFragmentManager)
            mDialog!!.listener = object : ClassificationDialog.OnClassificationClickListener {
                override fun onClassClick(pPos: Int, cPos: Int, taskClass: TaskClass) {
                    if (!classList.contains(taskClass)) {
                        classList.add(taskClass)
                        mFlowlayout.adapter.notifyDataChanged()
                        checkTipVisible()
                    }
                }
            }
            mPresenter.classData()
        } else {
            mDialog!!.showDialog(supportFragmentManager)
        }
    }

    fun checkTipVisible() {
        if (mode == Mode.LOOK) {
            mBtAddClass.visibility = View.GONE
            mTvClassTip.visibility = View.GONE
            return
        }

        if (classList.size == 0) {
            mTvClassTip.visibility = View.VISIBLE
            mBtAddClass.visibility = View.GONE
        } else {
            mTvClassTip.visibility = View.GONE
            mBtAddClass.visibility = View.VISIBLE
        }
    }

    //判断界面是否根据权限显示
    private fun <T> isShow(view: View, state: T, list: List<T>) {
        view.visibility = if (list.contains(state)) View.VISIBLE else View.GONE
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
