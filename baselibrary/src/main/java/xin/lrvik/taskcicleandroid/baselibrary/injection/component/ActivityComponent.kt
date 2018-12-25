package xin.lrvik.taskcicleandroid.baselibrary.injection.component

import android.app.Activity
import android.content.Context
import com.trello.rxlifecycle2.LifecycleProvider
import dagger.Component
import xin.lrvik.taskcicleandroid.baselibrary.injection.ActivityScope
import xin.lrvik.taskcicleandroid.baselibrary.injection.module.ActivityModule
import xin.lrvik.taskcicleandroid.baselibrary.injection.module.LifecycleProviderModule


@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(ActivityModule::class, LifecycleProviderModule::class))
interface ActivityComponent {
    fun activity(): Activity
    fun context(): Context
    fun providesLifecycleProvider(): LifecycleProvider<*>
}