package xin.lrvik.taskcicleandroid.service.impl

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.data.protocol.Chat
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.data.protocol.Result
import xin.lrvik.taskcicleandroid.data.repository.ChatRepository
import xin.lrvik.taskcicleandroid.service.ChatService
import javax.inject.Inject

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/2/1.
 *
 */
class ChatServiceImpl @Inject constructor() : ChatService {

    @Inject
    lateinit var chatRepository: ChatRepository

    override fun chatDetail(taskId: String, hunterid: Long, userid: Long, page: Int, size: Int): Observable<Page<Chat>> {
        return chatRepository.chatDetail(taskId, hunterid, userid, page, size)
    }

    override fun saveChat(hunterId: Long, userId: Long, taskId: String, context: String): Observable<Result> {
        return chatRepository.saveChat(hunterId, userId, taskId, context)
    }
}