package xin.lrvik.taskcicleandroid.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_task_class.*
import org.jetbrains.anko.support.v4.startActivity
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ui.fragment.BaseMvpFragment
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.data.protocol.Task
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.TaskClassPresenter
import xin.lrvik.taskcicleandroid.presenter.view.TaskClassView
import xin.lrvik.taskcicleandroid.ui.activity.PostTaskActivity
import xin.lrvik.taskcicleandroid.ui.activity.TaskDetailActivity
import xin.lrvik.taskcicleandroid.ui.adapter.RvRecommendAdapter
import java.util.*

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/3.
 *
 */

class TaskClassFragment : BaseMvpFragment<TaskClassPresenter>(), TaskClassView {

    var classId: Long = 0
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_task_class, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.classId = arguments!!.getLong(CLASSID)

        var linearLayoutManager = LinearLayoutManager(context)
        mRvTask.layoutManager = linearLayoutManager
        linearLayoutManager.orientation = OrientationHelper.VERTICAL
        var list = ArrayList<Task>()
        mRvRecommendAdapter = RvRecommendAdapter(list)
        mRvTask.adapter = mRvRecommendAdapter

        mRvRecommendAdapter.setOnItemClickListener { adapter, view, position ->
            var task = adapter.data[position] as Task
            startActivity<TaskDetailActivity>(TaskDetailActivity.TASKID to task.id!!)
        }

        mSwipeRefresh.setOnRefreshListener {
            curPage = 0
            mPresenter.queryByClassid(classId, curPage, pageSize)
        }

        mRvRecommendAdapter.setOnLoadMoreListener({
            mPresenter.queryByClassid(classId, ++curPage, pageSize)
        }, mRvTask)
        mSwipeRefresh.isRefreshing = true
        mPresenter.queryByClassid(classId, curPage, pageSize)
    }


    companion object {
        val CLASSID = "CLASSID"

        fun newInstance(type: Long): TaskClassFragment {
            var fragment = TaskClassFragment()
            var bundle = Bundle()
            bundle.putLong(CLASSID, type)
            fragment.arguments = bundle
            return fragment
        }

    }
}
