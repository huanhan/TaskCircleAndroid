package xin.lrvik.taskcicleandroid.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.jpush.android.api.JPushInterface
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_my.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.support.v4.alert
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
            alert("是否确认退出登录？") {
                positiveButton("是") {
                    JPushInterface.stopPush(activity)
                    AppPrefsUtils.putString("token", "")
                    AppPrefsUtils.putString(BaseConstant.KEY_SP_HISTORY, "")
                    activity!!.finish()
                    startActivity<LoginActivity>()
                }
                negativeButton("否") { }
            }.show()

        }

        mRlEva.onClick {
            startActivity<UserEvaListActivity>()
        }

        mRlHunterAudit.onClick {
            startActivity<HunterAuditActivity>()
        }
        //mPresenter.detail()

        mRlAbout.onClick {
            alert("只是为了一个曾经的梦想，我们一直在奋斗！\n" +
                    "即使我们的作品无人问津，却依然还在坚持！\n" +
                    "我们不为了成为一名伟大的作家，只想把我们脑子里和心里的那个故事写好！\n" +
                    "所以无论你是谁，如果看过了我们的作品，请给我们鼓励", "关于我们") {
                positiveButton("确定") {  }
            }.show()
        }

        mRlAgreement.onClick {
            alert("1、本条所述内容是指用户使用本服务过程中所制作、上载、复制、发布、传播的任何内容，包括但不限于帐号头像、名称、用户说明等注册信息及认证资料，或文字、语音、图片、视频、图文等发送、回复或自动回复消息和相关链接页面，以及其他使用帐号或本服务所产生的内容。\n" +
                    "\n" +
                    "2、用户不得利用“任务圈”帐号或本服务制作、上载、复制、发布、传播如下法律、法规和政策禁止的内容：\n" +
                    "\n" +
                    "(1) 反对宪法所确定的基本原则的；\n" +
                    "\n" +
                    "(2) 危害国家安全，泄露国家秘密，颠覆国家政权，破坏国家统一的；\n" +
                    "\n" +
                    "(3) 损害国家荣誉和利益的；\n" +
                    "\n" +
                    "(4) 煽动民族仇恨、民族歧视，破坏民族团结的；\n" +
                    "\n" +
                    "(5) 破坏国家宗教政策，宣扬邪教和封建迷信的；\n" +
                    "\n" +
                    "(6) 散布谣言，扰乱社会秩序，破坏社会稳定的；\n" +
                    "\n" +
                    "(7) 散布淫秽、色情、赌博、暴力、凶杀、恐怖或者教唆犯罪的；\n" +
                    "\n" +
                    "(8) 侮辱或者诽谤他人，侵害他人合法权益的；\n" +
                    "\n" +
                    "(9) 不遵守法律法规底线、社会主义制度底线、国家利益底线、公民合法权益底线、社会公共秩序底线、道德风尚底线和信息真实性底线的“七条底线”要求的；\n" +
                    "\n" +
                    "(10) 含有法律、行政法规禁止的其他内容的信息。\n" +
                    "\n" +
                    "3、用户不得利用“任务圈”帐号或本服务制作、上载、复制、发布、传播如下干扰“任务圈”正常运营，以及侵犯其他用户或第三方合法权益的内容：\n" +
                    "\n" +
                    "(1) 含有任何性或性暗示的；\n" +
                    "\n" +
                    "(2) 含有辱骂、恐吓、威胁内容的；\n" +
                    "\n" +
                    "(3) 含有骚扰、垃圾广告、恶意信息、诱骗信息的；\n" +
                    "\n" +
                    "(4) 涉及他人隐私、个人信息或资料的；\n" +
                    "\n" +
                    "(5) 侵害他人名誉权、肖像权、知识产权、商业秘密等合法权利的；\n" +
                    "\n" +
                    "(6) 含有其他干扰本服务正常运营和侵犯其他用户或第三方合法权益内容的信息。", "用户协议") {
                positiveButton("确定") {  }
            }.show()
        }
    }
}
