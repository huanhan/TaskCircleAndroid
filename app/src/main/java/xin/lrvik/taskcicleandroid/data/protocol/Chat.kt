package xin.lrvik.taskcicleandroid.data.protocol

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/8.
 *
 */
data class Chat(val icon: String,
                val content: String,
                val type: Int) : MultiItemEntity {


    override fun getItemType(): Int {
        return type
    }

    companion object {
        val RECEIVE_TYPE = 1
        val SEND_TYPE = 0
    }
}