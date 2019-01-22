package xin.lrvik.taskcicleandroid.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_task_class.*
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ui.fragment.BaseMvpFragment
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.data.protocol.Task
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.TaskClassPresenter
import xin.lrvik.taskcicleandroid.presenter.view.TaskClassView
import xin.lrvik.taskcicleandroid.ui.adapter.RvRecommendAdapter
import java.util.*

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/3.
 *
 */

class TaskClassFragment : BaseMvpFragment<TaskClassPresenter>(), TaskClassView {

    var classId: Long = 0
    lateinit var rvRecommendAdapter: RvRecommendAdapter

    override fun injectComponent() {
        DaggerTaskCircleComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onTaskListResult(data: Page<Task>) {
        rvRecommendAdapter.setNewData(data.content)
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
        rvRecommendAdapter = RvRecommendAdapter(list)
        mRvTask.adapter = rvRecommendAdapter
        mPresenter.queryByClassid(classId, 0, 10)
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
