package xin.lrvik.taskcicleandroid.ui.adapter

import com.baidu.mapapi.search.core.PoiInfo
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xin.lrvik.taskcicleandroid.R

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/2.
 *
 */
class RvAddressAdapter(data: ArrayList<PoiInfo>) : BaseQuickAdapter<PoiInfo, BaseViewHolder>(R.layout.item_address, data) {
    override fun convert(helper: BaseViewHolder, item: PoiInfo) {
        helper.setText(R.id.mTvName, item.name)
                .setText(R.id.mTvAddress, item.address)

    }
}
