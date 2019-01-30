package xin.lrvik.taskcicleandroid.data.repository

import io.reactivex.Observable
import retrofit2.Call
import xin.lrvik.taskcicleandroid.baselibrary.data.net.RetrofitFactory
import xin.lrvik.taskcicleandroid.data.api.HomeApi
import xin.lrvik.taskcicleandroid.data.protocol.Home
import xin.lrvik.taskcicleandroid.data.protocol.OssToken
import javax.inject.Inject

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
class HomeRepository @Inject constructor() {

    fun home(): Observable<Home> {
        return RetrofitFactory.instance.create(HomeApi::class.java).home()
    }
}