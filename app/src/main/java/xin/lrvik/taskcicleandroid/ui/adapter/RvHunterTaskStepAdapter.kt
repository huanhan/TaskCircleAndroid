package xin.lrvik.taskcicleandroid.ui.adapter

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseItemDraggableAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.data.protocol.HunterRunningStep

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/6.
 *
 */
class RvHunterTaskStepAdapter(data: List<HunterRunningStep>) : BaseItemDraggableAdapter<HunterRunningStep,
        BaseViewHolder>(R.layout.item_hunter_task_step, data) {

    override fun convert(helper: BaseViewHolder, item: HunterRunningStep) {

        helper.setText(R.id.mTvtitle, item.taskTitle)
                .setText(R.id.mTvContent, item.taskContext)
                .setText(R.id.mTvStep, "步骤${item.step}")
                .addOnClickListener(R.id.mBtSubmit)
                .addOnClickListener(R.id.mBtModify)

        var mLlHunterMarker = helper.getView<LinearLayout>(R.id.mLlHunterMarker)
        var mBtSubmit = helper.getView<Button>(R.id.mBtSubmit)
        var mBtModify = helper.getView<Button>(R.id.mBtModify)
        var mIvFlag = helper.getView<ImageView>(R.id.mIvFlag)
        if (item.hunterTaskContext == null) {
            helper.setVisible(R.id.mBtSubmit, true)
            mLlHunterMarker.visibility= View.GONE
            mBtSubmit.visibility= View.VISIBLE
            mBtModify.visibility= View.GONE
            mIvFlag.visibility= View.GONE
        } else {
            helper.setVisible(R.id.mBtSubmit, false)
                    .setText(R.id.mTvContext, item.hunterTaskContext)
                    .setText(R.id.mTvTitle, item.hunterTaskRemake)
            mLlHunterMarker.visibility= View.VISIBLE
            mBtSubmit.visibility= View.GONE
            mBtModify.visibility= View.VISIBLE
            mIvFlag.visibility= View.VISIBLE
        }


    }
}