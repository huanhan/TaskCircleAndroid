package xin.lrvik.taskcicleandroid.service.impl

import io.reactivex.Observable
import retrofit2.Call
import xin.lrvik.taskcicleandroid.data.protocol.Home
import xin.lrvik.taskcicleandroid.data.protocol.Message
import xin.lrvik.taskcicleandroid.data.protocol.OssToken
import xin.lrvik.taskcicleandroid.data.protocol.Page
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

    override fun message(page: Int, size: Int): Observable<Page<Message>> {
        return homeRepository.message(page, size)
    }
}