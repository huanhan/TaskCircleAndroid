package xin.lrvik.taskcicleandroid.ui.adapter

import android.text.format.DateFormat
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.loadCircleUrl
import xin.lrvik.taskcicleandroid.baselibrary.ext.loadUrl
import xin.lrvik.taskcicleandroid.data.protocol.Message
import xin.lrvik.taskcicleandroid.data.protocol.Task

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/2.
 *
 */
class RvMesAdapter(data: List<Message>) : BaseQuickAdapter<Message, BaseViewHolder>(R.layout.item_mes, data) {
    override fun convert(helper: BaseViewHolder, item: Message) {
        helper.setText(R.id.mTvName, item.name)
                .setText(R.id.mTvContent, item.context)
                .setText(R.id.mTvDate, "${DateFormat.format("yyyy年MM月dd", item.createTime)}")
        var imageView = helper.getView<ImageView>(R.id.mIvIcon)
        imageView.loadCircleUrl(R.drawable.def)
    }
}
