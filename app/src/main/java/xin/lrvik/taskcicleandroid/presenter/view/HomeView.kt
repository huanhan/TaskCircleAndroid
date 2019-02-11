package xin.lrvik.taskcicleandroid.presenter.view

import xin.lrvik.taskcicleandroid.baselibrary.presenter.view.BaseView
import xin.lrvik.taskcicleandroid.data.protocol.Home
import xin.lrvik.taskcicleandroid.data.protocol.Task

interface HomeView:BaseView {

    fun onHomeDataResult(data : Home)
    fun onTaskListResult(data : List<Task>)

}
