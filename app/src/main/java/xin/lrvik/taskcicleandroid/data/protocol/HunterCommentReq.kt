package xin.lrvik.taskcicleandroid.data.protocol


data class HunterCommentReq(var taskContext: String,
                            var taskStart: Float,
                            var hunterTaskId: String,
                            var userContext: String,
                            var userStart: Float
)
