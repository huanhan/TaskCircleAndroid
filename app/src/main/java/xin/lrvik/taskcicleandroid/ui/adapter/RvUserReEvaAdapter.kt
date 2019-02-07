package xin.lrvik.taskcicleandroid.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.iarcuschin.simpleratingbar.SimpleRatingBar
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.loadCircleUrl
import xin.lrvik.taskcicleandroid.baselibrary.ext.loadUrl
import xin.lrvik.taskcicleandroid.baselibrary.utils.DateUtils
import xin.lrvik.taskcicleandroid.baselibrary.utils.DateUtils.FORMAT_SHORT
import xin.lrvik.taskcicleandroid.common.UserInfo
import xin.lrvik.taskcicleandroid.data.protocol.CommentUser

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/2.
 *
 */
class RvUserReEvaAdapter(data: List<CommentUser>) : BaseQuickAdapter<CommentUser, BaseViewHolder>(R.layout.item_user_re_eva, data) {
    override fun convert(helper: BaseViewHolder, item: CommentUser) {
        var mIvIcon = helper.getView<ImageView>(R.id.mIvIcon)
        var mSrbStart = helper.getView<SimpleRatingBar>(R.id.mSrbStart)
        mSrbStart.rating = item.start
        mIvIcon.loadCircleUrl(item.img)
        helper.setText(R.id.mTvName, "${item.name}评价我")
                .setText(R.id.mTvTime, "${DateUtils.convertTimeToString(item.createTime, FORMAT_SHORT)}")
                .setText(R.id.mTvContent, item.context)

    }
}
