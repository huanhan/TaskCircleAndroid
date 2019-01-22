package xin.lrvik.taskcicleandroid.presenter.view

import xin.lrvik.taskcicleandroid.baselibrary.presenter.view.BaseView
import xin.lrvik.taskcicleandroid.data.protocol.TaskClass

interface ClassView:BaseView {

    fun onTaskClassResult(data : List<TaskClass>)

}
