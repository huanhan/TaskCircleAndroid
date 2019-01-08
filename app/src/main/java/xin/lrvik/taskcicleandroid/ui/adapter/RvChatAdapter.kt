package xin.lrvik.taskcicleandroid.ui.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.data.protocol.Chat

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/2.
 *
 */
class RvChatAdapter(data: ArrayList<Chat>) : BaseMultiItemQuickAdapter<Chat, BaseViewHolder>(data) {

    init {
        addItemType(Chat.RECEIVE_TYPE, R.layout.receive_item)
        addItemType(Chat.SEND_TYPE, R.layout.send_item)
    }


    override fun convert(helper: BaseViewHolder, item: Chat) {
        helper.setText(R.id.mTvContent, item.content)
    }

}


