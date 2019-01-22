package xin.lrvik.taskcicleandroid.data.api

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import xin.lrvik.taskcicleandroid.data.protocol.*

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
interface TaskApi {

    @GET("app/task/tcl")
    fun taskClass(): Observable<List<TaskClass>>

    //增加任务
    @POST("app/task/{id}")
    fun addTask(@Path("id") id: Int, @Body req: AddTaskReq): Observable<Task>

    //根据任务id查询任务详细
    @GET("app/task/{id}")
    fun queryTaskDetail(@Path("id") id: Int): Observable<TaskDetail>

    //查询分类下所有的已发布任务 http://localhost:8080/app/task/issue/9/0/2
    @GET("app/task/issue/{classid}/{page}/{size}")
    fun queryByClassid(@Path("classid") classid: Long,
                       @Path("page") page: Int,
                       @Path("size") size: Int): Observable<Page<Task>>

    //查根据状态获取指定用户的任务列表 http://localhost:8080/app/task/user/AWAIT_AUDIT/0/5/6
    @GET("app/task/user/{state}/{page}/{size}/{id}")
    fun queryByState(@Path("state") state: String,
                     @Path("page") page: Int,
                     @Path("size") size: Int,
                     @Path("id") id: Long): Observable<List<Task>>

}