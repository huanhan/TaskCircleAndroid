package xin.lrvik.taskcicleandroid.presenter.view

import xin.lrvik.taskcicleandroid.baselibrary.presenter.view.BaseView
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.data.protocol.UserWithdraw

interface UserWithdrawView:BaseView {

    fun onRecordResult(data : Page<UserWithdraw>)

}
