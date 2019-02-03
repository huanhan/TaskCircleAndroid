package xin.lrvik.taskcicleandroid.baselibrary.util

import android.annotation.SuppressLint
import android.content.Context
import android.telephony.TelephonyManager
import android.widget.Toast

import xin.lrvik.taskcicleandroid.baselibrary.common.BaseApplication

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/2/3.
 */
object DeviceUtil {

    fun getDeviceId(): String {
        val tm = BaseApplication.context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        @SuppressLint("MissingPermission") val deviceId = tm.deviceId
        if (deviceId == null) {
            Toast.makeText(BaseApplication.context, "设备id获取失败", Toast.LENGTH_SHORT).show()
            return ""
        } else {
            return deviceId
        }
    }
}
