package xin.lrvik.taskcicleandroid.data.protocol

enum class PushMsgState private constructor(val state: String) {
    TASK("任务"),
    HUNTER_TASK("猎刃任务"),
    HUNTER_LIST("猎刃执行列表"),
    CHAT("聊天"),
    NOTICE("公告")
}
