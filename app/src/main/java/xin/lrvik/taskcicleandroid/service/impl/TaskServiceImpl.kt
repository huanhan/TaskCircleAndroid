package xin.lrvik.taskcicleandroid.service.impl

import io.reactivex.Observable
import retrofit2.http.Path
import xin.lrvik.taskcicleandroid.common.UserInfo.latitude
import xin.lrvik.taskcicleandroid.common.UserInfo.longitude
import xin.lrvik.taskcicleandroid.data.protocol.*
import xin.lrvik.taskcicleandroid.data.repository.TaskRepository
import xin.lrvik.taskcicleandroid.service.TaskService
import java.sql.Timestamp
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

    override fun addTask(classs: List<Long>, text: String, contentText: String, data: MutableList<TaskStep>): Observable<TaskDetail> {
        return taskRepository.addTask(classs, text, contentText, data)
    }


    override fun modifyTask(id: String, classs: List<Long>, text: String, contentText: String, data: MutableList<TaskStep>): Observable<TaskDetail> {
        return taskRepository.modifyTask(id, classs, text, contentText, data)
    }

    override fun queryByClassid(classsId: Long,
                                page: Int,
                                size: Int): Observable<Page<Task>> {
        return taskRepository.queryByClassid(classsId, page, size)
    }

    override fun queryTaskDetail(id: String): Observable<TaskDetail> {
        return taskRepository.queryTaskDetail(id)
    }

    override fun issueTask(id: String, money: Float, peopleNumber: Int, beginTime: Long, deadline: Long, permitAbandonMinute: Int, longitude: Double, latitude: Double, taskRework: Boolean, compensate: Boolean, compensateMoney: Float): Observable<TaskDetail> {
        return taskRepository.issueTask(id, money, peopleNumber, beginTime, deadline, permitAbandonMinute, longitude, latitude, taskRework, compensate, compensateMoney)
    }

    override fun queryByState(state: String,
                              page: Int,
                              size: Int): Observable<Page<Task>> {
        return taskRepository.queryByState(state, page, size)
    }
}