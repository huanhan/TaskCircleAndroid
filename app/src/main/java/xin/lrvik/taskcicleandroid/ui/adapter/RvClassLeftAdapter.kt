package xin.lrvik.taskcicleandroid.ui.adapter

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.data.protocol.TaskClass

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/2.
 *
 */
class RvClassLeftAdapter(data: ArrayList<TaskClass>) : BaseQuickAdapter<TaskClass, BaseViewHolder>(R.layout.item_class_left, data) {
    override fun convert(helper: BaseViewHolder, item: TaskClass) {

        val mRlClass = helper.getView<RelativeLayout>(R.id.mRlClass)
        val mIvTag = helper.getView<ImageView>(R.id.mIvTag)
        mIvTag.visibility= View.GONE
        mRlClass.setBackgroundColor(Color.parseColor("#FCFCFC"))
        helper.setText(R.id.mTvClass, item.name)

        if (item.isSelect) {
            mRlClass.setBackgroundColor(Color.parseColor("#FFFFFF"))
            mIvTag.visibility= View.VISIBLE
        }
    }
}
