package xin.lrvik.taskcicleandroid.data.repository

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.baselibrary.data.net.RetrofitFactory
import xin.lrvik.taskcicleandroid.baselibrary.common.UserInfo
import xin.lrvik.taskcicleandroid.data.api.CommentApi
import xin.lrvik.taskcicleandroid.data.protocol.*
import javax.inject.Inject

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
class CommentRepository @Inject constructor() {

    fun evaUserByUser(page: Int,
                      size: Int): Observable<Page<CommentUser>> {
        return RetrofitFactory.instance.create(CommentApi::class.java).evaUserByUser(page, size)
    }

    fun evaHunterByUser(page: Int,
                        size: Int): Observable<Page<CommentHunter>> {
        return RetrofitFactory.instance.create(CommentApi::class.java).evaHunterByUser(page, size)
    }

    fun evaUserByHunter(page: Int,
                        size: Int): Observable<Page<CommentUser>> {
        return RetrofitFactory.instance.create(CommentApi::class.java).evaUserByHunter(page, size)
    }

    fun evaHunterByHunter(page: Int,
                          size: Int): Observable<Page<CommentHunter>> {
        return RetrofitFactory.instance.create(CommentApi::class.java).evaHunterByHunter(page, size)
    }

    fun taskComment(taskid: String,
                    page: Int,
                    size: Int): Observable<Page<CommentTask>> {
        return RetrofitFactory.instance.create(CommentApi::class.java).taskComment(taskid, page, size)
    }

    fun evaTaskAndTask(taskContext: String,
                       taskStart: Float,
                       hunterTaskId: String,
                       userContext: String,
                       userStart: Float): Observable<Result> {
        return RetrofitFactory.instance.create(CommentApi::class.java).evaTaskAndTask(HunterCommentReq(taskContext, taskStart, hunterTaskId, userContext, userStart))
    }

    fun evaHunter(content: String,
                  start: Float,
                  hunterTaskId: String): Observable<Result> {
        return RetrofitFactory.instance.create(CommentApi::class.java).evaHunter(UserCommentReq(content, start, hunterTaskId))
    }
}