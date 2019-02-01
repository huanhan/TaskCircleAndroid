package xin.lrvik.taskcicleandroid.data.api

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import xin.lrvik.taskcicleandroid.data.protocol.AddChatReq
import xin.lrvik.taskcicleandroid.data.protocol.Chat
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.data.protocol.Result

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/21.
 *
 */
interface ChatApi {

    @GET("app/chat/{taskid}/{hunterid}/{userid}/{page}/{size}")
    fun chatDetail(@Path("taskid") taskId: String,
                   @Path("hunterid") hunterid: Long,
                   @Path("userid") userid: Long,
                   @Path("page") page: Int,
                   @Path("size") size: Int): Observable<Page<Chat>>

    @POST("app/chat/{id}")
    fun saveChat(@Body req: AddChatReq, @Path("id") id: Long): Observable<Chat>


}