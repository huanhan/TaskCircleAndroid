package xin.lrvik.taskcicleandroid.ui.adapter

import android.text.format.DateFormat
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.loadUrl
import xin.lrvik.taskcicleandroid.data.protocol.UserWithdraw
import xin.lrvik.taskcicleandroid.data.protocol.enums.WithdrawType

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/2.
 *
 */
class RvUserWithdrawAdapter(data: List<UserWithdraw>) : BaseQuickAdapter<UserWithdraw, BaseViewHolder>(R.layout.item_withdraw, data) {
    override fun convert(helper: BaseViewHolder, item: UserWithdraw) {

        helper.setText(R.id.mTvTitle, item.type.type)
                .setText(R.id.mTvTime, "${DateFormat.format("yyyy年MM月dd", item.createTime)}")

        var mIvIcon = helper.getView<ImageView>(R.id.mIvIcon)

        if (item.type == WithdrawType.PAY) {
            helper.setText(R.id.mTvMoney, "+${item.money}")
                    .setVisible(R.id.mTvState, false)
            mIvIcon.loadUrl(R.mipmap.recharge)
        } else {
            helper.setText(R.id.mTvMoney, "-${item.money}")
                    .setVisible(R.id.mTvState, true)
                    .setText(R.id.mTvState, item.state.state)
            mIvIcon.loadUrl(R.mipmap.withdrawal)
        }
    }
}
