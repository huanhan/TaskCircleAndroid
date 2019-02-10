package xin.lrvik.taskcicleandroid.presenter.view

import xin.lrvik.taskcicleandroid.baselibrary.presenter.view.BaseView
import xin.lrvik.taskcicleandroid.baselibrary.data.protocol.TokenResult

interface LoginView:BaseView {
    fun onResult(result: TokenResult)
}
