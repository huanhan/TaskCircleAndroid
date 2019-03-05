package xin.lrvik.taskcicleandroid.data.protocol.enums

enum class AuditState private constructor(val state: String) {

    PASS("通过"),
    NO_PASS("不通过"),
    REWORK("重做任务"),
    ABANDON_COMPENSATE("放弃任务，并且补偿"),
    ABANDON_NOT_COMPENSATE("放弃任务，并且不用补偿"),
    TASK_OK("任务直接完成")

}
