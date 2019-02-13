package xin.lrvik.taskcicleandroid.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_user_detail.*
import org.jetbrains.anko.startActivity
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.loadCircleUrl
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseMvpActivity
import xin.lrvik.taskcicleandroid.baselibrary.utils.DateUtils
import xin.lrvik.taskcicleandroid.data.protocol.User
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.UserDetailPresenter
import xin.lrvik.taskcicleandroid.presenter.view.UserDetailView

class UserDetailActivity : BaseMvpActivity<UserDetailPresenter>(), UserDetailView {

    override fun injectComponent() {
        DaggerTaskCircleComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onUserResult(data: User) {

        mLevContext.id_et_input.isEnabled = false

        mIvIcon.loadCircleUrl(data.headImg ?: R.mipmap.icon_default_user)

        mTvName.text = "昵称:  " + (data.name)
        mTvGender.text = "性别:  " + (data.gender!!.gender)
        mLevContext.contentText = (data.intro ?: "")
        mTvIdCard.text = "身份证号码:  " + (data.idCard ?: "")
        mTvPhone.text = "电话:  " + (data.phone ?: "")
        mTvAddress.text = "地址:  " + (data.address ?: "")
        mTvSchool.text = "毕业学校:  " + (data.school ?: "")
        mTvJob.text = "职业:  " + (data.major ?: "")
        mTvInterest.text = "兴趣:  " + (data.interest ?: "")
        mTvHeight.text = "身高:  " + (data.height ?: "")
        mTvWeight.text = "体重:  " + (data.weight ?: "")
        mTvBirthday.text = "生日:  " + (if (data.birthday != null && data.birthday!!.time != 0L) DateUtils.convertTimeToString(data.birthday!!, DateUtils.FORMAT_SHORT) else "")
    }

    var userId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        initView()
    }

    private fun initView() {
        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "个人信息"
        }

    }

    override fun onStart() {
        super.onStart()

        mPresenter.detail()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.modify -> {
                startActivity<ModifyUserInfoActivity>()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.userinfo_look, menu)
        return true
    }
}
