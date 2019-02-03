package xin.lrvik.taskcicleandroid.service.impl

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.data.protocol.Result
import xin.lrvik.taskcicleandroid.data.protocol.User
import xin.lrvik.taskcicleandroid.data.protocol.enums.UserGender
import xin.lrvik.taskcicleandroid.data.repository.UserRepository
import xin.lrvik.taskcicleandroid.service.UserService
import java.sql.Timestamp
import javax.inject.Inject

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/2/3.
 *
 */
class UserServiceImpl @Inject constructor() : UserService {

    @Inject
    lateinit var userRepository: UserRepository

    override fun detail(id: Int): Observable<User> {
        return userRepository.detail(id)
    }

    override fun update(id: Long, name: String, gender: UserGender, idCard: String, address: String, school: String, major: String, interest: String, intro: String, height: Int, weight: Int, birthday: Timestamp, phone: String): Observable<Result> {
        return userRepository.update(id, name, gender, idCard, address, school, major, interest, intro, height, weight, birthday, phone)
    }

    override fun updateIcon(id: Long, header: String): Observable<Result> {
        return userRepository.updateIcon(id, header)
    }

    override fun upAudit(id: Int): Observable<Result> {
        return userRepository.upAudit(id)
    }

    override fun register(username: String, password: String, imageCode: String): Observable<Result> {
        return userRepository.register(username, password, imageCode)
    }
}