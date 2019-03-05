package xin.lrvik.taskcicleandroid.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.utils.DateUtils
import xin.lrvik.taskcicleandroid.data.protocol.Audit

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/2.
 *
 */
class RvAuditAdapter(data: ArrayList<Audit>) : BaseQuickAdapter<Audit, BaseViewHolder>(R.layout.item_audit, data) {
    override fun convert(helper: BaseViewHolder, item: Audit) {
        helper.setText(R.id.mTvTime, "时间:${DateUtils.convertTimeToString(item.createTime!!)}")
                .setText(R.id.mTvType, "类型：${item.type?.type}")
                .setText(R.id.mTvAuditer, "审核人：${item.auditerName}")
                .setText(R.id.mTvResult, "结果：${item.result?.state}")
                .setText(R.id.mTvReason, "原因：${item.reason}")
                .setText(R.id.mTvIdea, "建议：${item.idea}")

    }
}
