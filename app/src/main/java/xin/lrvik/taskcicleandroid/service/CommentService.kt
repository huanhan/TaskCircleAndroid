package xin.lrvik.taskcicleandroid.service

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.data.protocol.*

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/2/7.
 *
 */
interface CommentService {

    fun evaUserByUser(page: Int, size: Int): Observable<Page<CommentUser>>

    fun evaHunterByUser(page: Int, size: Int): Observable<Page<CommentHunter>>

    fun evaUserByHunter(page: Int, size: Int): Observable<Page<CommentUser>>

    fun evaHunterByHunter(page: Int, size: Int): Observable<Page<CommentHunter>>

    fun evaTaskAndTask(taskContext: String, taskStart: Float, hunterTaskId: String, userContext: String, userStart: Float): Observable<Result>

    fun evaHunter(content: String, start: Float, hunterTaskId: String): Observable<Result>

    fun taskComment(taskid: String, page: Int, size: Int): Observable<Page<CommentTask>>
}