package xin.lrvik.taskcicleandroid.ui.activity

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_class.*
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseActivity
import xin.lrvik.taskcicleandroid.ui.dialog.ClassificationDialog

class ClassActivity : BaseActivity() {

      var mDialog: ClassificationDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class)
        initView()
    }

    private fun initView() {
        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = intent.getStringExtra("CLASSTYPE")
        }

        mIvBt.onClick {
            if (mDialog == null) {
                mDialog = ClassificationDialog()
                mDialog!!.showDialog(supportFragmentManager)
            }else{
                mDialog!!.showDialog(supportFragmentManager)
            }
        }

    }
}
