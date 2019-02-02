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

//            NotificationUtils(activity!!).sendNotification("test", "测试通知")

        }
    }
}
