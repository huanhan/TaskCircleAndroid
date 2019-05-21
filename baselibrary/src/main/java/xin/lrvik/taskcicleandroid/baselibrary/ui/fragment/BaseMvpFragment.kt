package xin.lrvik.taskcicleandroid.baselibrary.ui.fragment

import android.os.Bundle
import lrvik.xin.base.widget.ProgressLoading
import org.jetbrains.anko.support.v4.toast
import xin.lrvik.taskcicleandroid.baselibrary.common.BaseApplication
import xin.lrvik.taskcicleandroid.baselibrary.injection.component.ActivityComponent
import xin.lrvik.taskcicleandroid.baselibrary.injection.component.DaggerActivityComponent
import xin.lrvik.taskcicleandroid.baselibrary.injection.module.ActivityModule
import xin.lrvik.taskcicleandroid.baselibrary.injection.module.LifecycleProviderModule
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.baselibrary.presenter.view.BaseView
import javax.inject.Inject

open abstract class BaseMvpFragment<T : BasePresenter<*>> : BaseFragment(), BaseView {

    @Inject
    lateinit var mPresenter: T

    lateinit var activityComponent: ActivityComponent

    private lateinit var mLoadingDialog: ProgressLoading

    override fun showLoading() {
        mLoadingDialog.showLoading()
    }

    override fun hideLoading() {
        mLoadingDialog.hideLoading()
    }

    override fun onError(text: String) {
        toast(text)
        hideLoading()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityInjection()
        injectComponent()

        mLoadingDialog = ProgressLoading.create(activity!!)
    }

    protected abstract fun injectComponent()

    private fun initActivityInjection() {
        activityComponent = DaggerActivityComponent.builder()
                .appComponent((activity!!.application as BaseApplication).appComponent)
                .activityModule(ActivityModule(activity!!))
                .lifecycleProviderModule(LifecycleProviderModule(this))
                .build()

    }

}