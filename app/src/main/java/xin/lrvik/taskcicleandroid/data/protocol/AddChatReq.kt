package xin.lrvik.taskcicleandroid.data.protocol


/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/8.
 *
 */
data class AddChatReq(val hunterId: Long,
                      val userId: Long,
                      val hunterTaskId: String,
                      val context: String)