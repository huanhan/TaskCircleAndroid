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
import xin.lrvik.taskcicleandroid.baselibrary.common.BaseConstant
import xin.lrvik.taskcicleandroid.baselibrary.common.BaseConstant.Companion.KEY_SP_HISTORY
import xin.lrvik.taskcicleandroid.baselibrary.ext.loadCircleUrl
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.baselibrary.ui.fragment.BaseMvpFragment
import xin.lrvik.taskcicleandroid.baselibrary.utils.AppPrefsUtils
import xin.lrvik.taskcicleandroid.baselibrary.common.UserInfo
import xin.lrvik.taskcicleandroid.data.protocol.TaskHistory
import xin.lrvik.taskcicleandroid.data.protocol.User
import xin.lrvik.taskcicleandroid.data.protocol.enums.UserCategory
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
        UserInfo.name = data.name ?: ""
        UserInfo.userId = data.id ?: 0
        UserInfo.isHunter = data.category == UserCategory.HUNTER
        UserInfo.headImg = data.headImg ?: ""
        UserInfo.commentsNum = data.commentsNum ?: 0

        mTvUserName.text = data.name
        mTvMoney.text = "${data.money}元"
        mTvEvaluate.text = "${data.commentsNum}条"

        mIvIcon.loadCircleUrl((if (UserInfo.headImg.isNullOrEmpty()) R.mipmap.icon_default_user else UserInfo.headImg))
        mTvHunter.visibility = if (UserInfo.isHunter) View.VISIBLE else View.GONE
    }

    var refresh: Boolean = false

    override fun onResume() {
        super.onResume()
        if (refresh) {
            refresh = false
            mPresenter.detail()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mTvUserName.visibility = View.VISIBLE
        mBtExitLogin.visibility = View.VISIBLE

        mRlHunterAudit.visibility = if (UserInfo.isHunter) View.GONE else View.VISIBLE

        mTvUserName.text = UserInfo.name
        mTvMoney.text = "${UserInfo.money}元"
        mTvEvaluate.text = "${UserInfo.commentsNum}条"
        mIvIcon.loadCircleUrl((if (UserInfo.headImg.isNullOrEmpty()) R.mipmap.icon_default_user else UserInfo.headImg))
        mTvHunter.visibility = if (UserInfo.isHunter) View.VISIBLE else View.GONE
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

        mRlHistory.onClick {
            startActivity<TaskHistoryActivity>()
        }

        mTvUserName.onClick {
            refresh = true
            startActivity<UserDetailActivity>()
        }

        mIvIcon.onClick {
            refresh = true
            startActivity<UserDetailActivity>()
        }

        mRlWallet.onClick {
            startActivity<WalletActivity>()
        }

        mBtExitLogin.onClick {
            AppPrefsUtils.putString("token", "")
            AppPrefsUtils.putString(BaseConstant.KEY_SP_HISTORY, "")
            activity!!.finish()
            startActivity<LoginActivity>()
        }

        mRlEva.onClick {
            startActivity<UserEvaListActivity>()
        }

        mRlHunterAudit.onClick {
            startActivity<HunterAuditActivity>()
        }
        //mPresenter.detail()
    }
}
