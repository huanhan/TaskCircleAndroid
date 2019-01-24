package xin.lrvik.taskcicleandroid.presenter.view

import xin.lrvik.taskcicleandroid.baselibrary.presenter.view.BaseView
import xin.lrvik.taskcicleandroid.data.protocol.TaskClass
import xin.lrvik.taskcicleandroid.data.protocol.TaskDetail

interface PostTaskView:BaseView {

    fun onTaskClassResult(data : List<TaskClass>)

    fun onAddTaskResult(data : TaskDetail)

    fun onTaskDetailResult(data: TaskDetail)

    fun onModifyTaskResult(it: TaskDetail)
}