package xin.lrvik.taskcicleandroid.service

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.baselibrary.data.protocol.TokenResult
import xin.lrvik.taskcicleandroid.data.protocol.*
import xin.lrvik.taskcicleandroid.data.protocol.enums.UserGender

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/2/3.
 *
 */
interface UserService {

    fun detail(): Observable<User>

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
               phone: String): Observable<Result>

    fun updateIcon(header: String): Observable<Result>

    fun upAudit(idCard: String,
                address: String,
                phone: String,
                idCardImgFront: String,
                idCardImgBack: String): Observable<Result>

    fun hunterAudit(): Observable<HunterAudit>

    fun register(username: String, password: String, imageCode: String): Observable<Result>

    fun login(username: String, password: String): Observable<TokenResult>

    fun refreshToken(refreshToken: String): Observable<TokenResult>
}