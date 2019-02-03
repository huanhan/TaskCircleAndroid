package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.data.protocol.enums.UserGender
import xin.lrvik.taskcicleandroid.presenter.view.ModifyUserView
import xin.lrvik.taskcicleandroid.service.UserService
import javax.inject.Inject

class ModifyUserPresenter @Inject constructor() : BasePresenter<ModifyUserView>() {
    @Inject
    lateinit var userService: UserService

    fun detail() {
        if (!checkNetWork()) {
            return
        }

        userService.detail().execute(lifecycleProvider, mView, false) {
            mView.onUserResult(it)
        }
    }

    fun updateIcon(header: String) {
        if (!checkNetWork()) {
            return
        }

        userService.updateIcon(header).execute(lifecycleProvider, mView, false) {
            mView.onImgResult(it.msg)
        }
    }


    fun update(name: String,
               gender: UserGender,
               idCard: String,
               address: String,
               school: String,
               major: String,
               interest: String,
               intro: String,
               height: Int?,
               weight: Int?,
               birthday: Long?,
               phone: String) {
        if (!checkNetWork()) {
            return
        }

        userService.update(name, gender, idCard, address, school, major, interest, intro, height, weight, birthday, phone).execute(lifecycleProvider, mView, false) {
            mView.onResult(it.msg)
        }
    }
}
