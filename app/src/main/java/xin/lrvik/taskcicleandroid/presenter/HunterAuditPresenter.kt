package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.presenter.view.ClassView
import xin.lrvik.taskcicleandroid.presenter.view.HunterAuditView
import xin.lrvik.taskcicleandroid.presenter.view.MyView
import xin.lrvik.taskcicleandroid.service.TaskService
import xin.lrvik.taskcicleandroid.service.UserService
import javax.inject.Inject

class HunterAuditPresenter @Inject constructor() : BasePresenter<HunterAuditView>() {
    @Inject
    lateinit var userService: UserService

    fun hunterAudit() {
        if (!checkNetWork()) {
            return
        }

        userService.hunterAudit().execute(lifecycleProvider, mView, false) {
            mView.onAuditResult(it)
        }
    }

    fun upAudit(idCard: String,
                address: String,
                phone: String,
                idCardImgFront: String,
                idCardImgBack: String) {
        if (!checkNetWork()) {
            return
        }

        userService.upAudit(idCard, address, phone, idCardImgFront, idCardImgBack).execute(lifecycleProvider, mView, false) {
            mView.onResult(it.msg)
        }
    }
}
