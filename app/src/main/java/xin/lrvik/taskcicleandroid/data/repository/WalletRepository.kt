package xin.lrvik.taskcicleandroid.data.repository

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.baselibrary.data.net.RetrofitFactory
import xin.lrvik.taskcicleandroid.baselibrary.common.UserInfo
import xin.lrvik.taskcicleandroid.data.api.WalletApi
import xin.lrvik.taskcicleandroid.data.protocol.CashPledge
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.data.protocol.Transfer
import xin.lrvik.taskcicleandroid.data.protocol.UserWithdraw
import javax.inject.Inject

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/29.
 *
 */
class WalletRepository @Inject constructor() {

    fun transferList(page: Int, size: Int): Observable<Page<Transfer>> {
        return RetrofitFactory.instance.create(WalletApi::class.java).transferList(page, size, UserInfo.userId)
    }

    fun userWithdrawList(page: Int, size: Int): Observable<Page<UserWithdraw>> {
        return RetrofitFactory.instance.create(WalletApi::class.java).userWithdrawList(page, size, UserInfo.userId)
    }

    fun cashPledgeList(): Observable<List<CashPledge>> {
        return RetrofitFactory.instance.create(WalletApi::class.java).cashPledgeList(UserInfo.userId)
    }
}