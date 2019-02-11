package xin.lrvik.taskcicleandroid.data.repository

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.baselibrary.data.net.RetrofitFactory
import xin.lrvik.taskcicleandroid.baselibrary.common.UserInfo
import xin.lrvik.taskcicleandroid.data.api.HomeApi
import xin.lrvik.taskcicleandroid.data.protocol.Home
import xin.lrvik.taskcicleandroid.data.protocol.Message
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.data.protocol.Task
import javax.inject.Inject

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
class HomeRepository @Inject constructor() {

    fun home(): Observable<Home> {
        return RetrofitFactory.instance.create(HomeApi::class.java).home()
    }

    fun message(page: Int, size: Int): Observable<Page<Message>> {
        return RetrofitFactory.instance.create(HomeApi::class.java).message(page, size, UserInfo.userId)
    }

    fun task(sort: String,
             lat: Double,
             log: Double): Observable<List<Task>> {
        return RetrofitFactory.instance.create(HomeApi::class.java).task(sort, lat, log, UserInfo.userId)
    }
}