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
    var hunterId: Long? = null
    var hunterName: String? = null
    var headImg: String? = null
    var hunterHeadImg: String? = null
    var name: String? = null
    var taskContext: String? = null
    var context: String? = null //猎刃放弃任务理由
    var acceptTime: Timestamp? = null
    var finishTime: Timestamp? = null
    var beginTime: Timestamp? = null
    var state: HunterTaskState? = null
    var stop: Boolean? = null
    var money: Float? = null
    val curStep: Int? = null
    val auditContext: String? = null //用户审核失败理由，拒绝猎刃放弃理由
    val hunterRejectContext: String? = null //猎刃拒绝理由
}