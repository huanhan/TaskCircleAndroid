package xin.lrvik.taskcicleandroid.ui.activity

import android.graphics.Canvas
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.MenuItem
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback
import com.chad.library.adapter.base.listener.OnItemDragListener
import com.chad.library.adapter.base.listener.OnItemSwipeListener
import kotlinx.android.synthetic.main.activity_task_detail.*
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseActivity
import xin.lrvik.taskcicleandroid.data.protocol.TaskStep
import xin.lrvik.taskcicleandroid.ui.adapter.RvAddTaskStepAdapter
import xin.lrvik.taskcicleandroid.ui.widget.KeyboardUtil
import java.util.*


class TaskDetailActivity : BaseActivity() {

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

        //拖拽
        val listener = object : OnItemDragListener {
            override fun onItemDragStart(viewHolder: RecyclerView.ViewHolder, pos: Int) {
                //val holder = viewHolder as BaseViewHolder
                //                holder.setTextColor(R.id.tv, Color.WHITE);
            }

            override fun onItemDragMoving(source: RecyclerView.ViewHolder, from: Int, target: RecyclerView.ViewHolder, to: Int) {
                //Log.d(TAG, "move from: " + source.adapterPosition + " to: " + target.adapterPosition)
            }

            override fun onItemDragEnd(viewHolder: RecyclerView.ViewHolder, pos: Int) {
                //Log.d(TAG, "drag end")
                //val holder = viewHolder as BaseViewHolder
                //                holder.setTextColor(R.id.tv, Color.BLACK);
            }
        }

        val onItemSwipeListener = object : OnItemSwipeListener {
            override fun onItemSwipeStart(viewHolder: RecyclerView.ViewHolder, pos: Int) {
                //Log.d(TAG, "view swiped start: $pos")
                val holder = viewHolder as BaseViewHolder
                //                holder.setTextColor(R.id.tv, Color.WHITE);
            }

            override fun clearView(viewHolder: RecyclerView.ViewHolder, pos: Int) {
                //Log.d(TAG, "View reset: $pos")
                val holder = viewHolder as BaseViewHolder
                //                holder.setTextColor(R.id.tv, Color.BLACK);
            }

            override fun onItemSwiped(viewHolder: RecyclerView.ViewHolder, pos: Int) {
                //Log.d(TAG, "View Swiped: $pos")
            }

            override fun onItemSwipeMoving(canvas: Canvas, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, isCurrentlyActive: Boolean) {
                //canvas.drawColor(ContextCompat.getColor(this@ItemDragAndSwipeUseActivity, R.color.color_light_blue))
                //                canvas.drawText("Just some text", 0, 40, paint);
            }
        }

        var list = ArrayList<TaskStep>()
        for (i in 0..10) {
            list.add(TaskStep("$i ", i, "步骤标题$i", "步骤内容$i", ""))
        }
        val mRvTaskStepAdapter = RvAddTaskStepAdapter(list)
        val mItemDragAndSwipeCallback = ItemDragAndSwipeCallback(mRvTaskStepAdapter)
        val mItemTouchHelper = ItemTouchHelper(mItemDragAndSwipeCallback)
        mItemTouchHelper.attachToRecyclerView(mRvStep)

        //mItemDragAndSwipeCallback.setDragMoveFlags(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN);
        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START or ItemTouchHelper.END)
        mRvTaskStepAdapter.enableSwipeItem()
        mRvTaskStepAdapter.setOnItemSwipeListener(onItemSwipeListener)
        mRvTaskStepAdapter.enableDragItem(mItemTouchHelper)
        mRvTaskStepAdapter.setOnItemDragListener(listener)
//        mRecyclerView.addItemDecoration(new GridItemDecoration(this ,R.drawable.list_divider));

        mRvStep.adapter = mRvTaskStepAdapter

        mBtAddStep.onClick {
            mRvTaskStepAdapter.addData(TaskStep("111 ", 1, "新增步骤标题", "新增步骤内容", ""))

        }
    }


    override fun onResume() {
        super.onResume()
        mLl.requestFocus()
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
