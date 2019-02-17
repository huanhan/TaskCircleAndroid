package xin.lrvik.taskcicleandroid.ui.adapter

import android.text.format.DateFormat
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.isShow
import xin.lrvik.taskcicleandroid.baselibrary.ext.loadCircleUrl
import xin.lrvik.taskcicleandroid.data.protocol.HunterTask
import xin.lrvik.taskcicleandroid.data.protocol.enums.HunterTaskState


/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/2.
 *
 */
class RvHunterRunningAdapter(data: List<HunterTask>) : BaseQuickAdapter<HunterTask, BaseViewHolder>(R.layout.item_hunter_running, data) {
    override fun convert(helper: BaseViewHolder, item: HunterTask) {

        //按钮根据状态决定是否显示
        val mBtAuditSuccess = helper.getView<Button>(R.id.mBtAuditSuccess)
        val mBtAuditFailure = helper.getView<Button>(R.id.mBtAuditFailure)
        val mBtAbandonPass = helper.getView<Button>(R.id.mBtAbandonPass)
        val mBtAbandonNotPass = helper.getView<Button>(R.id.mBtAbandonNotPass)
        val mBtAbandon = helper.getView<Button>(R.id.mBtAbandon)
        val mBtWarning = helper.getView<ImageView>(R.id.mBtWarning)
        val mBtEva = helper.getView<Button>(R.id.mBtEva)

        mBtWarning.visibility = if (!item.context.isNullOrEmpty() || !item.hunterRejectContext.isNullOrEmpty()) View.VISIBLE else View.GONE

        item.state?.let {

            isShow(mBtAuditSuccess, it, listOf(HunterTaskState.AWAIT_USER_AUDIT))

            isShow(mBtAuditFailure, it, listOf(HunterTaskState.AWAIT_USER_AUDIT))

            isShow(mBtAbandonPass, it, listOf(HunterTaskState.WITH_USER_NEGOTIATE))

            isShow(mBtAbandonNotPass, it, listOf(HunterTaskState.WITH_USER_NEGOTIATE))

            isShow(mBtEva, it, listOf(HunterTaskState.END_NO,
                    HunterTaskState.END_OK,
                    HunterTaskState.TASK_ABANDON,
                    HunterTaskState.TASK_BE_ABANDON))

            if (mBtEva.visibility == View.VISIBLE) {
                isShow(mBtEva, !item.userCHunter!!)
            }

            //猎刃拒绝的时候才能出现放弃按钮
            isShow(mBtAbandon,it, listOf(HunterTaskState.HUNTER_REPULSE))
        }

        if (item.acceptTime != null) {
            helper.setText(R.id.mTvTime, "${DateFormat.format("yyyy年MM月dd", item.acceptTime)}")
        }

        helper.setText(R.id.mTvHunterName, item.hunterName)
                .setText(R.id.mTvState, item.state!!.state)
                .setText(R.id.mTvContext, if (item.curStep == 0) "任务还未开始执行" else "已执行完第${item.curStep}步")
                .addOnClickListener(R.id.mBtAuditSuccess)
                .addOnClickListener(R.id.mBtAuditFailure)
                .addOnClickListener(R.id.mBtAbandonPass)
                .addOnClickListener(R.id.mBtAbandonNotPass)
                .addOnClickListener(R.id.mBtChat)
                .addOnClickListener(R.id.mBtWarning)
                .addOnClickListener(R.id.mBtEva)
                .addOnClickListener(R.id.mBtAbandon)
//                .setVisible(R.id.mBtWarning, !item.auditContext.isNullOrEmpty())
        var imageView = helper.getView<ImageView>(R.id.mIvIcon)
        imageView.loadCircleUrl(item.hunterHeadImg ?: R.mipmap.def)

    }

    private fun isShow(view: View, state: HunterTaskState, list: List<HunterTaskState>) {
        isShow(view, list.contains(state))
    }

    private fun isShow(view: View, state: HunterTaskState, list: List<HunterTaskState>, list2: List<HunterTaskState>) {
        isShow(view, list.contains(state) && !list2.contains(state))
    }

    private fun isShow(view: View, isVis: Boolean) {
        view.visibility = if (isVis) View.VISIBLE else View.GONE
    }


}
