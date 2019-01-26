package xin.lrvik.taskcicleandroid.service.impl

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.data.protocol.*
import xin.lrvik.taskcicleandroid.data.repository.HunterTaskRepository
import xin.lrvik.taskcicleandroid.service.HunterTaskService
import javax.inject.Inject

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
class HunterTaskServiceImpl @Inject constructor() : HunterTaskService {

    @Inject
    lateinit var hunterTaskRepository: HunterTaskRepository


    override fun queryByState(state: String,
                              page: Int,
                              size: Int): Observable<Page<HunterTask>> {
        return hunterTaskRepository.queryByState(state, page, size)
    }

}