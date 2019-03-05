package xin.lrvik.taskcicleandroid.data.protocol


import xin.lrvik.taskcicleandroid.data.protocol.enums.UserState

data class HunterAudit (
    var idCard: String? = null,
    var address: String? = null,
    var phone: String? = null,
    var idCardImgFront: String? = null,
    var idCardImgBack: String? = null,
    var state: UserState,
    val audits: ArrayList<Audit>? = null
)
