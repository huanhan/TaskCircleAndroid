package xin.lrvik.taskcicleandroid.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_task_detail.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.margin
import org.jetbrains.anko.startActivity
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseActivity
import xin.lrvik.taskcicleandroid.data.protocol.TaskClass
import xin.lrvik.taskcicleandroid.data.protocol.TaskStep
import xin.lrvik.taskcicleandroid.ui.adapter.RvAddTaskStepAdapter
import xin.lrvik.taskcicleandroid.ui.dialog.ClassificationDialog
import xin.lrvik.taskcicleandroid.ui.dialog.TaskStepDialog
import xin.lrvik.taskcicleandroid.ui.widget.KeyboardUtil
import java.util.*


class TaskDetailActivity : BaseActivity() {

    internal var colors = intArrayOf(Color.parseColor("#90C5ED"),
            Color.parseColor("#92CED6"),
            Color.parseColor("#F69153"),
            Color.parseColor("#BFAED0"),
            Color.parseColor("#E58F8E"),
            Color.parseColor("#66CCB7"),
            Color.parseColor("#F4BB7E"),
            Color.parseColor("#90C5ED"),
            Color.parseColor("#F79153"))

    var mDialog: ClassificationDialog? = null

    var classList = ArrayList<TaskClass>()

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
            actionBar.title = "发布任务"
        }
        KeyboardUtil(mKeyBoardView, mEtTaskNum)
        KeyboardUtil(mKeyBoardView, mEtMoneyNum)

        //步骤
        mRvStep.layoutManager = LinearLayoutManager(this)


        var list = ArrayList<TaskStep>()
        list.add(TaskStep("1 ", 1, "默认标题", "默认内容", ""))

        val mRvTaskStepAdapter = RvAddTaskStepAdapter(list)
        val mItemDragAndSwipeCallback = ItemDragAndSwipeCallback(mRvTaskStepAdapter)
        val mItemTouchHelper = ItemTouchHelper(mItemDragAndSwipeCallback)
        mItemTouchHelper.attachToRecyclerView(mRvStep)

        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START or ItemTouchHelper.END)
        mRvTaskStepAdapter.enableSwipeItem()
        mRvTaskStepAdapter.enableDragItem(mItemTouchHelper)

        mRvStep.adapter = mRvTaskStepAdapter

        mBtAddStep.onClick {
            mRvTaskStepAdapter.addData(TaskStep("111", -1, "新步骤标题", "新步骤内容", ""))
        }

        mRvTaskStepAdapter.setOnItemClickListener { adapter, view, position ->
            val dialog = TaskStepDialog.showDialog(supportFragmentManager, list, true, position)

            dialog.setOnCloseListener(object : TaskStepDialog.OnCloseListener {
                override fun onClose() {
                    mRvTaskStepAdapter.notifyDataSetChanged()
                }
            })
        }

        mTvClassTip.onClick {
            if (mDialog == null) {
                mDialog = ClassificationDialog()
                mDialog!!.showDialog(supportFragmentManager)
                mDialog!!.listener = object : ClassificationDialog.OnClassificationClickListener {
                    override fun onClassClick(taskClass: TaskClass) {
                        if (!classList.contains(taskClass)) {
                            classList.add(taskClass)
                            mFlowlayout.adapter.notifyDataChanged()
                            checkTipVisible()
                        }
                    }
                }
            }else{
                mDialog!!.showDialog(supportFragmentManager)
            }


        }

        mBtAddClass.onClick {
            if (mDialog == null) {
                mDialog = ClassificationDialog()
                mDialog!!.showDialog(supportFragmentManager)
                mDialog!!.listener = object : ClassificationDialog.OnClassificationClickListener {
                    override fun onClassClick(taskClass: TaskClass) {
                        if (!classList.contains(taskClass)) {
                            classList.add(taskClass)
                            mFlowlayout.adapter.notifyDataChanged()
                            checkTipVisible()
                        }
                    }
                }
            }else{
                mDialog!!.showDialog(supportFragmentManager)
            }
        }

        mFlowlayout.adapter = object : TagAdapter<TaskClass>(classList) {

            override fun getView(parent: FlowLayout, position: Int, taskStep: TaskClass): View {
                //动态创建热词布局
                var tv = TextView(this@TaskDetailActivity)
                var params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                params.margin = dip(4)
                tv.layoutParams = params
                tv.setPadding(dip(6), dip(4), dip(6), dip(4))
                tv.setBackgroundColor(colors[position % colors.size])
                tv.text = taskStep.name
                return tv
            }
        }

        mFlowlayout.setOnTagClickListener { view, position, parent ->
            classList.removeAt(position)
            mFlowlayout.adapter.notifyDataChanged()
            checkTipVisible()
            true
        }


    }

    fun checkTipVisible() {
        if (classList.size == 0) {
            mTvClassTip.visibility = View.VISIBLE
            mBtAddClass.visibility = View.GONE
        } else {
            mTvClassTip.visibility = View.GONE
            mBtAddClass.visibility = View.VISIBLE
        }
    }


    override fun onResume() {
        mLl.requestFocus()
        super.onResume()
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

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mKeyBoardView.visibility == View.VISIBLE) {
                mKeyBoardView.visibility = View.GONE
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}
