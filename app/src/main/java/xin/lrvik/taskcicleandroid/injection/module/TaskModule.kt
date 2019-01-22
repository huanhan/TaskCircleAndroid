package xin.lrvik.taskcicleandroid.injection.module

import dagger.Module
import dagger.Provides
import xin.lrvik.taskcicleandroid.service.TaskService
import xin.lrvik.taskcicleandroid.service.impl.TaskServiceImpl

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
@Module
class TaskModule {

    @Provides
    fun providesUserService(service:TaskServiceImpl): TaskService {
        return service
    }
}