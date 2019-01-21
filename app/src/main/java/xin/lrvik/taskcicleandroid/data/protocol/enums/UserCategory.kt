package xin.lrvik.taskcicleandroid.data.protocol.enums

/**
 * @author Cyg
 * 用户类别
 */

enum class UserCategory constructor(val category: String) {

    NORMAL("普通用户"),
    HUNTER("猎刃"),
    ADMINISTRATOR("管理员"),
    DEVELOPER("开发人员");

}
