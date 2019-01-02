package xin.lrvik.taskcicleandroid.data.protocol

import java.sql.Timestamp

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/2.
 *
 */
data class Task (val id:String,
                 val name:String,
                 val money:String,
                 val type:String,
                 val context:String,
                 val peopleNumber:Int,
                 val beginTime: Timestamp,
                 val deadline: Timestamp,
                 val distance:String,
                 val stepNum:Int,
                 val icon:Int)