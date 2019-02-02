package xin.lrvik.taskcicleandroid.ui.adapter

import android.text.format.DateFormat
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.loadCircleUrl
import xin.lrvik.taskcicleandroid.data.protocol.Message

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/2.
 *
 */
class RvMesAdapter(data: List<Message>) : BaseQuickAdapter<Message, BaseViewHolder>(R.layout.item_mes, data) {
    override fun convert(helper: BaseViewHolder, item: Message) {
        helper.setText(R.id.mTvTitle, item.title)
                .setText(R.id.mTvContent, item.context)
                .setText(R.id.mTvDate, "${DateFormat.format("yyyy年MM月dd", item.createTime)}")
    }
}
