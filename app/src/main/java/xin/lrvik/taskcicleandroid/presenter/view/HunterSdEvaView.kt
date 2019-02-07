package xin.lrvik.taskcicleandroid.presenter.view

import xin.lrvik.taskcicleandroid.baselibrary.presenter.view.BaseView
import xin.lrvik.taskcicleandroid.data.protocol.Chat
import xin.lrvik.taskcicleandroid.data.protocol.CommentUser
import xin.lrvik.taskcicleandroid.data.protocol.Page

interface HunterSdEvaView:BaseView {

    fun onListResult(data: Page<CommentUser>)

}
