package xin.lrvik.taskcicleandroid.service.impl

import io.reactivex.Observable
import xin.lrvik.taskcicleandroid.data.protocol.CashPledge
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.data.protocol.Result
import xin.lrvik.taskcicleandroid.data.protocol.Transfer
import xin.lrvik.taskcicleandroid.data.protocol.UserWithdraw
import xin.lrvik.taskcicleandroid.data.repository.WalletRepository
import xin.lrvik.taskcicleandroid.service.WalletService
import javax.inject.Inject

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/29.
 *
 */
class WalletServiceImpl @Inject constructor() : WalletService {
    @Inject
    lateinit var walletRepository: WalletRepository

    override fun transferList(page: Int, size: Int): Observable<Page<Transfer>> {
        return walletRepository.transferList(page, size)
    }

    override fun userWithdrawList(page: Int, size: Int): Observable<Page<UserWithdraw>> {
        return walletRepository.userWithdrawList(page, size)
    }

    override fun cashPledgeList(): Observable<List<CashPledge>> {
        return walletRepository.cashPledgeList()
    }


    override fun withdrawAdd(): Observable<Result> {
        return walletRepository.withdrawAdd()
    }


    override fun payAdd(): Observable<Result> {
        return walletRepository.payAdd()
    }


}