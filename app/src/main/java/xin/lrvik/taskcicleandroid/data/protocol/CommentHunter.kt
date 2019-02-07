package xin.lrvik.taskcicleandroid.data.protocol

import java.sql.Timestamp

data class CommentHunter(
        var commentId: Long,
        var context: String,
        var createTime: Timestamp,
        var start: Float,
        var hunterId: Long,
        var name: String,
        var hunterTaskId: String
)
