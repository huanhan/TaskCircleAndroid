package xin.lrvik.taskcicleandroid.injection.module

import dagger.Module
import dagger.Provides
import xin.lrvik.taskcicleandroid.service.HomeService
import xin.lrvik.taskcicleandroid.service.impl.HomeServiceImpl

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
@Module
class HomeModule {

    @Provides
    fun providesUserService(service:HomeServiceImpl): HomeService {
        return service
    }
}