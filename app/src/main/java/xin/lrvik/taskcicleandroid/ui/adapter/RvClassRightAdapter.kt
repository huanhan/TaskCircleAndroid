package xin.lrvik.taskcicleandroid.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.data.protocol.TaskClass

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/2.
 *
 */
class RvClassRightAdapter(data: ArrayList<TaskClass>) : BaseQuickAdapter<TaskClass, BaseViewHolder>(R.layout.item_class_right, data) {
    override fun convert(helper: BaseViewHolder, item: TaskClass) {

        val mIvIcon = helper.getView<ImageView>(R.id.mIvIcon)
        helper.setText(R.id.mTvName, item.name)
        helper.setTextColor(R.id.mTvName,mContext.resources.getColor(R.color.text_normal))

        if (item.isSelect) {
            helper.setTextColor(R.id.mTvName,mContext.resources.getColor(R.color.colorPrimaryDark))
        }
    }
}
