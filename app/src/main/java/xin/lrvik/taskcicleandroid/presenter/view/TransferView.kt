package xin.lrvik.taskcicleandroid.presenter.view

import xin.lrvik.taskcicleandroid.baselibrary.presenter.view.BaseView
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.data.protocol.Transfer

interface TransferView:BaseView {

    fun onRecordResult(data : Page<Transfer>)

}
