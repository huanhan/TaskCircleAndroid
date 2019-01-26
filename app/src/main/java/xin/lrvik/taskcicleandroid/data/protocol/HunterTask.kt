package xin.lrvik.taskcicleandroid.data.protocol

import xin.lrvik.taskcicleandroid.data.protocol.enums.HunterTaskState
import java.sql.Timestamp

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/27.
 *
 */
class HunterTask {
    var id: String? = null
    var taskId: String? = null
    var userId: Long? = null
    var headImg: String? = null
    var name: String? = null
    var taskContext: String? = null
    var context: String? = null
    var acceptTime: Timestamp? = null
    var finishTime: Timestamp? = null
    var beginTime: Timestamp? = null
    var state: HunterTaskState? = null
    var stop: Boolean? = null
    var money: Float? = null
}