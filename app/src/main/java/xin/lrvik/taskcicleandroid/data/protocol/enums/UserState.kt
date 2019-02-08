package xin.lrvik.taskcicleandroid.data.protocol.enums

/**
 * 用户状态
 */
enum class UserState private constructor(val state: String) {
    NORMAL("未审核"),
    AUDIT_HUNTER("猎刃审核"),
    AUDIT_CENTER("审核中"),
    AUDIT_FAILE("审核失败"),
    AUDIT_SUCCESS("审核成功"),
    STOP("停用"),
    DELETE("删除")
}
