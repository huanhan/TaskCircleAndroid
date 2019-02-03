package xin.lrvik.taskcicleandroid.data.repository

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*
import xin.lrvik.taskcicleandroid.baselibrary.data.net.RetrofitFactory
import xin.lrvik.taskcicleandroid.common.UserInfo
import xin.lrvik.taskcicleandroid.data.api.HomeApi
import xin.lrvik.taskcicleandroid.data.api.UserApi
import xin.lrvik.taskcicleandroid.data.protocol.*
import xin.lrvik.taskcicleandroid.data.protocol.enums.UserGender
import java.sql.Timestamp
import javax.inject.Inject

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
class UserRepository @Inject constructor() {

    fun detail(id: Int): Observable<User> {
        return RetrofitFactory.instance.create(UserApi::class.java).detail(id)
    }

    fun update(id: Long,
               name: String,
               gender: UserGender,
               idCard: String,
               address: String,
               school: String,
               major: String,
               interest: String,
               intro: String,
               height: Int,
               weight: Int,
               birthday: Timestamp,
               phone: String): Observable<Result> {
        return RetrofitFactory.instance.create(UserApi::class.java).update(ModifyUser(id, name, gender, idCard, address, school, major, interest, intro, height, weight, birthday, phone))
    }

    fun updateIcon(id: Long, header: String): Observable<Result> {
        return RetrofitFactory.instance.create(UserApi::class.java).updateIcon(ModifyUserHeader(id, header))
    }

    fun upAudit(id: Int): Observable<Result> {
        return RetrofitFactory.instance.create(UserApi::class.java).upAudit(id)
    }

    fun register(username: String, password: String, imageCode: String): Observable<Result> {
        return RetrofitFactory.instance.create(UserApi::class.java).register(RegisterUser(username, password, imageCode))
    }
}