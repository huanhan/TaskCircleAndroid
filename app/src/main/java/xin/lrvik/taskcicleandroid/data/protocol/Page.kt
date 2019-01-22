package xin.lrvik.taskcicleandroid.data.protocol

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/23.
 */
data class Page<T>(var isLast: Boolean,
                   var totalPages: Int,
                   var totalElements: Int,
                   var number: Int,
                   var size: Int,
                   var isFirst: Boolean,
                   var numberOfElements: Int,
                   var content: List<T>?)