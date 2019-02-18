package xin.lrvik.taskcicleandroid.data.repository

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.baselibrary.data.net.RetrofitFactory
import xin.lrvik.taskcicleandroid.baselibrary.common.UserInfo
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

    fun addTask(classs: List<Long>, text: String, contentText: String, data: MutableList<TaskStep>): Observable<Result> {
        return RetrofitFactory.instance.create(TaskApi::class.java).addTask(AddTaskReq(text, contentText, classs, data))
    }

    fun deleteTask(taskId: String): Observable<Result> {
        return RetrofitFactory.instance.create(TaskApi::class.java).deleteTask(taskId)
    }

    fun modifyTask(id: String, classs: List<Long>, text: String, contentText: String, data: MutableList<TaskStep>): Observable<Result> {
        return RetrofitFactory.instance.create(TaskApi::class.java).modifyTask(ModifyTaskReq(id, text, contentText, classs, data))
    }

    fun queryByClassid(classsId: Long,
                       page: Int,
                       size: Int): Observable<Page<Task>> {
        return RetrofitFactory.instance.create(TaskApi::class.java).queryByClassid(classsId, page, size)
    }

    fun search(key: String,
               page: Int,
               size: Int): Observable<Page<Task>> {
        return RetrofitFactory.instance.create(TaskApi::class.java).search(key, page, size)
    }

    fun issueTask(id: String,
                  money: Float,
                  peopleNumber: Int,
                  beginTime: Long,
                  deadline: Long,
                  permitAbandonMinute: Int,
                  longitude: Double,
                  latitude: Double,
                  address: String,
                  taskRework: Boolean,
                  compensate: Boolean,
                  compensateMoney: Float): Observable<TaskDetail> {
        return RetrofitFactory.instance.create(TaskApi::class.java).issueTask(IssueTaskReq(id, money, peopleNumber, beginTime, deadline, permitAbandonMinute, longitude, latitude, address, taskRework, compensate, compensateMoney))
    }

    fun queryTaskDetail(taskId: String): Observable<TaskDetail> {
        return RetrofitFactory.instance.create(TaskApi::class.java).queryTaskDetail(taskId)
    }

    fun queryByState(state: String, page: Int, size: Int): Observable<Page<Task>> {
        return RetrofitFactory.instance.create(TaskApi::class.java).queryByState(state, page, size)
    }

    fun submitAudit(taskId: String): Observable<Result> {
        return RetrofitFactory.instance.create(TaskApi::class.java).submitAudit(taskId)
    }

    fun cancelAudit(taskId: String): Observable<Result> {
        return RetrofitFactory.instance.create(TaskApi::class.java).cancelAudit(taskId)
    }

    fun outTask(taskId: String): Observable<Result> {
        return RetrofitFactory.instance.create(TaskApi::class.java).outTask(taskId)
    }

    fun upperTask(taskId: String): Observable<Result> {
        return RetrofitFactory.instance.create(TaskApi::class.java).upperTask(taskId)
    }

    fun abandonTask(taskId: String): Observable<Result> {
        return RetrofitFactory.instance.create(TaskApi::class.java).abandonTask(taskId)
    }

    fun cancelAbandon(taskId: String): Observable<Result> {
        return RetrofitFactory.instance.create(TaskApi::class.java).cancelAbandon(taskId)
    }

    fun auditSuccess(htId: String): Observable<Result> {
        return RetrofitFactory.instance.create(TaskApi::class.java).auditSuccess(htId)
    }

    fun auditFailure(id: String, context: String): Observable<Result> {
        return RetrofitFactory.instance.create(TaskApi::class.java).auditFailure(AuditContextReq(id, context))
    }

    fun abandonPass(htId: String): Observable<Result> {
        return RetrofitFactory.instance.create(TaskApi::class.java).abandonPass(htId)
    }

    fun abandonNotPass(htId: String, context: String): Observable<Result> {
        return RetrofitFactory.instance.create(TaskApi::class.java).abandonNotPass(AuditContextReq(htId, context))
    }

    fun hunterRunning(taskid: String, page: Int, size: Int): Observable<Page<HunterTask>> {
        return RetrofitFactory.instance.create(TaskApi::class.java).hunterRunning(taskid, page, size)
    }

    fun abandonHunterTask(htId: String): Observable<Result> {
        return RetrofitFactory.instance.create(TaskApi::class.java).abandonHunterTask(htId)
    }

    fun forceAbandonHunterTask(htId: String): Observable<Result> {
        return RetrofitFactory.instance.create(TaskApi::class.java).forceAbandonHunterTask(htId)
    }


}