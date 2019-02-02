package xin.lrvik.taskcicleandroid.data.protocol

import java.sql.Timestamp

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/3.
 *
 */
data class Message (val context:String,
                    val createTime :Timestamp,
                    val title:String,
                    val id:Int)