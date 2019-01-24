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
        /* when (item.state) {
             TaskState.NEW_CREATE,TaskState.AUDIT_FAILURE//新建
             -> {
                 taskStates.add(TaskState.NEW_CREATE)//新建
                 taskStates.add(TaskState.AUDIT_FAILURE)//任务审核失败
             }
             "AUDIT"//待审核
             -> {
                 taskStates.add(TaskState.AWAIT_AUDIT)//等待审核
                 taskStates.add(TaskState.AUDIT)//审核中
                 taskStates.add(TaskState.AUDIT_SUCCESS)//任务审核成功
                 taskStates.add(TaskState.OK_ISSUE)//任务可以发布
             }
             "ISSUE"//已发布
             -> {
                 taskStates.add(TaskState.ISSUE)//任务发布中
                 taskStates.add(TaskState.FORBID_RECEIVE)//任务禁止被接取
                 taskStates.add(TaskState.OUT)//任务被撤回
             }
             "FINISH"//已完成
             -> {
                 taskStates.add(TaskState.FINISH)//任务完成
                 taskStates.add(TaskState.ABANDON_COMMIT)//用户提交放弃的申请
                 taskStates.add(TaskState.ABANDON_OK)//任务被放弃
                 taskStates.add(TaskState.USER_HUNTER_NEGOTIATE)//与猎刃协商中
                 taskStates.add(TaskState.HUNTER_REJECT)//猎刃拒绝协商
                 taskStates.add(TaskState.COMMIT_AUDIT)//提交管理员协商
                 taskStates.add(TaskState.ADMIN_NEGOTIATE)//管理员协商中
                 taskStates.add(TaskState.HUNTER_COMMIT)//猎刃放弃任务
             }
         }
         */
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
        val mBtAuditSuc = helper.getView<Button>(R.id.mBtAuditSuc)
        val mBtAuditFail = helper.getView<Button>(R.id.mBtAuditFail)

        item.state?.let {
            isShow(mReleaseMsg,it, listOf(TaskState.NEW_CREATE,
                    TaskState.AUDIT_FAILURE,
                    TaskState.AWAIT_AUDIT,
                    TaskState.AUDIT,
                    TaskState.AUDIT_SUCCESS,TaskState.OK_ISSUE))


            isShow(mBtModify, it, listOf(TaskState.NEW_CREATE,
                    TaskState.AUDIT_FAILURE))

            // todo 按钮是否展示 还未完成
        }


        var distance = DistanceUtil.getDistance(LatLng(UserInfo.latitude, UserInfo.longitude), LatLng(item.latitude
                ?: 0.0, item.longitude ?: 0.0)).toInt()

        var dis = if (distance < 1000) "$distance 米" else "${distance / 1000} 千米"
        helper.setText(R.id.mTvTaskName, item.name)
                .setText(R.id.mTvContext, item.context)
                .setText(R.id.mTvMoney, "${item.money} 元")
                .setText(R.id.mTvDistance, "$dis")
                .setText(R.id.mTvTime, "${DateFormat.format("yyyy年MM月dd", item.beginTime)}~${DateFormat.format("yyyy年MM月dd", item.deadline)}")
        var imageView = helper.getView<ImageView>(R.id.mIvIcon)
        imageView.loadUrl(item.headImg ?: R.mipmap.def)

    }

    //判断界面是否根据权限显示
    private fun isShow(view: View, state: TaskState, list: List<TaskState>) {
        view.visibility = if (list.contains(state)) View.VISIBLE else View.GONE
    }


}
