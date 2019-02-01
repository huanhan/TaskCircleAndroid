package xin.lrvik.taskcicleandroid.util

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.support.v4.app.NotificationCompat
import xin.lrvik.taskcicleandroid.R
import android.support.v4.app.NotificationCompat.VISIBILITY_SECRET
import xin.lrvik.taskcicleandroid.baselibrary.common.BaseApplication.Companion.context

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/2/1.
 */
class NotificationUtils(base: Context) : ContextWrapper(base) {
    private var mManager: NotificationManager? = null

    private val manager: NotificationManager
        get() {
            if (mManager == null) {
                mManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
            return mManager!!
        }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        //是否绕过请勿打扰模式
        channel.canBypassDnd()
        //闪光灯
        channel.enableLights(true)
        //锁屏显示通知
        channel.lockscreenVisibility = VISIBILITY_SECRET
        //闪关灯的灯光颜色
        channel.lightColor = Color.RED
        //桌面launcher的消息角标
        channel.canShowBadge()
        //是否允许震动
        channel.enableVibration(true)
        //获取系统通知响铃声音的配置
        channel.audioAttributes
        //获取通知取到组
        channel.group
        //设置可绕过  请勿打扰模式
        channel.setBypassDnd(true)
        //设置震动模式
        channel.vibrationPattern = longArrayOf(100, 100, 200)
        //是否会有灯光
        channel.shouldShowLights()
        manager.createNotificationChannel(channel)

    }

    /**
     * 发送通知
     */
    fun sendNotification(title: String, content: String) {
        val builder = getNotification(title, content)
        manager.notify(1, builder.build())
    }

    /**
     * 发送通知
     */
    fun sendNotification(title: String, content: String, intent: Intent) {
        val builder = getNotification(title, content)
        var pIntent = PendingIntent.getActivity(context, 1, intent, 0)
        builder.setContentIntent(pIntent)
        manager.notify(1, builder.build())
    }

    private fun getNotification(title: String, content: String): NotificationCompat.Builder {
        var builder: NotificationCompat.Builder? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            builder.priority = NotificationManager.IMPORTANCE_HIGH
        } else {
            builder = NotificationCompat.Builder(applicationContext)
            builder.priority = NotificationCompat.PRIORITY_HIGH
        }
        //标题
        builder.setContentTitle(title)
        //文本内容
        builder.setContentText(content)
        //小图标
        builder.setSmallIcon(R.mipmap.ic_launcher)
        //设置点击信息后自动清除通知
        builder.setAutoCancel(true)
        return builder
    }

    /**
     * 发送通知
     */
    fun sendNotification(notifyId: Int, title: String, content: String) {
        val builder = getNotification(title, content)
        manager.notify(notifyId, builder.build())
    }

    /**
     * 发送带有进度的通知
     */
    fun sendNotificationProgress(title: String, content: String, progress: Int, intent: PendingIntent) {
        val builder = getNotificationProgress(title, content, progress, intent)
        manager.notify(0, builder.build())
    }

    /**
     * 获取带有进度的Notification
     */
    private fun getNotificationProgress(title: String, content: String,
                                        progress: Int, intent: PendingIntent): NotificationCompat.Builder {
        var builder: NotificationCompat.Builder? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            builder.priority = NotificationManager.IMPORTANCE_HIGH
        } else {
            builder = NotificationCompat.Builder(applicationContext)
            builder.priority = NotificationCompat.PRIORITY_HIGH
        }
        //标题
        builder.setContentTitle(title)
        //文本内容
        builder.setContentText(content)
        //小图标
        builder.setSmallIcon(R.mipmap.ic_launcher)
        //设置大图标，未设置时使用小图标代替，拉下通知栏显示的那个图标
        //设置大图片 BitmpFactory.decodeResource(Resource res,int id) 根据给定的资源Id解析成位图
        builder.setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
        if (progress > 0 && progress < 100) {
            //一种是有进度刻度的（false）,一种是循环流动的（true）
            //设置为false，表示刻度，设置为true，表示流动
            builder.setProgress(100, progress, false)
        } else {
            //0,0,false,可以将进度条隐藏
            builder.setProgress(0, 0, false)
            builder.setContentText("下载完成")
        }
        //设置点击信息后自动清除通知
        builder.setAutoCancel(true)
        //通知的时间
        builder.setWhen(System.currentTimeMillis())
        //设置点击信息后的跳转（意图）
        builder.setContentIntent(intent)
        return builder
    }

    companion object {

        val CHANNEL_ID = "xin.lrvik.taskcicleandroid.notify"
        private val CHANNEL_NAME = "新消息通知管理"
        private val CHANNEL_DESCRIPTION = "通知管理"
    }
}
