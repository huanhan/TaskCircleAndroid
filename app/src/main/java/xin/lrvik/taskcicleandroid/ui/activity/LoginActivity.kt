package xin.lrvik.taskcicleandroid.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import cn.jpush.android.api.JPushInterface
import com.google.gson.Gson
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseMvpActivity
import xin.lrvik.taskcicleandroid.baselibrary.utils.AppPrefsUtils
import xin.lrvik.taskcicleandroid.baselibrary.common.UserInfo
import xin.lrvik.taskcicleandroid.baselibrary.data.protocol.TokenResult
import xin.lrvik.taskcicleandroid.baselibrary.utils.DateUtils
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.LoginPresenter
import xin.lrvik.taskcicleandroid.presenter.view.LoginView


class LoginActivity : BaseMvpActivity<LoginPresenter>(), LoginView {
    override fun injectComponent() {
        DaggerTaskCircleComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onResult(result: TokenResult) {
        finish()
        startActivity<MainActivity>()
        if (JPushInterface.isPushStopped(this)) {
            JPushInterface.resumePush(this)
        }
        JPushInterface.setAlias(this@LoginActivity, 0, "app_${result.userId}")
        //设置refreshtoken失效时间30天
        result.expires_out = (DateUtils.curTime + (1000L * 60L * 60L * 24L * 30L))
        AppPrefsUtils.putString("token", Gson().toJson(result))
        UserInfo.userId = result.userId.toLong()
        UserInfo.access_token = result.access_token
        UserInfo.refresh_token = result.refresh_token
    }

    private fun initView() {
        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "登陆"
        }

        mTvRegist.onClick {
            startActivity<RegistActivity>()
        }

        mBtnLogin.onClick {
            if (validation()) {
                mPresenter.login(mEtMobile.text.toString(), mEtPwd.text.toString())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
    }


    fun validation(): Boolean {

        if (mEtMobile.text.isEmpty()) {
            toast("账号不能为空")
            return false
        }

        if (mEtPwd.text.isEmpty()) {
            toast("密码不能为空")
            return false
        }

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
