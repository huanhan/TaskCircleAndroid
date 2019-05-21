package xin.lrvik.taskcicleandroid.common

import cn.jpush.android.api.JPushInterface
import com.baidu.mapapi.SDKInitializer
import xin.lrvik.taskcicleandroid.baselibrary.common.BaseApplication


/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/31.
 *
 */
class TaskCircleBaseApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        //初始化百度地图
        SDKInitializer.initialize(applicationContext)
        JPushInterface.setDebugMode(true)    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this)            // 初始化 JPush
    }
}