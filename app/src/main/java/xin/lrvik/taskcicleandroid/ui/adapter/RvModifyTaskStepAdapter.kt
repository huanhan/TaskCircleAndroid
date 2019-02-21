package xin.lrvik.taskcicleandroid.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseItemDraggableAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.loadUrl
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.data.protocol.TaskStep
import xin.lrvik.taskcicleandroid.ui.widget.LinesEditView

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/6.
 *
 */
class RvModifyTaskStepAdapter(data: List<TaskStep>, var isModify: Boolean = false) : BaseItemDraggableAdapter<TaskStep,
        BaseViewHolder>(R.layout.item_modify_task_step, data) {

    override fun convert(helper: BaseViewHolder, item: TaskStep) {

        helper.setText(R.id.mTvtitle, item.title).setText(R.id.mTvContent, item.context)
                .addOnClickListener(R.id.mIvIcon)
                .addOnClickListener(R.id.mIvFlag)

        var mIvIcon = helper.getView<ImageView>(R.id.mIvIcon)

        mIvIcon.loadUrl(item.img)

        var mTvtitle = helper.getView<TextView>(R.id.mTvtitle)
        var mTvContent = helper.getView<TextView>(R.id.mTvContent)
        var mEtStepTitle = helper.getView<TextView>(R.id.mEtStepTitle)
        var mLevStepContent = helper.getView<LinesEditView>(R.id.mLevStepContent)

        var mIvFlag = helper.getView<ImageView>(R.id.mIvFlag)
        var mIvCheck = helper.getView<ImageView>(R.id.mIvCheck)
        var mLlModify = helper.getView<LinearLayout>(R.id.mLlModify)
        var mLldef = helper.getView<LinearLayout>(R.id.mLldef)

        if (isModify) {
            mIvFlag.visibility = View.VISIBLE

           /* mIvFlag.onClick {
                mLlModify.animate().scaleY(1f)
                mLldef.animate().scaleY(0f)

                mLldef.visibility = View.GONE

                mIvCheck.visibility = View.VISIBLE
                mLlModify.visibility = View.VISIBLE
                mIvFlag.visibility = View.GONE

                mEtStepTitle.text = mTvtitle.text
                mLevStepContent.contentText = mTvContent.text.toString()
            }

            mIvCheck.onClick {
                mLlModify.animate().scaleY(0f)
                mLldef.animate().scaleY(1f)

                mLlModify.visibility = View.GONE
                mIvFlag.visibility = View.VISIBLE
                mIvCheck.visibility = View.GONE
                mLldef.visibility = View.VISIBLE

                mTvtitle.text = mEtStepTitle.text
                mTvContent.text = mLevStepContent.contentText

                item.title = mTvtitle.text.toString()
                item.context = mTvContent.text.toString()

                notifyDataSetChanged()
            }*/

        } else {
            mIvFlag.visibility = View.GONE
        }



    }
}