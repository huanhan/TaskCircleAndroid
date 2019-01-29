package xin.lrvik.taskcicleandroid.service

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.data.protocol.CashPledge
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.data.protocol.Transfer
import xin.lrvik.taskcicleandroid.data.protocol.UserWithdraw

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/29.
 *
 */
interface WalletService {

    fun transferList(page: Int, size: Int): Observable<Page<Transfer>>

    fun userWithdrawList(page: Int, size: Int): Observable<Page<UserWithdraw>>

    fun cashPledgeList(): Observable<List<CashPledge>>
}