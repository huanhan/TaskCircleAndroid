package xin.lrvik.taskcicleandroid.injection.component

import dagger.Component
import lrvik.xin.base.injection.PerComponentScope
import xin.lrvik.taskcicleandroid.baselibrary.injection.component.ActivityComponent
import xin.lrvik.taskcicleandroid.injection.module.CircleTaskModule
import xin.lrvik.taskcicleandroid.ui.activity.*
import xin.lrvik.taskcicleandroid.ui.fragment.*

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
    fun inject(activity: TaskDetailActivity)
    fun inject(activity: HunterTaskDetailActivity)
    fun inject(activity: ChatActivity)
    fun inject(activity: RegistActivity)
    fun inject(activity: UserDetailActivity)
    fun inject(activity: ModifyUserInfoActivity)
    fun inject(activity: SearchListActivity)
    fun inject(activity: HunterEvaluateActivity)
    fun inject(activity: UserEvaluateActivity)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: TaskClassFragment)
    fun inject(fragment: TaskStateFragment)
    fun inject(fragment: HunterTaskStateFragment)
    fun inject(fragment: UserWithdrawFragment)
    fun inject(fragment: TransferFragment)
    fun inject(fragment: CashPledgeFragment)
    fun inject(fragment: MesFragment)
    fun inject(fragment: MyFragment)
    fun inject(fragment: UserReEvaFragment)
    fun inject(fragment: UserSdEvaFragment)
}