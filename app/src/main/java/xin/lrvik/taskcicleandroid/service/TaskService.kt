package xin.lrvik.taskcicleandroid.service

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.data.protocol.Task
import xin.lrvik.taskcicleandroid.data.protocol.TaskClass
import xin.lrvik.taskcicleandroid.data.protocol.TaskStep

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
interface TaskService {
    fun getTaskClassData(): Observable<List<TaskClass>>
    fun addTask(req: List<Long>, text: String, contentText: String, data: MutableList<TaskStep>): Observable<Task>
    fun queryByClassid(classsId: Long, page: Int, size: Int): Observable<Page<Task>>
}