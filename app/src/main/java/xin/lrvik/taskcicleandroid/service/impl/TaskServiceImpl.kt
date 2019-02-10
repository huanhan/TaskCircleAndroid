package xin.lrvik.taskcicleandroid.service.impl

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.data.protocol.*
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

    override fun addTask(classs: List<Long>, text: String, contentText: String, data: MutableList<TaskStep>): Observable<Result> {
        return taskRepository.addTask(classs, text, contentText, data)
    }

    override fun deleteTask(taskId: String): Observable<Result> {
        return taskRepository.deleteTask(taskId)
    }


    override fun modifyTask(id: String, classs: List<Long>, text: String, contentText: String, data: MutableList<TaskStep>): Observable<Result> {
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

    override fun issueTask(id: String, money: Float, peopleNumber: Int, beginTime: Long, deadline: Long, permitAbandonMinute: Int, longitude: Double, latitude: Double, address: String, taskRework: Boolean, compensate: Boolean, compensateMoney: Float): Observable<TaskDetail> {
        return taskRepository.issueTask(id, money, peopleNumber, beginTime, deadline, permitAbandonMinute, longitude, latitude, address, taskRework, compensate, compensateMoney)
    }

    override fun queryByState(state: String,
                              page: Int,
                              size: Int): Observable<Page<Task>> {
        return taskRepository.queryByState(state, page, size)
    }

    override fun search(key: String, page: Int, size: Int): Observable<Page<Task>> {
        return taskRepository.search(key, page, size)
    }

    override fun submitAudit(taskId: String): Observable<Result> {
        return taskRepository.submitAudit(taskId)
    }

    override fun cancelAudit(taskId: String): Observable<Result> {
        return taskRepository.cancelAudit(taskId)
    }

    override fun outTask(taskId: String): Observable<Result> {
        return taskRepository.outTask(taskId)
    }

    override fun upperTask(taskId: String): Observable<Result> {
        return taskRepository.upperTask(taskId)
    }

    override fun abandonTask(taskId: String): Observable<Result> {
        return taskRepository.abandonTask(taskId)
    }

    override fun cancelAbandon(taskId: String): Observable<Result> {
        return taskRepository.cancelAbandon(taskId)
    }

    override fun auditSuccess(htId: String): Observable<Result> {
        return taskRepository.auditSuccess(htId)
    }

    override fun auditFailure(id: String, context: String): Observable<Result> {
        return taskRepository.auditFailure(id, context)
    }

    override fun abandonPass(htId: String): Observable<Result> {
        return taskRepository.abandonPass(htId)
    }

    override fun abandonNotPass(htId: String, context: String): Observable<Result> {
        return taskRepository.abandonNotPass(htId, context)
    }

    override fun hunterRunning(taskid: String, page: Int, size: Int): Observable<Page<HunterTask>> {
        return taskRepository.hunterRunning(taskid, page, size)
    }

}