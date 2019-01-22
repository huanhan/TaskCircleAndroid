package xin.lrvik.taskcicleandroid.data.protocol

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/22.
 *
 */
data class AddTaskReq(var name: String,
                      var context: String,
                      var classify: List<Long>,
                      var taskSteps: List<TaskStep>)