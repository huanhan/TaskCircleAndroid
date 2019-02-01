package xin.lrvik.taskcicleandroid.data.repository

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.baselibrary.data.net.RetrofitFactory
import xin.lrvik.taskcicleandroid.common.UserInfo
import xin.lrvik.taskcicleandroid.data.api.ChatApi
import xin.lrvik.taskcicleandroid.data.protocol.AddChatReq
import xin.lrvik.taskcicleandroid.data.protocol.Chat
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.data.protocol.Result
import javax.inject.Inject

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
class ChatRepository @Inject constructor() {

    fun chatDetail(taskId: String,
                   hunterid: Long,
                   userid: Long,
                   page: Int,
                   size: Int): Observable<Page<Chat>> {
        return RetrofitFactory.instance.create(ChatApi::class.java).chatDetail(taskId, hunterid, userid, page, size)
    }

    fun saveChat(hunterId: Long,
                 userId: Long,
                 taskId: String,
                 context: String): Observable<Chat> {
        return RetrofitFactory.instance.create(ChatApi::class.java).saveChat(AddChatReq(hunterId, userId, taskId, context),UserInfo.userId)
    }
}