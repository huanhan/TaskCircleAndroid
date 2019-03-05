package xin.lrvik.taskcicleandroid.data.protocol


import java.sql.Timestamp
import java.util.ArrayList
import xin.lrvik.taskcicleandroid.data.protocol.enums.TaskState
import xin.lrvik.taskcicleandroid.data.protocol.enums.TaskType

data class TaskDetail(

        var id: String? = null,
        var userId: Long? = null,
        var name: String? = null,
        var context: String? = null,
        var username: String? = null,
        var headImg: String? = null,
        var money: Float? = null,
        var state: TaskState? = null,
        var type: TaskType? = null,
        var peopleNumber: Int? = null,
        var createTime: Timestamp? = null,
        var beginTime: Timestamp? = null,
        var deadline: Timestamp? = null,
        var auditTime: Timestamp? = null,
        var adminAuditTime: Timestamp? = null,
        var issueTime: Timestamp? = null,
        var originalMoney: Float? = null,
        var compensateMoney: Float? = null,
        var permitAbandonMinute: Int? = null,
        var longitude: Double? = null,
        var latitude: Double? = null,
        var address: String? = null,
        var taskRework: Boolean? = null,
        var compensate: Boolean? = null,
        var pick: Boolean,

        var taskClassifyAppDtos: ArrayList<TaskClass>? = null,
        var taskSteps: ArrayList<TaskStep>? = null,
        val audits: Collection<Audit>? = null
)
