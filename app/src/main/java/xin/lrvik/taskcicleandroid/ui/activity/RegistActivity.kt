package xin.lrvik.taskcicleandroid.ui.activity

import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_regist.*
import org.jetbrains.anko.toast
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseActivity

class RegistActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regist)
        initView()
    }

    private fun initView() {
        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "注册"
        }

        mBtnRegister.onClick {
            if (validation()) {
                //todo 调用注册接口

            }
        }
    }

    fun validation(): Boolean {

        if (mEtMobile.text.isEmpty()) {
            toast("账号至少8位")
            return false
        }

        if (mEtVerifyCode.text.isEmpty()) {
            toast("验证码不能为空")
            return false
        }

        if (mEtPwd.text.isEmpty()) {
            toast("请输入密码（6-20位字母或数字）")
            return false
        }

        if (mEtPwdConfirm.text.toString() != mEtPwd.text.toString()) {
            toast("两次密码不一致")
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
