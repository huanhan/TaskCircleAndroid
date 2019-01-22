package xin.lrvik.taskcicleandroid.data.protocol

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/6.
 *
 */
data class TaskClass(var id: Long,
                     var name: String,
                     var classifyImg: String?,
                     var taskClassifies: ArrayList<TaskClass>?,
                     var isSelect: Boolean)