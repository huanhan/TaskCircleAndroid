package xin.lrvik.taskcicleandroid.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.utils.DateUtils
import xin.lrvik.taskcicleandroid.data.protocol.CashPledge

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/2.
 *
 */
class RvCashPledgeAdapter(data: List<CashPledge>) : BaseQuickAdapter<CashPledge, BaseViewHolder>(R.layout.item_cash_pledge, data) {
    override fun convert(helper: BaseViewHolder, item: CashPledge) {
        helper.setText(R.id.mTvTitle, "类型：${item.name}押金")
                .setText(R.id.mTvContent, "金额：${item.money}元")
                .setText(R.id.mTvTime, "${DateUtils.convertTimeToString(item.createTime)}")

    }
}
