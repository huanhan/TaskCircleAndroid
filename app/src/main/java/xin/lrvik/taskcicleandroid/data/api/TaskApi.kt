package xin.lrvik.taskcicleandroid.data.api

import io.reactivex.Observable
import retrofit2.http.GET
import xin.lrvik.taskcicleandroid.data.protocol.Home
import xin.lrvik.taskcicleandroid.data.protocol.TaskClass

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
interface TaskApi {

    @GET("app/task/tcl")
    fun taskClass(): Observable<List<TaskClass>>

}