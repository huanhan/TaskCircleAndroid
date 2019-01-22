package xin.lrvik.taskcicleandroid.presenter.view

import xin.lrvik.taskcicleandroid.baselibrary.presenter.view.BaseView
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.data.protocol.Task

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/23.
 *
 */
interface TaskClassView : BaseView {

    fun onTaskListResult(data: Page<Task>)
}