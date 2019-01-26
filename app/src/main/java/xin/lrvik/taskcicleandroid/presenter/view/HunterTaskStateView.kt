package xin.lrvik.taskcicleandroid.presenter.view

import xin.lrvik.taskcicleandroid.baselibrary.presenter.view.BaseView
import xin.lrvik.taskcicleandroid.data.protocol.*

interface HunterTaskStateView : BaseView {
    fun onTaskStateResult(data: Page<HunterTask>)
    fun onResult(result: String)
}
