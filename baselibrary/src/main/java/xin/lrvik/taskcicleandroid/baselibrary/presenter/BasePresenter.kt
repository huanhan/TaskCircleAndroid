package xin.lrvik.taskcicleandroid.baselibrary.presenter

import android.content.Context
import com.trello.rxlifecycle2.LifecycleProvider
import xin.lrvik.taskcicleandroid.baselibrary.presenter.view.BaseView
import xin.lrvik.taskcicleandroid.baselibrary.utils.NetWorkUtils
import javax.inject.Inject


open class BasePresenter<T : BaseView> {
    lateinit var mView: T

    @Inject
    lateinit var lifecycleProvider: LifecycleProvider<*>

    @Inject
    lateinit var context: Context

    fun checkNetWork(): Boolean {
        return if (NetWorkUtils.isNetWorkAvailable(context)) {
            true
        } else {
            mView.onError("网络不可用")
            false
        }

    }
}