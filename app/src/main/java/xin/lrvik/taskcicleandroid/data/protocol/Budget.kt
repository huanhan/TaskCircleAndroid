package xin.lrvik.taskcicleandroid.data.protocol

import java.sql.Timestamp

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/9.
 *
 */
data class Budget(val createTime: Timestamp,
                  val money: Float,
                  val context: String
)