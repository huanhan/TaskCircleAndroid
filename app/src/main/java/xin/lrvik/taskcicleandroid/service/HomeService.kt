package xin.lrvik.taskcicleandroid.service

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.data.protocol.Home

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
interface HomeService {
    fun getHomeData(): Observable<Home>
}