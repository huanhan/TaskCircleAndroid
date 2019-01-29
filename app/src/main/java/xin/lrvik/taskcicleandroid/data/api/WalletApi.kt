package xin.lrvik.taskcicleandroid.data.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import xin.lrvik.taskcicleandroid.data.protocol.*

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/29.
 *
 */
interface WalletApi {

    @GET("app/finance/transfer/{page}/{size}/{id}")
    fun transferList(
            @Path("page") page: Int,
            @Path("size") size: Int,
            @Path("id") id: Long): Observable<Page<Transfer>>

    @GET("app/finance/{page}/{size}/{id}")
    fun userWithdrawList(
            @Path("page") page: Int,
            @Path("size") size: Int,
            @Path("id") id: Long): Observable<Page<UserWithdraw>>

    @GET("app/finance/money/{id}")
    fun cashPledgeList(
            @Path("id") id: Long): Observable<List<CashPledge>>
}