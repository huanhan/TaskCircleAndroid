package xin.lrvik.taskcicleandroid.data.protocol

import java.sql.Timestamp

import xin.lrvik.taskcicleandroid.data.protocol.enums.HunterTaskState

class HunterTaskAndStep {

    var userId: Long? = null//用户id
    var taskId: String? = null//任务id
    var hunterTaskId: String? = null//猎刃任务id
    var hunterId: Long? = null//猎刃id
    var name: String? = null//任务名
    var context: String? = null//任务简介
    var acceptTime: Timestamp? = null//接取时间
    var beginTime: Timestamp? = null//可执行开始时间
    var deadline: Timestamp? = null//可执行截至时间
    var taskFinishTime: Timestamp? = null//任务完成时间
    var auditTime: Timestamp? = null//提交审核时间
    var taskBeginTime: Timestamp? = null//任务开始时间
    val currStep: Int? = null//任务开始时间
    val totalStep: Int? = null//任务开始时间
    var state: HunterTaskState? = null//任务状态
    var stop: Boolean? = null

    var taskSteps: ArrayList<HunterRunningStep>? = null
}
