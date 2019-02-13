package xin.lrvik.taskcicleandroid.data.protocol

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/23.
 */
data class Page<T>(var content: ArrayList<T>,
                   var totalNum: Long,//总数据数
                   var totalPage: Int,//总页数
                   var pageNum: Int,//当前页
                   var pageSize: Int)//当前页条数