package xin.lrvik.taskcicleandroid.baselibrary.common

import android.content.Context
import android.support.multidex.MultiDexApplication
import xin.lrvik.taskcicleandroid.baselibrary.injection.component.AppComponent
import xin.lrvik.taskcicleandroid.baselibrary.injection.component.DaggerAppComponent
import xin.lrvik.taskcicleandroid.baselibrary.injection.module.AppModule


class BaseApplication : MultiDexApplication() {

    lateinit var appComponent: AppComponent

    companion object {
        lateinit var context:Context
    }

    override fun onCreate() {
        super.onCreate()
        initAppInjection()
        context =this
    }

    private fun initAppInjection() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}