package xin.lrvik.taskcicleandroid.service

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.data.protocol.Chat
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.data.protocol.Result

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
interface ChatService {
    fun chatDetail(taskId: String,
                   hunterid: Long,
                   userid: Long,
                   page: Int,
                   size: Int): Observable<Page<Chat>>

    fun saveChat(hunterId: Long,
                 userId: Long,
                 taskId: String,
                 context: String): Observable<Chat>
}