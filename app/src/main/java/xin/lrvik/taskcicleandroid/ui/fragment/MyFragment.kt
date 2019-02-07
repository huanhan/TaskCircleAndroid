package xin.lrvik.taskcicleandroid.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_my.*
import org.jetbrains.anko.support.v4.startActivity
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.common.BaseConstant.Companion.KEY_SP_HISTORY
import xin.lrvik.taskcicleandroid.baselibrary.ext.loadCircleUrl
import xin.lrvik.taskcicleandroid.baselibrary.ext.loadUrl
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.baselibrary.ui.fragment.BaseMvpFragment
import xin.lrvik.taskcicleandroid.baselibrary.utils.AppPrefsUtils
import xin.lrvik.taskcicleandroid.common.UserInfo
import xin.lrvik.taskcicleandroid.data.protocol.TaskHistory
import xin.lrvik.taskcicleandroid.data.protocol.User
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.MyPresenter
import xin.lrvik.taskcicleandroid.presenter.view.MyView
import xin.lrvik.taskcicleandroid.ui.activity.*


class MyFragment : BaseMvpFragment<MyPresenter>(), MyView {

    override fun injectComponent() {
        DaggerTaskCircleComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onUserResult(data: User) {
        UserInfo.money = data.money ?: 0f
        UserInfo.userId = data.id ?: 0

        mTvUserName.visibility = View.VISIBLE
        mBtExitLogin.visibility = View.VISIBLE
        mTvLoginOrRegist.visibility = View.GONE
        mTvUserName.text = data.name
        mTvMoney.text = "${data.money}元"

        mIvIcon.loadCircleUrl(data.headImg ?: "")

        var historys = AppPrefsUtils.getString(KEY_SP_HISTORY)
        if (historys.isNullOrEmpty()) {
            var taskHistory = TaskHistory()
            taskHistory.size = 0
            taskHistory.tasks = ArrayList()
            var taskHistoryStr = Gson().toJson(taskHistory)
            AppPrefsUtils.putString(KEY_SP_HISTORY, taskHistoryStr)
            historys = taskHistoryStr
        }

        var taskHistorys = Gson().fromJson(historys, TaskHistory::class.java)

        //历史记录从本地获取
        mTvHistory.text = "${taskHistorys.size}"
        mTvEvaluate.text = "${data.commentsNum}条"

        mRlHistory.onClick {
            startActivity<TaskHistoryActivity>()
        }

        mTvUserName.onClick {
            //todo 跳转到个人信息
            startActivity<UserDetailActivity>()
        }

        mIvIcon.onClick {
            //todo 跳转到个人信息
            startActivity<UserDetailActivity>()
        }

        mRlWallet.onClick {
            startActivity<WalletActivity>()
        }

        mBtExitLogin.onClick {
            //todo 退出登陆
//            NotificationUtils(activity!!).sendNotification("test", "测试通知")
        }

        mRlEva.onClick {
            startActivity<UserEvaListActivity>()
        }
    }

    override fun onResume() {
        super.onResume()
        mPresenter.detail()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mTvUserName.visibility = View.GONE
        mTvLoginOrRegist.visibility = View.VISIBLE

        //设置系统通知
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

        mIvIcon.onClick {
            startActivity<LoginActivity>()
        }

        mTvLoginOrRegist.onClick {
            startActivity<LoginActivity>()
        }

    }
}
