package xin.lrvik.taskcicleandroid.presenter.view

import xin.lrvik.taskcicleandroid.baselibrary.presenter.view.BaseView
import xin.lrvik.taskcicleandroid.data.protocol.User

interface ModifyUserView:BaseView {

    fun onUserResult(data : User)

    fun onResult(result:String)

    fun onImgResult(result:String)
}
