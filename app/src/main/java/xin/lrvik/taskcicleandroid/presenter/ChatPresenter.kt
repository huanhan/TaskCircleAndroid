package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.presenter.view.ChatView
import xin.lrvik.taskcicleandroid.presenter.view.ClassView
import xin.lrvik.taskcicleandroid.service.ChatService
import xin.lrvik.taskcicleandroid.service.TaskService
import javax.inject.Inject

class ChatPresenter @Inject constructor() : BasePresenter<ChatView>() {
    @Inject
    lateinit var chatService: ChatService

    fun chatDetail(taskId: String,
                   hunterid: Long,
                   userid: Long,
                   page: Int,
                   size: Int) {
        if (!checkNetWork()) {
            return
        }

        chatService.chatDetail(taskId, hunterid, userid, page, size).execute(lifecycleProvider, mView, false) {
            mView.onChatListResult(it)
        }
    }

    fun saveChat(hunterId: Long,
                   userId: Long,
                   taskId: String,
                   context: String) {
        if (!checkNetWork()) {
            return
        }

        chatService.saveChat(hunterId, userId, taskId, context).execute(lifecycleProvider, mView, false) {
            mView.onSendResult(it)
        }
    }
}
