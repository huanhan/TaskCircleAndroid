package xin.lrvik.taskcicleandroid.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_task_state.*
import org.jetbrains.anko.support.v4.startActivity
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.R.id.mRvTask
import xin.lrvik.taskcicleandroid.baselibrary.common.BaseApplication.Companion.context
import xin.lrvik.taskcicleandroid.baselibrary.ui.fragment.BaseFragment
import xin.lrvik.taskcicleandroid.baselibrary.ui.fragment.BaseMvpFragment
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.data.protocol.Task
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.TaskStatePresenter
import xin.lrvik.taskcicleandroid.presenter.view.TaskStateView
import xin.lrvik.taskcicleandroid.ui.activity.PostTaskActivity
import xin.lrvik.taskcicleandroid.ui.activity.ReleaseTaskActivity
import xin.lrvik.taskcicleandroid.ui.adapter.RvTaskStateAdapter
import java.util.*

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/3.
 *
 */

class TaskStateFragment : BaseMvpFragment<TaskStatePresenter>(), TaskStateView {

    lateinit var type: String
    lateinit var mRvTaskStateAdapter: RvTaskStateAdapter

    override fun injectComponent() {
        DaggerTaskCircleComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onTaskStateResult(data: Page<Task>) {
        mRvTaskStateAdapter.setNewData(data.content)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_task_state, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.type = arguments!!.getString(TYPE)

        var linearLayoutManager = LinearLayoutManager(context)
        mRvTask.layoutManager = linearLayoutManager
        linearLayoutManager.orientation = OrientationHelper.VERTICAL
        var list = ArrayList<Task>()
        mRvTaskStateAdapter = RvTaskStateAdapter(list)
        mRvTask.adapter = mRvTaskStateAdapter
        mPresenter.getStateTaskData(type, 0, 20)

        mRvTaskStateAdapter.setOnItemClickListener { adapter, view, position ->
            var task = adapter.data[position] as Task
            startActivity<PostTaskActivity>(PostTaskActivity.MODE to PostTaskActivity.Mode.LOOK.name, PostTaskActivity.TASKID to task.id!!)
        }
        mRvTaskStateAdapter.setOnItemChildClickListener { adapter, view, position ->
            var task = adapter.data[position] as Task
            when (view.id) {
                R.id.mBtModify -> {
                    startActivity<PostTaskActivity>(PostTaskActivity.MODE to PostTaskActivity.Mode.MODIFY.name, PostTaskActivity.TASKID to task.id!!)
                }
                R.id.mBtSubmitAudit -> {

                }
                R.id.mBtCancelAudit -> {

                }
                R.id.mBtRelease -> {
                    startActivity<ReleaseTaskActivity>(ReleaseTaskActivity.TASKID to task.id!!)
                }
                R.id.mBtOut -> {

                }
                R.id.mBtUpper -> {

                }
                R.id.mBtAbandon -> {

                }
                R.id.mBtCancelAbandon -> {

                }
            }

        }

    }


    companion object {
        val TYPE = "TYPE"

        fun newInstance(type: String): TaskStateFragment {
            var taskClassFragment = TaskStateFragment()
            var bundle = Bundle()
            bundle.putString(TYPE, type)
            taskClassFragment.arguments = bundle
            return taskClassFragment
        }

    }
}
