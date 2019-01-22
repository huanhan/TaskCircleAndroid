package xin.lrvik.taskcicleandroid.data.api

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import xin.lrvik.taskcicleandroid.data.protocol.AddTaskReq
import xin.lrvik.taskcicleandroid.data.protocol.Home
import xin.lrvik.taskcicleandroid.data.protocol.Task
import xin.lrvik.taskcicleandroid.data.protocol.TaskClass

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
interface TaskApi {

    @GET("app/task/tcl")
    fun taskClass(): Observable<List<TaskClass>>

    @POST("app/task/{id}")
    fun addTask(@Path("id") id: Int, @Body req: AddTaskReq): Observable<Task>

}