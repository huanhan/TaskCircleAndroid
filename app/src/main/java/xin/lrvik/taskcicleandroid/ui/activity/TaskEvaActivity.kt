package xin.lrvik.taskcicleandroid.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_task_eva.*
import org.jetbrains.anko.toast
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.common.BaseApplication.Companion.context
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseMvpActivity
import xin.lrvik.taskcicleandroid.data.protocol.CommentTask
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.TaskEvaPresenter
import xin.lrvik.taskcicleandroid.presenter.view.TaskEvaView
import xin.lrvik.taskcicleandroid.ui.adapter.RvTaskEvaAdapter
import java.lang.Exception
import java.util.ArrayList

class TaskEvaActivity : BaseMvpActivity<TaskEvaPresenter>(), TaskEvaView {

    companion object {
        val TASKID = "TASKID"
    }


    var curPage: Int = 0
    var pageSize: Int = 20
    lateinit var mRvTaskEvaAdapter: RvTaskEvaAdapter
    lateinit var taskid: String

    override fun onListResult(data: Page<CommentTask>) {
        mRvTaskEvaAdapter.setNewData(data.content)

        //下拉刷新
        if (mSwipeRefresh.isRefreshing) {
            mSwipeRefresh.isRefreshing = false
            mRvTaskEvaAdapter.setNewData(data.content)
            if (data.pageNum == data.totalPage - 1) {
                mRvTaskEvaAdapter.loadMoreEnd()
            }
//            mRvHunterRunningAdapter.notifyDataSetChanged()
        } else {//上拉加载数据
            if (data.pageNum == data.totalPage - 1) {//到底了
                mRvTaskEvaAdapter.loadMoreEnd()
            } else {//还可以上拉
                mRvTaskEvaAdapter.loadMoreComplete()
            }
            mRvTaskEvaAdapter.addData(data.content)
        }
        curPage = data.pageNum

    }

    override fun injectComponent() {
        DaggerTaskCircleComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_eva)

        initView()
    }

    private fun initView() {
        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "任务评价"
        }

        try {
            taskid = intent.getStringExtra(TASKID)
        } catch (e: Exception) {
            toast("数值传递异常")
            finish()
        }

        var linearLayoutManager = LinearLayoutManager(context)
        mRvComment.layoutManager = linearLayoutManager
        linearLayoutManager.orientation = OrientationHelper.VERTICAL
        var list = ArrayList<CommentTask>()
        mRvTaskEvaAdapter = RvTaskEvaAdapter(list)
        mRvComment.adapter = mRvTaskEvaAdapter

        mRvTaskEvaAdapter.setEmptyView(R.layout.view_empty,mRvComment)
        mSwipeRefresh.setOnRefreshListener {
            curPage = 0
            mPresenter.taskComment(taskid, curPage, pageSize)
        }

        mRvTaskEvaAdapter.setOnLoadMoreListener({
            mPresenter.taskComment(taskid, ++curPage, pageSize)
        }, mRvComment)
        mSwipeRefresh.isRefreshing = true
        mPresenter.taskComment(taskid, curPage, pageSize)
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
