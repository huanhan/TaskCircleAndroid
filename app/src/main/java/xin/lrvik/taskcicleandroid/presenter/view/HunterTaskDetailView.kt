package xin.lrvik.taskcicleandroid.presenter.view

import xin.lrvik.taskcicleandroid.baselibrary.presenter.view.BaseView
import xin.lrvik.taskcicleandroid.data.protocol.TaskClass
import xin.lrvik.taskcicleandroid.data.protocol.TaskDetail

interface HunterTaskDetailView : BaseView {

    fun onTaskDetailResult(data: TaskDetail)

    fun onResult(result: String)
}
