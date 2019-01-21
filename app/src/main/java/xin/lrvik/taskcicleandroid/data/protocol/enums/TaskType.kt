package xin.lrvik.taskcicleandroid.data.protocol.enums

enum class TaskType private constructor(private val type: String) {

    /**
     * 只允许以个人接
     */
    SOLO("单人型"),

    /**
     * 可以多个人接
     */
    MULTI("多人型"),

    /**
     * 用户指定某个人去完成
     */
    APPOINT("指定型")
}
