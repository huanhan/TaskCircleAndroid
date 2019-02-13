package xin.lrvik.taskcicleandroid.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.loadUrl
import xin.lrvik.taskcicleandroid.baselibrary.common.UserInfo
import xin.lrvik.taskcicleandroid.baselibrary.ext.loadCircleUrl
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
        helper.setText(R.id.mTvContent, item.context)
        var mIvIcon = helper.getView<ImageView>(R.id.mIvIcon)
        mIvIcon.loadCircleUrl(if (item.userId == UserInfo.userId)
            if (item.userIcon.isNullOrEmpty()) R.mipmap.icon_default_user else item.userIcon
        else
            if (item.hunterIcon.isNullOrEmpty()) R.mipmap.icon_default_user else item.hunterIcon
        )

        if (item.sender == UserInfo.userId){
            //是我发的消息
            if (item.userId == UserInfo.userId){
                //我是用户
                mIvIcon.loadCircleUrl(if (item.userIcon.isNullOrEmpty()) R.mipmap.icon_default_user else item.userIcon)
            }else{
                //我是猎刃
                mIvIcon.loadCircleUrl(if (item.hunterIcon.isNullOrEmpty()) R.mipmap.icon_default_user else item.hunterIcon)
            }

        }else{
            //是对面发送的消息
            if (item.userId == UserInfo.userId){
                //我是用户那对面就是猎刃
                mIvIcon.loadCircleUrl(if (item.hunterIcon.isNullOrEmpty()) R.mipmap.icon_default_user else item.hunterIcon)
            }else{
                //我是猎刃那对面就是用户
                mIvIcon.loadCircleUrl(if (item.userIcon.isNullOrEmpty()) R.mipmap.icon_default_user else item.userIcon)
            }
        }
    }

}



