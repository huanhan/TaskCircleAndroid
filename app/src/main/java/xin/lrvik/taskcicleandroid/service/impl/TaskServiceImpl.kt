package xin.lrvik.taskcicleandroid.service.impl

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.data.protocol.Task
import xin.lrvik.taskcicleandroid.data.protocol.TaskClass
import xin.lrvik.taskcicleandroid.data.protocol.TaskStep
import xin.lrvik.taskcicleandroid.data.repository.TaskRepository
import xin.lrvik.taskcicleandroid.service.TaskService
import javax.inject.Inject

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
class TaskServiceImpl @Inject constructor() : TaskService {

    @Inject
    lateinit var taskRepository: TaskRepository

    override fun getTaskClassData(): Observable<List<TaskClass>> {
        return taskRepository.taskClass()
    }

    override fun addTask(classs: List<Long>, text: String, contentText: String, data: MutableList<TaskStep>): Observable<Task> {
        return taskRepository.addTask(classs, text, contentText, data)
    }

    override fun queryByClassid(classsId: Long,
                                page: Int,
                                size: Int): Observable<Page<Task>> {
        return taskRepository.queryByClassid(classsId, page, size)
    }
}