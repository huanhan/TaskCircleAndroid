package xin.lrvik.taskcicleandroid.data.protocol

import com.chad.library.adapter.base.entity.MultiItemEntity
import xin.lrvik.taskcicleandroid.baselibrary.common.UserInfo
import java.sql.Timestamp

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/8.
 *
 */
data class Chat(val hunterId: Long,
                val userId: Long,
                val sender: Long,
                val taskId: String,
                val createTime: Timestamp,
                val context: String,
                val userIcon: String,
                val hunterIcon: String) : MultiItemEntity {

    override fun getItemType(): Int {
        return if (UserInfo.userId == sender) SEND_TYPE else RECEIVE_TYPE
    }

    companion object {
        fun toChat(chatMsg: ChatMsg):Chat {
            return Chat(chatMsg.hunterId,
                    chatMsg.userId,
                    chatMsg.sender,
                    chatMsg.taskId,
                    chatMsg.createTime,
                    chatMsg.content,
                    chatMsg.userIcon,
                    chatMsg.hunterIcon)
        }

        val RECEIVE_TYPE = 1
        val SEND_TYPE = 0
    }
}