package xin.lrvik.taskcicleandroid.injection.module

import dagger.Module
import dagger.Provides
import xin.lrvik.taskcicleandroid.service.*
import xin.lrvik.taskcicleandroid.service.impl.*

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
@Module
class CircleTaskModule {

    @Provides
    fun providesHomeService(service: HomeServiceImpl): HomeService {
        return service
    }

    @Provides
    fun providesTaskService(service: TaskServiceImpl): TaskService {
        return service
    }

    @Provides
    fun providesHunterTaskService(service: HunterTaskServiceImpl): HunterTaskService {
        return service
    }

    @Provides
    fun providesWalletService(service: WalletServiceImpl): WalletService {
        return service
    }

    @Provides
    fun providesChatService(service: ChatServiceImpl): ChatService {
        return service
    }
}