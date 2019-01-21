package xin.lrvik.taskcicleandroid.service.impl

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.data.protocol.Home
import xin.lrvik.taskcicleandroid.data.repository.HomeRepository
import xin.lrvik.taskcicleandroid.service.HomeService
import javax.inject.Inject

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
class HomeServiceImpl @Inject constructor() : HomeService {

    @Inject
    lateinit var homeRepository: HomeRepository

    override fun getHomeData(): Observable<Home> {
        return homeRepository.home()
    }
}