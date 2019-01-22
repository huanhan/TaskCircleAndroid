package xin.lrvik.taskcicleandroid.data.repository

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.baselibrary.data.net.RetrofitFactory
import xin.lrvik.taskcicleandroid.data.api.HomeApi
import xin.lrvik.taskcicleandroid.data.api.TaskApi
import xin.lrvik.taskcicleandroid.data.protocol.Home
import xin.lrvik.taskcicleandroid.data.protocol.TaskClass
import javax.inject.Inject

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
class TaskRepository @Inject constructor() {

    fun taskClass(): Observable<List<TaskClass>> {
        return RetrofitFactory.instance.create(TaskApi::class.java).taskClass()
    }
}