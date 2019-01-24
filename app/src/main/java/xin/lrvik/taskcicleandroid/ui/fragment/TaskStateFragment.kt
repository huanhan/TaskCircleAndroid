package xin.lrvik.taskcicleandroid.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_task_state.*
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ui.fragment.BaseFragment
import xin.lrvik.taskcicleandroid.data.protocol.Task
import xin.lrvik.taskcicleandroid.ui.adapter.RvTaskStateAdapter
import java.util.*

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/3.
 *
 */

class TaskStateFragment : BaseFragment() {

    lateinit var type: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_task_state, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.type = arguments!!.getString(TYPE)

        var linearLayoutManager = LinearLayoutManager(context)
        mRvTask.layoutManager = linearLayoutManager
        linearLayoutManager.orientation = OrientationHelper.VERTICAL
        var list = ArrayList<Task>()
        mRvTask.adapter = RvTaskStateAdapter(list)
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
