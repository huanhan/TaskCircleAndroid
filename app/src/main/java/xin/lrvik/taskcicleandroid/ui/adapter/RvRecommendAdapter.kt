package xin.lrvik.taskcicleandroid.ui.adapter

import android.text.format.DateFormat
import android.widget.ImageView
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.utils.DistanceUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.loadUrl
import xin.lrvik.taskcicleandroid.common.UserInfo
import xin.lrvik.taskcicleandroid.data.protocol.Task
import xin.lrvik.taskcicleandroid.data.protocol.Task2

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/2.
 *
 */
class RvRecommendAdapter(data: List<Task>) : BaseQuickAdapter<Task, BaseViewHolder>(R.layout.item_recommend, data) {
    override fun convert(helper: BaseViewHolder, item: Task) {
        var distance = DistanceUtil.getDistance(LatLng(UserInfo.latitude, UserInfo.longitude), LatLng(item.latitude
                ?: 0.0, item.longitude ?: 0.0)).toInt()

        var dis = if (distance < 1000) "$distance 米" else "${distance / 1000} 千米"
        helper.setText(R.id.mTvTaskName, item.name)
                .setText(R.id.mTvContext, item.context)
                .setText(R.id.mTvMoney, "${item.money} 元")
                .setText(R.id.mTvDistance, "$dis")
                .setText(R.id.mTvTime, "${DateFormat.format("yyyy年MM月dd", item.beginTime)}~${DateFormat.format("yyyy年MM月dd", item.deadline)}")
        var imageView = helper.getView<ImageView>(R.id.mIvIcon)
        imageView.loadUrl(item.headImg ?: R.mipmap.def)
    }
}
