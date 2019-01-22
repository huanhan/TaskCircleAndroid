package xin.lrvik.taskcicleandroid.data.repository

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.baselibrary.data.net.RetrofitFactory
import xin.lrvik.taskcicleandroid.common.UserInfo
import xin.lrvik.taskcicleandroid.data.api.TaskApi
import xin.lrvik.taskcicleandroid.data.protocol.*
import javax.inject.Inject

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
class TaskRepository @Inject constructor() {

    fun taskClass(): Observable<List<TaskClass>> {
        return RetrofitFactory.instance.create(TaskApi::class.java).taskClass()
    }

    fun addTask(classs: List<Long>, text: String, contentText: String, data: MutableList<TaskStep>): Observable<Task> {
        return RetrofitFactory.instance.create(TaskApi::class.java).addTask(UserInfo.userId, AddTaskReq(text, contentText, classs, data))
    }

    fun queryByClassid(classsId: Long,
                       page: Int,
                       size: Int): Observable<Page<Task>> {
        return RetrofitFactory.instance.create(TaskApi::class.java).queryByClassid(classsId, page, size)
    }
}