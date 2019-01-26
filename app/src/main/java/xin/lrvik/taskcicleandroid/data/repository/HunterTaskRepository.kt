package xin.lrvik.taskcicleandroid.data.repository

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.baselibrary.data.net.RetrofitFactory
import xin.lrvik.taskcicleandroid.common.UserInfo
import xin.lrvik.taskcicleandroid.data.api.HunterTaskApi
import xin.lrvik.taskcicleandroid.data.protocol.HunterTask
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.data.protocol.Result
import javax.inject.Inject

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/27.
 *
 */
class HunterTaskRepository @Inject constructor() {

    //todo 注意修改用户id
    fun queryByState(state: String, page: Int, size: Int): Observable<Page<HunterTask>> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).queryByState(state, page, size, 13)
    }

    fun acceptTask(taskId: String): Observable<Result> {
        return RetrofitFactory.instance.create(HunterTaskApi::class.java).acceptTask(taskId, 13)
    }
}