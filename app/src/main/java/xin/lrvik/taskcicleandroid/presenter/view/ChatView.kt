package xin.lrvik.taskcicleandroid.presenter.view

import xin.lrvik.taskcicleandroid.baselibrary.presenter.view.BaseView
import xin.lrvik.taskcicleandroid.data.protocol.Chat
import xin.lrvik.taskcicleandroid.data.protocol.Page

interface ChatView:BaseView {

    fun onChatListResult(data: Page<Chat>)

    fun onResult(result: String)

}
