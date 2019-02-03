package xin.lrvik.taskcicleandroid.service

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.data.protocol.*
import xin.lrvik.taskcicleandroid.data.protocol.enums.UserGender
import java.sql.Timestamp

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/2/3.
 *
 */
interface UserService {

    fun detail(id: Int): Observable<User>

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
               phone: String): Observable<Result>

    fun updateIcon(id: Long, header: String): Observable<Result>

    fun upAudit(id: Int): Observable<Result>

    fun register(username: String, password: String, imageCode: String): Observable<Result>
}