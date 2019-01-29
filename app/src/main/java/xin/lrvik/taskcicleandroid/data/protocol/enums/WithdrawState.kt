package xin.lrvik.taskcicleandroid.data.protocol.enums

enum class WithdrawState private constructor(val state: String) {
    SUCCESS("提现成功"),
    FAILED("提现失败"),
    EXCEPTION("提现异常"),
    AUDIT("提现审核"),
    AUDIT_CENTER("审核中")
}
