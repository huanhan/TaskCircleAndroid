package xin.lrvik.taskcicleandroid.presenter.view

import xin.lrvik.taskcicleandroid.baselibrary.presenter.view.BaseView
import xin.lrvik.taskcicleandroid.data.protocol.CashPledge
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.data.protocol.Transfer

interface CashPledgeView:BaseView {

    fun onRecordResult(data : List<CashPledge>)

}
