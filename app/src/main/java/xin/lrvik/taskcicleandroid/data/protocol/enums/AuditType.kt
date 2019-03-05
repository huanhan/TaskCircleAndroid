package xin.lrvik.taskcicleandroid.data.protocol.enums

enum class AuditType private constructor(val type: String) {

    HUNTER("猎刃审核"),
    WITHDRAW("提现审核"),
    PAY("充值审核"),
    TASK("新建任务审核"),
    USER_FAILURE_TASK("用户放弃任务"),
    HUNTER_FAILURE_TASK("猎刃放弃任务"),
    HUNTER_OK_TASK("猎刃完成任务")

}
