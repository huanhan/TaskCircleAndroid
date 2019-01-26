package xin.lrvik.taskcicleandroid.service

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.data.protocol.*

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
interface HunterTaskService {

    //根据状态获取任务列表
    fun queryByState(state: String, page: Int, size: Int): Observable<Page<HunterTask>>
    fun acceptTask(taskId: String): Observable<Result>
}