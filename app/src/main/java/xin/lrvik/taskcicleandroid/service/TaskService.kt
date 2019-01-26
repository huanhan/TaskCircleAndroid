package xin.lrvik.taskcicleandroid.service

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.data.protocol.*

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
interface TaskService {
    //获取所有任务分类
    fun getTaskClassData(): Observable<List<TaskClass>>

    //增加任务
    fun addTask(req: List<Long>, text: String, contentText: String, data: MutableList<TaskStep>): Observable<Result>

    //修改任务
    fun modifyTask(id: String, classs: List<Long>, text: String, contentText: String, data: MutableList<TaskStep>): Observable<Result>

    //查询分类下所有任务
    fun queryByClassid(classsId: Long, page: Int, size: Int): Observable<Page<Task>>

    //查询任务详细
    fun queryTaskDetail(id: String): Observable<TaskDetail>

    //发布任务
    fun issueTask(id: String, money: Float, peopleNumber: Int, beginTime: Long, deadline: Long, permitAbandonMinute: Int, longitude: Double, latitude: Double, address: String, taskRework: Boolean, compensate: Boolean, compensateMoney: Float): Observable<TaskDetail>

    //根据状态获取任务列表
    fun queryByState(state: String, page: Int, size: Int): Observable<Page<Task>>

    //将用户的任务提交给管理员审核
    fun submitAudit(taskId: String): Observable<Result>

    //取消审核
    fun cancelAudit(taskId: String): Observable<Result>

    //撤回我的任务
    fun outTask(taskId: String): Observable<Result>

    //用户点击重新上架按钮功能
    fun upperTask(taskId: String): Observable<Result>

    //用户点击放弃任务
    fun abandonTask(taskId: String): Observable<Result>

    //用户点击取消放弃任务
    fun cancelAbandon(taskId: String): Observable<Result>

    //用户点击审核猎刃任务通过
    fun auditSuccess(htId: String): Observable<Result>

    //用户点击审核猎刃任务不通过
    fun auditFailure(id: String, context: String): Observable<Result>
}