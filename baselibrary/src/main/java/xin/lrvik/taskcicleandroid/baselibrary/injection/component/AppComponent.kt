package xin.lrvik.taskcicleandroid.baselibrary.injection.component

import android.content.Context
import dagger.Component
import xin.lrvik.taskcicleandroid.baselibrary.injection.module.AppModule
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun context(): Context
}