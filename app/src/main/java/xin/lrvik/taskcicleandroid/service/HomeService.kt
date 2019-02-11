package xin.lrvik.taskcicleandroid.service

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.data.protocol.Home
import xin.lrvik.taskcicleandroid.data.protocol.Message
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.data.protocol.Task

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
interface HomeService {
    fun getHomeData(): Observable<Home>
    fun message(page: Int, size: Int):  Observable<Page<Message>>
    fun task(sort: String, lat: Double, log: Double):  Observable<List<Task>>
}