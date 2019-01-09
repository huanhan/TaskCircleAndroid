package xin.lrvik.taskcicleandroid.ui.adapter

import android.text.format.DateFormat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.data.protocol.Budget

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/2.
 *
 */
class RvWalletDetailAdapter(data: ArrayList<Budget>) : BaseQuickAdapter<Budget, BaseViewHolder>(R.layout.item_wallet_budget, data) {
    override fun convert(helper: BaseViewHolder, item: Budget) {
        helper.setText(R.id.mTvTitle, item.context)
                .setText(R.id.mTvTime, "${DateFormat.format("yyyy年MM月dd", item.createTime)}")
                .setText(R.id.mTvMoney, "${item.money}")
        if (item.money > 0) {
            helper.setTextColor(R.id.mTvMoney, mContext.resources.getColor(R.color.common_green))
        } else {
            helper.setTextColor(R.id.mTvMoney, mContext.resources.getColor(R.color.common_black))
        }

    }
}
