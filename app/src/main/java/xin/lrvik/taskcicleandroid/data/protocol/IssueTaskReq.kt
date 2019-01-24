package xin.lrvik.taskcicleandroid.data.protocol

import java.sql.Timestamp

/**
 * 发布任务请求
 *
 */
data class IssueTaskReq(
        var id: String,
        var money: Float,
        var peopleNumber: Int,
        var beginTime: Long,
        var deadline: Long,
        var permitAbandonMinute: Int,
        var longitude: Double,
        var latitude: Double,
        var taskRework: Boolean,
        var compensate: Boolean,
        var compensateMoney: Float
)
