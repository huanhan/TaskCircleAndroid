package xin.lrvik.taskcicleandroid.presenter.view

import xin.lrvik.taskcicleandroid.baselibrary.presenter.view.BaseView
import xin.lrvik.taskcicleandroid.data.protocol.CommentHunter
import xin.lrvik.taskcicleandroid.data.protocol.CommentTask
import xin.lrvik.taskcicleandroid.data.protocol.Page

interface TaskEvaView:BaseView {

    fun onListResult(data: Page<CommentTask>)

}
