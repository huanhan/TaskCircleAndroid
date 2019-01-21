package xin.lrvik.taskcicleandroid.injection.component

import dagger.Component
import lrvik.xin.base.injection.PerComponentScope
import xin.lrvik.taskcicleandroid.baselibrary.injection.component.ActivityComponent
import xin.lrvik.taskcicleandroid.injection.module.HomeModule
import xin.lrvik.taskcicleandroid.ui.fragment.HomeFragment

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class), modules = arrayOf(HomeModule::class))
interface HomeComponent {
    fun inject(fragment: HomeFragment)
}