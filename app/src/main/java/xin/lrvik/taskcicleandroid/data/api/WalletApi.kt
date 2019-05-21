package xin.lrvik.taskcicleandroid.data.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import xin.lrvik.taskcicleandroid.data.protocol.*

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/29.
 *
 */
interface WalletApi {

    @GET("app/finance/transfer/{page}/{size}")
    fun transferList(
            @Path("page") page: Int,
            @Path("size") size: Int): Observable<Page<Transfer>>

    @GET("app/finance/{page}/{size}")
    fun userWithdrawList(
            @Path("page") page: Int,
            @Path("size") size: Int): Observable<Page<UserWithdraw>>

    @GET("app/finance/money")
    fun cashPledgeList(): Observable<List<CashPledge>>

    @POST("app/finance/withdraw/add")
    fun withdrawAdd(): Observable<Result>


    @GET("app/finance/pay/add")
    fun payAdd(): Observable<Result>
}