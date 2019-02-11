package xin.lrvik.taskcicleandroid.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.MenuItem
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_search_list.*
import org.jetbrains.anko.startActivity
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.common.BaseApplication.Companion.context
import xin.lrvik.taskcicleandroid.baselibrary.common.BaseConstant
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseMvpActivity
import xin.lrvik.taskcicleandroid.baselibrary.utils.AppPrefsUtils
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.data.protocol.Task
import xin.lrvik.taskcicleandroid.data.protocol.TaskHistory
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.SearchListPresenter
import xin.lrvik.taskcicleandroid.presenter.view.SearchListView
import xin.lrvik.taskcicleandroid.ui.adapter.RvRecommendAdapter
import java.net.URLEncoder
import java.util.ArrayList

class SearchListActivity : BaseMvpActivity<SearchListPresenter>(), SearchListView {

    lateinit var query: String

    var curPage: Int = 0
    var pageSize: Int = 20
    lateinit var mRvRecommendAdapter: RvRecommendAdapter

    override fun injectComponent() {
        DaggerTaskCircleComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onTaskListResult(data: Page<Task>) {
        mRvRecommendAdapter.setNewData(data.content)

        //下拉刷新
        if (mSwipeRefresh.isRefreshing) {
            mSwipeRefresh.isRefreshing = false
            mRvRecommendAdapter.setNewData(data.content)
            if (data.pageNum == data.totalPage - 1) {
                mRvRecommendAdapter.loadMoreEnd()
            }
//            mRvHunterRunningAdapter.notifyDataSetChanged()
        } else {//上拉加载数据
            if (data.pageNum == data.totalPage - 1) {//到底了
                mRvRecommendAdapter.loadMoreEnd()
            } else {//还可以上拉
                mRvRecommendAdapter.loadMoreComplete()
            }
            mRvRecommendAdapter.addData(data.content)
        }
        curPage = data.pageNum
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_list)
        initView()
    }

    private fun initView() {
//        query = URLEncoder.encode(intent.getStringExtra("QUERY"), "utf-8")
        query = intent.getStringExtra("QUERY")

        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "$query 的搜索结果"
        }

        var linearLayoutManager = LinearLayoutManager(context)
        mRvTask.layoutManager = linearLayoutManager
        linearLayoutManager.orientation = OrientationHelper.VERTICAL
        var list = ArrayList<Task>()
        mRvRecommendAdapter = RvRecommendAdapter(list)
        mRvTask.adapter = mRvRecommendAdapter

        mRvRecommendAdapter.setEmptyView(R.layout.view_empty,mRvTask)
        mRvRecommendAdapter.setOnItemClickListener { adapter, view, position ->
            var task = adapter.data[position] as Task
            startActivity<TaskDetailActivity>(TaskDetailActivity.TASKID to task.id!!)

            var historys = AppPrefsUtils.getString(BaseConstant.KEY_SP_HISTORY)
            var taskHistorys = Gson().fromJson(historys, TaskHistory::class.java)
            if (taskHistorys.tasks.isNullOrEmpty()) {
                taskHistorys.tasks = ArrayList()
            }
            if (!taskHistorys.tasks!!.contains(task)) {
                taskHistorys.tasks!!.add(task)
                taskHistorys.size = taskHistorys.tasks!!.size
                AppPrefsUtils.putString(BaseConstant.KEY_SP_HISTORY, Gson().toJson(taskHistorys))
            }
        }

        mSwipeRefresh.setOnRefreshListener {
            curPage = 0
            mPresenter.search(query, curPage, pageSize)
        }

        mRvRecommendAdapter.setOnLoadMoreListener({
            mPresenter.search(query, ++curPage, pageSize)
        }, mRvTask)
        mSwipeRefresh.isRefreshing = true
        mPresenter.search(query, curPage, pageSize)

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
