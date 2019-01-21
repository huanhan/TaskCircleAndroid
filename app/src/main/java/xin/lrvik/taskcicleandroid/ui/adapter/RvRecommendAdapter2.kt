package xin.lrvik.taskcicleandroid.ui.adapter

import android.text.format.DateFormat
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.data.protocol.Task2

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/2.
 *
 */
class RvRecommendAdapter2(data: List<Task2>) : BaseQuickAdapter<Task2, BaseViewHolder>(R.layout.item_recommend, data) {
    override fun convert(helper: BaseViewHolder, item: Task2) {
        helper.setText(R.id.mTvTaskName, item.name)
                .setText(R.id.mTvContext, item.context)
                .setText(R.id.mTvMoney, item.money+"元")
                .setText(R.id.mTvDistance, item.distance)
                .setText(R.id.mTvTime, "${DateFormat.format("yyyy年MM月dd",item.beginTime)}~${DateFormat.format("yyyy年MM月dd",item.deadline)}")
        var imageView = helper.getView<ImageView>(R.id.mIvIcon)

    }
}
