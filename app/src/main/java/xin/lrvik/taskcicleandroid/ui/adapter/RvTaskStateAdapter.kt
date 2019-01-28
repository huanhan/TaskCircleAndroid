package xin.lrvik.taskcicleandroid.ui.adapter

import android.text.format.DateFormat
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.utils.DistanceUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.loadUrl
import xin.lrvik.taskcicleandroid.common.UserInfo
import xin.lrvik.taskcicleandroid.data.protocol.Task
import xin.lrvik.taskcicleandroid.data.protocol.enums.TaskState


/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/2.
 *
 */
class RvTaskStateAdapter(data: List<Task>) : BaseQuickAdapter<Task, BaseViewHolder>(R.layout.item_task_state, data) {
    override fun convert(helper: BaseViewHolder, item: Task) {

        //按钮根据状态决定是否显示
        val mReleaseMsg = helper.getView<LinearLayout>(R.id.mReleaseMsg)
        val mBtModify = helper.getView<Button>(R.id.mBtModify)
        val mBtSubmitAudit = helper.getView<Button>(R.id.mBtSubmitAudit)
        val mBtCancelAudit = helper.getView<Button>(R.id.mBtCancelAudit)
        val mBtRelease = helper.getView<Button>(R.id.mBtRelease)
        val mBtOut = helper.getView<Button>(R.id.mBtOut)
        val mBtUpper = helper.getView<Button>(R.id.mBtUpper)
        val mBtAbandon = helper.getView<Button>(R.id.mBtAbandon)
        val mBtCancelAbandon = helper.getView<Button>(R.id.mBtCancelAbandon)

        item.state?.let {
            //发布的信息的界面
            isShow(mReleaseMsg, it, listOf(TaskState.ISSUE,
                    TaskState.FORBID_RECEIVE,
                    TaskState.OUT,
                    TaskState.FINISH,
                    TaskState.ABANDON_COMMIT,
                    TaskState.ABANDON_OK,
                    TaskState.USER_HUNTER_NEGOTIATE,
                    TaskState.HUNTER_REJECT,
                    TaskState.COMMIT_AUDIT,
                    TaskState.ADMIN_NEGOTIATE,
                    TaskState.HUNTER_COMMIT))

            //修改信息
            isShow(mBtModify, it, listOf(TaskState.NEW_CREATE,
                    TaskState.AUDIT_FAILURE))

            //提交审核
            isShow(mBtSubmitAudit, it, listOf(TaskState.NEW_CREATE,
                    TaskState.HUNTER_REJECT))

            //取消审核
            isShow(mBtCancelAudit, it, listOf(TaskState.AWAIT_AUDIT,
                    TaskState.AUDIT,
                    TaskState.ADMIN_NEGOTIATE,
                    TaskState.COMMIT_AUDIT))

            //发布
            isShow(mBtRelease, it, listOf(TaskState.AUDIT_SUCCESS,
                    TaskState.OK_ISSUE))

            //撤回
            isShow(mBtOut, it, listOf(TaskState.ISSUE))

            //上架
            isShow(mBtUpper, it, listOf(TaskState.OUT))

            //放弃任务
            isShow(mBtAbandon, it, listOf(TaskState.FORBID_RECEIVE, TaskState.OUT))

            //取消放弃
            isShow(mBtCancelAbandon, it, listOf(TaskState.ABANDON_COMMIT))

        }

        if (item.latitude != null && item.longitude != null) {
            var distance = DistanceUtil.getDistance(LatLng(UserInfo.latitude, UserInfo.longitude), LatLng(item.latitude
                    ?: 0.0, item.longitude ?: 0.0)).toInt()
            var dis = if (distance < 1000) "$distance 米" else "${distance / 1000} 千米"
            helper.setText(R.id.mTvDistance, "$dis")
        }

        if (item.money != null) {
            helper.setText(R.id.mTvMoney, "${item.money!!.toBigDecimal().divide(item.peopleNumber!!.toBigDecimal())}元/人")
        }

        if (item.beginTime != null && item.deadline != null) {
            helper.setText(R.id.mTvTime, "${DateFormat.format("yyyy年MM月dd", item.beginTime)}~${DateFormat.format("yyyy年MM月dd", item.deadline)}")
        }

        helper.setText(R.id.mTvTaskName, item.name)
                .setText(R.id.mTvState, item.state!!.state)
                .setText(R.id.mTvContext, item.context)
                .addOnClickListener(R.id.mBtModify)
                .addOnClickListener(R.id.mBtSubmitAudit)
                .addOnClickListener(R.id.mBtCancelAudit)
                .addOnClickListener(R.id.mBtRelease)
                .addOnClickListener(R.id.mBtOut)
                .addOnClickListener(R.id.mBtUpper)
                .addOnClickListener(R.id.mBtAbandon)
                .addOnClickListener(R.id.mBtCancelAbandon)
        var imageView = helper.getView<ImageView>(R.id.mIvIcon)
        imageView.loadUrl(item.headImg ?: R.mipmap.def)

    }

    //判断界面是否根据权限显示
    private fun <T> isShow(view: View, state: T, list: List<T>) {
        view.visibility = if (list.contains(state)) View.VISIBLE else View.GONE
    }


}
