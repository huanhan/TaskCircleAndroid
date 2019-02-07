package xin.lrvik.taskcicleandroid.data.protocol

import java.sql.Timestamp

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/2/7.
 */
data class CommentUser(
        var commentId: Long,
        var context: String,
        var createTime: Timestamp,
        var start: Float,
        var hunterId: Long,
        var userId: Long,
        var name: String,
        var taskId: String,
        var img: String?
)
