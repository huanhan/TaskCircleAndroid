package xin.lrvik.taskcicleandroid.data.protocol

import xin.lrvik.taskcicleandroid.data.protocol.enums.UserCategory
import xin.lrvik.taskcicleandroid.data.protocol.enums.UserGender
import java.sql.Timestamp

class UserAppDto {
    /**
     * 用户姓名
     */
    var name: String? = null
    /**
     * 用户账户
     */
    var username: String? = null
    /**
     * 用户性别
     */
    var gender: UserGender? = null
    /**
     * 用户分类
     */
    var category: UserCategory? = null
    /**
     * 账户余额
     */
    var money: Float? = 0.00f
    /**
     * 身份证号码
     */
    var idCard: String? = null
    /**
     * 家庭住址
     */
    var address: String? = null
    /**
     * 毕业学校
     */
    var school: String? = null
    /**
     * 职业
     */
    var major: String? = null
    /**
     * 兴趣
     */
    var interest: String? = null
    /**
     * 简介
     */
    var intro: String? = null
    /**
     * 身高
     */
    var height: Int? = null
    /**
     * 体重
     */
    var weight: Int? = null
    /**
     * 生日
     */
    var birthday: Timestamp? = null
    /**
     * 用户手机号码
     */
    var phone: String? = null
    /**
     * 用户头像
     */
    var headImg: String? = null
    /**
     * 获取所有评论数
     */
    var commentsNum: Long? = null
}
