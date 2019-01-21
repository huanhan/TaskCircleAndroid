package xin.lrvik.taskcicleandroid.data.protocol

import xin.lrvik.taskcicleandroid.data.protocol.enums.TaskState
import xin.lrvik.taskcicleandroid.data.protocol.enums.TaskType
import java.sql.Timestamp

class Task {
    var id: String? = null
    val userId: Long? = null
    var name: String? = null
    var context: String? = null
    var username: String? = null
    var headImg: String? = null
    var money: Float? = null
    val state: TaskState? = null
    val type: TaskType? = null
    var longitude: Double? = null
    var latitude: Double? = null
    val peopleNumber: Int? = null
    val beginTime: Timestamp? = null
    val deadline: Timestamp? = null
}
