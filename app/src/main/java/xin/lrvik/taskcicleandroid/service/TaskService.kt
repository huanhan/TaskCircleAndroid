package xin.lrvik.taskcicleandroid.service

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.data.protocol.*
import java.sql.Timestamp

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
interface TaskService {
    //获取所有任务分类
    fun getTaskClassData(): Observable<List<TaskClass>>

    //增加任务
    fun addTask(req: List<Long>, text: String, contentText: String, data: MutableList<TaskStep>): Observable<TaskDetail>

    //修改任务
    fun modifyTask(id: String, classs: List<Long>, text: String, contentText: String, data: MutableList<TaskStep>): Observable<TaskDetail>

    //查询分类下所有任务
    fun queryByClassid(classsId: Long, page: Int, size: Int): Observable<Page<Task>>

    //查询任务详细
    fun queryTaskDetail(id: String): Observable<TaskDetail>

    //发布任务
    fun issueTask(id: String, money: Float, peopleNumber: Int, beginTime: Long, deadline: Long, permitAbandonMinute: Int, longitude: Double, latitude: Double, taskRework: Boolean, compensate: Boolean, compensateMoney: Float): Observable<TaskDetail>

    //根据状态获取任务列表
    fun queryByState(state: String, page: Int, size: Int): Observable<Page<Task>>
}