package xin.lrvik.taskcicleandroid.data.protocol.enums


enum class HunterTaskState constructor(val state: String) {

    /**
     * 猎刃点击接取任务
     */
    RECEIVE("任务接取"),
    BEGIN("开始"),
    EXECUTE("正在执行"),
    TASK_COMPLETE("任务完成"),
    AWAIT_USER_AUDIT("等待用户审核"),
    USER_AUDIT("用户审核中"),
    AWAIT_SETTLE_ACCOUNTS("等待结算"),
    SETTLE_ACCOUNTS_SUCCESS("结算成功"),
    SETTLE_ACCOUNTS_EXCEPTION("结算异常"),
    END_OK("任务结束并且完成"),
    END_NO("任务结束并且未完成"),
    USER_AUDIT_FAILURE("用户审核失败"),
    COMMIT_ADMIN_AUDIT("任务完成，提交管理员审核"),
    ADMIN_AUDIT("管理员审核中"),
    ALLOW_REWORK_ABANDON_HAVE_COMPENSATE("允许重做，放弃要补偿"),
    ALLOW_REWORK_ABANDON_NO_COMPENSATE("允许重做，放弃不用补偿"),
    NO_REWORK_NO_COMPENSATE("不能重做，不用补偿"),
    NO_REWORK_HAVE_COMPENSATE("不能重做，要补偿"),
    AWAIT_COMPENSATE("等待补偿"),
    COMPENSATE_SUCCESS("补偿成功"),
    COMPENSATE_EXCEPTION("补偿异常"),
    TASK_ABANDON("任务放弃"),
    TASK_BE_ABANDON("任务被放弃"),
    WITH_USER_NEGOTIATE("与用户协商"),
    WITH_HUNTER_NEGOTIATE("用户申请放弃"),
    USER_REPULSE("用户拒绝猎刃放弃"),
    HUNTER_REPULSE("猎刃拒绝用户放弃"),
    COMMIT_TO_ADMIN("提交管理员放弃申请"),
    WITH_ADMIN_NEGOTIATE("管理员参与协商"),
    NONE("无状态");

}
