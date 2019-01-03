package xin.lrvik.taskcicleandroid.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_mes.*
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ui.fragment.BaseFragment
import xin.lrvik.taskcicleandroid.data.protocol.Message
import xin.lrvik.taskcicleandroid.ui.adapter.RvMesAdapter
import java.sql.Timestamp
import java.util.*

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/3.
 *
 */
class MesFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mes, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var linearLayoutManager = LinearLayoutManager(context)
        mRvMes.layoutManager = linearLayoutManager
        linearLayoutManager.orientation = OrientationHelper.VERTICAL
        var list = ArrayList<Message>()

        for (i in 0..10) {
            list.add(Message("急需$i 名跑腿小老弟", Timestamp(Date().time), "小老弟$i", ""))
        }
        mRvMes.adapter = RvMesAdapter(list)
    }
}