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
import xin.lrvik.taskcicleandroid.data.protocol.Task2
import xin.lrvik.taskcicleandroid.ui.adapter.RvRecommendAdapter2
import java.sql.Timestamp
import java.util.*

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/3.
 *
 */

class TaskStateFragment : BaseFragment(){

    lateinit var type: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_task_state, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.type = arguments!!.getString(TYPE)

        var linearLayoutManager = LinearLayoutManager(context)
        mRvTask.layoutManager = linearLayoutManager
        linearLayoutManager.orientation = OrientationHelper.VERTICAL
        var list = ArrayList<Task2>()

        for (i in 0..10) {
            list.add(Task2("$i ", "急需$i 名跑腿小老弟", "${i * 5}", "跑腿$i",
                    "马上需要跑腿小老弟一只啊，有没有跑腿小老弟马上就要跑了。马上需要跑腿小老弟一只啊，有没有跑腿小老弟马上就要跑了。马上需要跑腿小老弟一只啊，" +
                            "有没有跑腿小老弟马上就要跑了。马上需要跑腿小老弟一只啊，有没有跑腿小老弟马上就要跑了。马上需要跑腿小老弟一只啊，有没有跑腿小老弟马上就要跑了。",
                    i + 5, Timestamp(Date().time), Timestamp(Date().time + 1000 * 60 * 60 * 24), "200米", i + 5, R.mipmap.def))
        }
        mRvTask.adapter = RvRecommendAdapter2(list)
    }



    companion object {
        val TYPE = "TYPE"

        fun newInstance(type: String): TaskStateFragment {
            var f = TaskStateFragment()
            var b = Bundle()
            b.putString(TYPE, type)
            f.setArguments(b)
            return f
        }

    }
}
