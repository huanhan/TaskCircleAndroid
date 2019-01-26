package xin.lrvik.taskcicleandroid.data.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import xin.lrvik.taskcicleandroid.data.protocol.HunterTask
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.data.protocol.Task

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/27.
 *
 */
interface HunterTaskApi {

    //查根据状态获取指定用户的任务列表 http://192.168.2.209:8080/app/task/hunter/ALL/0/15/6
    @GET("app/task/hunter/{state}/{page}/{size}/{id}")
    fun queryByState(@Path("state") state: String,
                     @Path("page") page: Int,
                     @Path("size") size: Int,
                     @Path("id") id: Long): Observable<Page<HunterTask>>
}