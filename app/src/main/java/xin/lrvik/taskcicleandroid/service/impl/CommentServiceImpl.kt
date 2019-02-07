package xin.lrvik.taskcicleandroid.service.impl

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.data.protocol.*
import xin.lrvik.taskcicleandroid.data.repository.CommentRepository
import xin.lrvik.taskcicleandroid.service.CommentService
import javax.inject.Inject

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/2/7.
 *
 */
class CommentServiceImpl @Inject constructor() : CommentService {

    @Inject
    lateinit var commentRepository: CommentRepository

    override fun evaUserByUser(page: Int, size: Int): Observable<Page<CommentUser>> {
        return commentRepository.evaUserByUser(page, size)
    }

    override fun evaHunterByUser(page: Int, size: Int): Observable<Page<CommentHunter>> {
        return commentRepository.evaHunterByUser(page, size)
    }

    override fun evaUserByHunter(page: Int, size: Int): Observable<Page<CommentUser>> {
        return commentRepository.evaUserByHunter(page, size)
    }

    override fun evaHunterByHunter(page: Int, size: Int): Observable<Page<CommentHunter>> {
        return commentRepository.evaHunterByHunter(page, size)
    }

    override fun taskComment(taskid: String, page: Int, size: Int): Observable<Page<CommentTask>> {
        return commentRepository.taskComment(taskid, page, size)
    }

    override fun evaTaskAndTask(taskContext: String, taskStart: Float, hunterTaskId: String, userContext: String, userStart: Float): Observable<Result> {
        return commentRepository.evaTaskAndTask(taskContext, taskStart, hunterTaskId, userContext, userStart)
    }

    override fun evaHunter(content: String, start: Float, hunterTaskId: String): Observable<Result> {
        return commentRepository.evaHunter(content, start, hunterTaskId)
    }
}