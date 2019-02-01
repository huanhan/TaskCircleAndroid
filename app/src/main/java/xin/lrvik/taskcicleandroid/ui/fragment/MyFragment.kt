package xin.lrvik.taskcicleandroid.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_my.*
import org.jetbrains.anko.support.v4.startActivity
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.baselibrary.ui.fragment.BaseFragment
import xin.lrvik.taskcicleandroid.common.UserInfo
import xin.lrvik.taskcicleandroid.ui.activity.LoginActivity
import xin.lrvik.taskcicleandroid.ui.activity.WalletActivity
import xin.lrvik.taskcicleandroid.util.NotificationUtils
import android.provider.Settings.EXTRA_APP_PACKAGE
import android.provider.Settings.EXTRA_CHANNEL_ID


class MyFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my, null)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mTvMoney.text = "${UserInfo.money}元"
        mTvStart.text = "${UserInfo.start}"
        mTvEvaluate.text = "${UserInfo.comment}条"

        mTvLoginOrRegist.onClick {
            startActivity<LoginActivity>()
        }

        mRlWallet.onClick {
            startActivity<WalletActivity>()
        }
        mRlMsgSetting.onClick {
            var mIntent = Intent()
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            if (Build.VERSION.SDK_INT >= 9) {
                mIntent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
                mIntent.data = Uri.fromParts("package", context!!.getPackageName(), null)
            } else if (Build.VERSION.SDK_INT <= 8) {
                mIntent.action = Intent.ACTION_VIEW;
                mIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails")
                mIntent.putExtra("com.android.settings.ApplicationPkgName", context!!.packageName)
            }
            context!!.startActivity(mIntent)

        }

        mBtExitLogin.onClick {
            UserInfo.userId = if (UserInfo.userId == 6L) 13 else 6

            NotificationUtils(activity!!).sendNotification("test", "测试通知")

            /*val manger = activity!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

            val channel = NotificationChannel("2",
                    "新消息通知",
                    NotificationManager.IMPORTANCE_HIGH)
            channel.enableVibration(true)
            channel.enableLights(true)
            channel.lockscreenVisibility = Notification.VISIBILITY_SECRET
            channel.setBypassDnd(true)

            channel.description = "新消息通知的描述"

            manger!!.createNotificationChannel(channel)

            val builder = NotificationCompat.Builder(activity!!, "2")
            builder.setContentTitle("测试横幅通知")
            builder.setContentText("测试横幅通知的内容")

//            builder.setDefaults(NotificationCompat.DEFAULT_ALL)

            builder.setSmallIcon(R.mipmap.ic_launcher)
            builder.setLargeIcon(BitmapFactory.decodeResource(activity!!.getResources(), R.mipmap.ic_launcher))

            var intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(ChatActivity.HUNTERID, 13)
            intent.putExtra(ChatActivity.TASKID, "20190201040203278856064")
            intent.putExtra(ChatActivity.USERID, 6)

            var pIntent = PendingIntent.getActivity(context, 1, intent, 0)
            builder.setContentIntent(pIntent)
//            builder.setFullScreenIntent(pIntent, true)
//            builder.setAutoCancel(true)

            val notification = builder.build()
            manger!!.notify(1, notification)

            toast("横幅")

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                Toast.makeText(context, "此类通知在Android 5.0以上版本才会有横幅有效！", Toast.LENGTH_SHORT).show()

            }

            var timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    manger.cancel(1)
                }
            }, 5000)*/

        }
    }
}
