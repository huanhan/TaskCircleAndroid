package xin.lrvik.taskcicleandroid.data.protocol


import java.sql.Timestamp

import xin.lrvik.taskcicleandroid.data.protocol.enums.UserGender

data class ModifyUser (
    /**
     * 用户编号
     */
    var id: Long,
    /**
     * 用户姓名
     */
    var name: String,
    /**
     * 用户性别
     */
    var gender: UserGender,
    /**
     * 身份证号码
     */
    var idCard: String,
    /**
     * 家庭住址
     */
    var address: String,
    /**
     * 毕业学校
     */
    var school: String,
    /**
     * 职业
     */
    var major: String,
    /**
     * 兴趣
     */
    var interest: String,
    /**
     * 简介
     */
    var intro: String,
    /**
     * 身高
     */
    var height: Int,
    /**
     * 体重
     */
    var weight: Int,
    /**
     * 生日
     */
    var birthday: Timestamp,
    /**
     * 用户手机号码
     */
    var phone: String
)
