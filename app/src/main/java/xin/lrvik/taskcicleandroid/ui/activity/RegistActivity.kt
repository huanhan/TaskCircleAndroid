package xin.lrvik.taskcicleandroid.ui.activity

import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import kotlinx.android.synthetic.main.activity_regist.*
import org.jetbrains.anko.toast
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.common.BaseApplication.Companion.context
import xin.lrvik.taskcicleandroid.baselibrary.common.BaseConstant
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseMvpActivity
import xin.lrvik.taskcicleandroid.baselibrary.util.DeviceUtil
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.RegistPresenter
import xin.lrvik.taskcicleandroid.presenter.view.RegistView

class RegistActivity : BaseMvpActivity<RegistPresenter>(), RegistView {
    override fun injectComponent() {
        DaggerTaskCircleComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onResult(result: String) {
        toast(result)
        if (result == "注册成功") {
            finish()
        } else {
            refreshVerifyCode()
        }
    }

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
                mPresenter.register(mEtMobile.text.toString(), mEtPwd.text.toString(), mEtVerifyCode.text.toString())
            }
        }

        mIvVerifyCode.onClick {
            refreshVerifyCode()
        }

        refreshVerifyCode()
    }

    private fun refreshVerifyCode() {
        var glideUrl = GlideUrl("${BaseConstant.SERVICE_ADDRESS}app/user/${(Math.random() * 10000).toInt()}/code/Image", LazyHeaders.Builder()
                .addHeader("deviceId", DeviceUtil.getDeviceId())
                .build())

        Glide.with(context)
                .load(glideUrl)
                .into(mIvVerifyCode)
    }

    fun validation(): Boolean {

        if (mEtMobile.text.isEmpty() && mEtMobile.text.length <= 8) {
            toast("账号至少8位")
            return false
        }

        if (mEtVerifyCode.text.isEmpty()) {
            toast("验证码不能为空")
            return false
        }

        if (mEtPwd.text.isEmpty() || mEtPwd.text.length !in 6..20) {
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
