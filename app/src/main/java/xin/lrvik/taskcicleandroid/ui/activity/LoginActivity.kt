package xin.lrvik.taskcicleandroid.ui.activity

import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseActivity
import cn.jpush.android.api.JPushInterface
import xin.lrvik.taskcicleandroid.common.UserInfo


class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
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
                //todo 调用登陆逻辑
                //todo 设置极光别名，到时候要加入到登陆信息回调然后设置别名
                JPushInterface.setAlias(this@LoginActivity,0,"app_${mEtMobile.text}")
            }
        }
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
