package xin.lrvik.taskcicleandroid.data.protocol

import java.sql.Timestamp

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/2/1.
 *
 */

data class TaskMsg(var content: String,
                   var title: String,
                   var extraData: String)

data class ChatMsg(var title: String,
                   var content: String,
                   var userId: Long,
                   var hunterId: Long,
                   var sender: Long,
                   var hunterTaskId: String,
                   var createTime: Timestamp,
                   val userIcon: String?,
                   val hunterIcon: String?
)