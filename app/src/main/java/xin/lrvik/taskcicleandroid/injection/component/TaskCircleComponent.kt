package xin.lrvik.taskcicleandroid.injection.component

import dagger.Component
import lrvik.xin.base.injection.PerComponentScope
import xin.lrvik.taskcicleandroid.baselibrary.injection.component.ActivityComponent
import xin.lrvik.taskcicleandroid.injection.module.CircleTaskModule
import xin.lrvik.taskcicleandroid.ui.activity.ClassActivity
import xin.lrvik.taskcicleandroid.ui.activity.HunterRunningActivity
import xin.lrvik.taskcicleandroid.ui.activity.PostTaskActivity
import xin.lrvik.taskcicleandroid.ui.activity.ReleaseTaskActivity
import xin.lrvik.taskcicleandroid.ui.fragment.HomeFragment
import xin.lrvik.taskcicleandroid.ui.fragment.HunterTaskStateFragment
import xin.lrvik.taskcicleandroid.ui.fragment.TaskClassFragment
import xin.lrvik.taskcicleandroid.ui.fragment.TaskStateFragment

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
@PerComponentScope
@Component(dependencies = [ActivityComponent::class], modules = [CircleTaskModule::class])
interface TaskCircleComponent {
    fun inject(activity: ClassActivity)
    fun inject(activity: PostTaskActivity)
    fun inject(activity: ReleaseTaskActivity)
    fun inject(activity: HunterRunningActivity)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: TaskClassFragment)
    fun inject(fragment: TaskStateFragment)
    fun inject(fragment: HunterTaskStateFragment)
}