package xin.lrvik.taskcicleandroid.data.protocol.enums

enum class TaskState constructor(val state: String) {

    /**
     * 用户新建一条任务
     */
    NEW_CREATE("新建"),
    /**
     * 用户将新建的任务提交审核
     */
    AWAIT_AUDIT("等待审核"),
    /**
     * 任务被管理员查看
     */
    AUDIT("审核中"),
    /**
     * 管理员点击提交审核，并设置审核状态
     */
    AUDIT_FAILURE("任务审核失败"),
    /**
     * 管理员点击提交审核，并设置审核状态
     */
    AUDIT_SUCCESS("任务审核成功"),
    /**
     * 审核成功的任务，用户缴纳定金后
     */
    OK_ISSUE("任务可以发布"),
    /**
     * 用户点击发布
     */
    ISSUE("任务发布中"),
    /**
     * 用户主动禁止，系统自动禁止（任务被接数量超出可接数量，用户账户金额不足）
     */
    FORBID_RECEIVE("任务禁止被接取"),
    /**
     * 任务被用户撤回
     */
    OUT("任务被撤回"),
    /**
     * 用户发布任务被猎刃完成
     */
    FINISH("任务完成"),
    /**
     * 用户点击了任务删除按钮，并经过一系列的判断后
     */
    DELETE_OK("任务被删除"),
    /**
     * 用户提交放弃申请
     */
    ABANDON_COMMIT("用户提交放弃的申请"),
    /**
     * 用户点击放弃任务按钮，并经过一系列判断后
     */
    ABANDON_OK("任务被放弃"),
    /**
     * 任务被猎刃接取后，用户放弃任务，并且系统默认不允许用户放弃时
     */
    USER_HUNTER_NEGOTIATE("与猎刃协商中"),
    /**
     * 猎刃拒绝与用户协商
     */
    HUNTER_REJECT("猎刃拒绝协商"),
    /**
     * 用户将放弃申请提交给管理员
     */
    COMMIT_AUDIT("提交管理员协商"),
    /**
     * 猎刃拒绝用户放弃任务，并且用户提交管理员协调解决
     */
    ADMIN_NEGOTIATE("管理员协商中"),
    /**
     * 查询专用
     */
    HUNTER_COMMIT("猎刃放弃任务")


}
