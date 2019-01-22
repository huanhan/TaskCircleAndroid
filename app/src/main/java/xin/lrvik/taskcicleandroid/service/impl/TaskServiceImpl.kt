package xin.lrvik.taskcicleandroid.service.impl

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.data.protocol.Home
import xin.lrvik.taskcicleandroid.data.protocol.TaskClass
import xin.lrvik.taskcicleandroid.data.repository.HomeRepository
import xin.lrvik.taskcicleandroid.data.repository.TaskRepository
import xin.lrvik.taskcicleandroid.service.HomeService
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
}