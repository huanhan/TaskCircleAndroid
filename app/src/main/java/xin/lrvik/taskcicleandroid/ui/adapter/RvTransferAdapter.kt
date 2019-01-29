package xin.lrvik.taskcicleandroid.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.loadUrl
import xin.lrvik.taskcicleandroid.baselibrary.utils.DateUtils
import xin.lrvik.taskcicleandroid.common.UserInfo
import xin.lrvik.taskcicleandroid.data.protocol.Transfer
import xin.lrvik.taskcicleandroid.data.protocol.enums.WithdrawType

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/2.
 *
 */
class RvTransferAdapter(data: List<Transfer>) : BaseQuickAdapter<Transfer, BaseViewHolder>(R.layout.item_transfer, data) {
    override fun convert(helper: BaseViewHolder, item: Transfer) {
        var mIvIcon = helper.getView<ImageView>(R.id.mIvIcon)

        helper.setText(R.id.mTvTitle, item.context)
                .setText(R.id.mTvTime, "${DateUtils.convertTimeToString(item.createTime)}")

        if (UserInfo.userId==item.me) {
            helper.setText(R.id.mTvContent, "支出给 用户：${item.toName} 金额：${item.money}元")
            mIvIcon.loadUrl(R.mipmap.recharge)
        } else {
            helper.setText(R.id.mTvContent, "从 用户：${item.meName} 收入金额：${item.money}元")
            mIvIcon.loadUrl(R.mipmap.withdrawal)
        }

    }
}
