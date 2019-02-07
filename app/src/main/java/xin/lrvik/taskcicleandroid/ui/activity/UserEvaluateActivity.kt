package xin.lrvik.taskcicleandroid.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_user_evaluate.*
import org.jetbrains.anko.toast
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseMvpActivity
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.UserEvaluatePresenter
import xin.lrvik.taskcicleandroid.presenter.view.UserEvaluateView
import java.lang.Exception

class UserEvaluateActivity : BaseMvpActivity<UserEvaluatePresenter>(), UserEvaluateView {

    companion object {
        val HUNTERTASKID = "HUNTERTASKID"
    }

    lateinit var hunterTaskId: String

    override fun injectComponent() {
        DaggerTaskCircleComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onResult(result: String) {
        toast(result)
        if (result == "评价成功") {
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_evaluate)

        initView()
    }

    private fun initView() {
        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "评价猎刃"
        }
        try {
            hunterTaskId = intent.getStringExtra(HUNTERTASKID)
        } catch (e: Exception) {
            toast("数值传递异常")
            finish()
        }

    }


    fun validation(): Boolean {

        if (mLevTaskEva.contentText.isEmpty()) {
            toast("对猎刃的评价内容不能为空")
            return false
        }
        return true
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.evaluate, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.save_evaluate -> {
                if (validation()) {
                    mPresenter.evaHunter(mLevTaskEva.contentText, mSrbStart.rating, hunterTaskId)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
