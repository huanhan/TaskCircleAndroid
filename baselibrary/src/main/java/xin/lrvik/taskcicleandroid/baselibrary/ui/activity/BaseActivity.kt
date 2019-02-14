package xin.lrvik.taskcicleandroid.baselibrary.ui.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import xin.lrvik.taskcicleandroid.baselibrary.common.AppManger

open class BaseActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        AppManger.instance.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManger.instance.finishActivity(this)
    }
}