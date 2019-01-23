package xin.lrvik.taskcicleandroid.data.protocol

import java.sql.Timestamp

/**
 * 发布任务请求
 *
 */
class IssueTaskReq {

    var id: String? = null
    var money: Float? = null
    var peopleNumber: Int? = null
    var beginTime: Timestamp? = null
    var deadline: Timestamp? = null
    var permitAbandonMinute: Int? = null
    var longitude: Double? = null
    var latitude: Double? = null
    var taskRework: Boolean? = null
    var compensate: Boolean? = null
    var compensateMoney: Float? = null
}
