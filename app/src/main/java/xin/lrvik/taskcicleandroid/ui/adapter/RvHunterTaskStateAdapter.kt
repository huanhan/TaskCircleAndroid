package xin.lrvik.taskcicleandroid.ui.adapter

import android.text.format.DateFormat
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.isShow
import xin.lrvik.taskcicleandroid.baselibrary.ext.loadUrl
import xin.lrvik.taskcicleandroid.data.protocol.HunterTask
import xin.lrvik.taskcicleandroid.data.protocol.enums.HunterTaskState


/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/2.
 *
 */
class RvHunterTaskStateAdapter(data: List<HunterTask>) : BaseQuickAdapter<HunterTask, BaseViewHolder>(R.layout.item_hunter_task_state, data) {
    override fun convert(helper: BaseViewHolder, item: HunterTask) {

        //按钮根据状态决定是否显示
        val mBtAuditHistory = helper.getView<Button>(R.id.mBtAuditHistory)
        val mBtBegin = helper.getView<Button>(R.id.mBtBegin)
        val mBtSubmitAudit = helper.getView<Button>(R.id.mBtSubmitAudit)
        val mBtReWork = helper.getView<Button>(R.id.mBtReWork)
        val mBtAbandon = helper.getView<Button>(R.id.mBtAbandon)
        val mBtSubmitAdminAudit = helper.getView<Button>(R.id.mBtSubmitAdminAudit)
        val mBtCancelAdminAudit = helper.getView<Button>(R.id.mBtCancelAdminAudit)
        val mBtAgreeAbandon = helper.getView<Button>(R.id.mBtAgreeAbandon)
        val mBtDisAgreeAbandon = helper.getView<Button>(R.id.mBtDisAgreeAbandon)
        val mBtEva = helper.getView<Button>(R.id.mBtEva)

        item.state?.let {

            //评价
            isShow(mBtEva, it, listOf(HunterTaskState.END_NO,
                    HunterTaskState.END_OK,
                    HunterTaskState.TASK_ABANDON,
                    HunterTaskState.TASK_BE_ABANDON))

            if (mBtEva.visibility == View.VISIBLE) {
                isShow(mBtEva, !item.hunterCTask!! || !item.hunterCUser!!)
            }

            //开始按钮
            isShow(mBtBegin, it, listOf(HunterTaskState.RECEIVE))

            //提交审核
            if (item.stop == true)
                mBtSubmitAudit.visibility = View.GONE
            else
                isShow(mBtSubmitAudit, it, listOf(HunterTaskState.TASK_COMPLETE,
                        HunterTaskState.ALLOW_REWORK_ABANDON_HAVE_COMPENSATE,
                        HunterTaskState.ALLOW_REWORK_ABANDON_NO_COMPENSATE,
                        HunterTaskState.NO_REWORK_NO_COMPENSATE,
                        HunterTaskState.NO_REWORK_HAVE_COMPENSATE))

            //重做
            isShow(mBtReWork, it, listOf(HunterTaskState.ALLOW_REWORK_ABANDON_HAVE_COMPENSATE,
                    HunterTaskState.ALLOW_REWORK_ABANDON_NO_COMPENSATE))

            isShow(mBtAbandon, it, listOf(HunterTaskState.RECEIVE,
                    HunterTaskState.BEGIN,
                    HunterTaskState.EXECUTE,
                    HunterTaskState.TASK_COMPLETE,
                    HunterTaskState.ALLOW_REWORK_ABANDON_HAVE_COMPENSATE,
                    HunterTaskState.ALLOW_REWORK_ABANDON_NO_COMPENSATE,
                    HunterTaskState.NO_REWORK_NO_COMPENSATE,
                    HunterTaskState.USER_REPULSE,
                    HunterTaskState.NO_REWORK_HAVE_COMPENSATE),
                    listOf(HunterTaskState.END_NO,
                            HunterTaskState.END_OK,
                            HunterTaskState.TASK_ABANDON,
                            HunterTaskState.TASK_BE_ABANDON))

            isShow(mBtSubmitAdminAudit, it, listOf(HunterTaskState.USER_REPULSE,
                    HunterTaskState.ALLOW_REWORK_ABANDON_HAVE_COMPENSATE,
                    HunterTaskState.ALLOW_REWORK_ABANDON_NO_COMPENSATE,
                    HunterTaskState.NO_REWORK_NO_COMPENSATE,
                    HunterTaskState.NO_REWORK_HAVE_COMPENSATE))

            isShow(mBtCancelAdminAudit, it, listOf(HunterTaskState.COMMIT_TO_ADMIN,
                    HunterTaskState.WITH_ADMIN_NEGOTIATE,
                    HunterTaskState.COMMIT_ADMIN_AUDIT,
                    HunterTaskState.ADMIN_AUDIT,
                    HunterTaskState.ALLOW_REWORK_ABANDON_NO_COMPENSATE,
                    HunterTaskState.NO_REWORK_HAVE_COMPENSATE,
                    HunterTaskState.NO_REWORK_NO_COMPENSATE))

            isShow(mBtAgreeAbandon, it,listOf(HunterTaskState.WITH_HUNTER_NEGOTIATE))

            isShow(mBtDisAgreeAbandon, it,listOf(HunterTaskState.WITH_HUNTER_NEGOTIATE))

        }


        if (item.money != null) {
            helper.setText(R.id.mTvMoney, "${item.money} 元")
        }

        if (item.beginTime != null && item.acceptTime != null) {
            helper.setText(R.id.mTvTime, "${DateFormat.format("yyyy年MM月dd", item.beginTime)}")
        }

        helper.setText(R.id.mTvTaskName, item.name)
                .setText(R.id.mTvState, item.state!!.state)
                .setText(R.id.mTvContext, item.taskContext)
                .addOnClickListener(R.id.mBtBegin)
                .addOnClickListener(R.id.mBtSubmitAudit)
                .addOnClickListener(R.id.mBtReWork)
                .addOnClickListener(R.id.mBtAbandon)
                .addOnClickListener(R.id.mBtSubmitAdminAudit)
                .addOnClickListener(R.id.mBtCancelAdminAudit)
                .addOnClickListener(R.id.mBtAgreeAbandon)
                .addOnClickListener(R.id.mBtDisAgreeAbandon)
                .addOnClickListener(R.id.mBtChat)
                .addOnClickListener(R.id.mBtWarning)
                .addOnClickListener(R.id.mBtEva)
                .addOnClickListener(R.id.mBtAuditHistory)
                .setVisible(R.id.mBtWarning, !item.auditContext.isNullOrEmpty())
        var imageView = helper.getView<ImageView>(R.id.mIvIcon)
        imageView.loadUrl(item.headImg ?: R.mipmap.def)

        item.audits?.let {
            if (it.isNotEmpty()) {
                mBtAuditHistory.visibility= View.VISIBLE
            }else{
                mBtAuditHistory.visibility= View.GONE
            }
        }

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
