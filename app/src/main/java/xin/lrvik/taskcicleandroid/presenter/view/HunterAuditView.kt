package xin.lrvik.taskcicleandroid.presenter.view

import xin.lrvik.taskcicleandroid.baselibrary.presenter.view.BaseView
import xin.lrvik.taskcicleandroid.data.protocol.HunterAudit
import xin.lrvik.taskcicleandroid.data.protocol.User

interface HunterAuditView : BaseView {

    fun onAuditResult(data: HunterAudit)
    fun onResult(result: String)

}
