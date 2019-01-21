package xin.lrvik.taskcicleandroid.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import xin.lrvik.easybanner.adapter.recyclerview.BaseViewHolder
import xin.lrvik.easybanner.adapter.viewpager.BaseTypeItemAdapter
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.loadUrl
import xin.lrvik.taskcicleandroid.data.protocol.TaskClassifyAppDto

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/4.
 *
 */
class EvpTypeItemAdapter : BaseTypeItemAdapter<TaskClassifyAppDto>(10, 5, R.layout.item_evp_type) {
    override fun convert(holder: BaseViewHolder, data: TaskClassifyAppDto) {
        holder.getView<ImageView>(R.id.mIvEvp).loadUrl(data.classifyImg ?: R.mipmap.def)
        holder.getView<TextView>(R.id.mTvEvp).text = data.name
    }
}