package xin.lrvik.taskcicleandroid.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.startActivity
import xin.lrvik.easybanner.Transformer
import xin.lrvik.easybanner.adapter.viewpager.EasyImageAdapter
import xin.lrvik.easybanner.adapter.viewpager.EasyTypeItemAdapter
import xin.lrvik.easybanner.dto.TypeItem
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.loadUrl
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.baselibrary.ui.fragment.BaseFragment
import xin.lrvik.taskcicleandroid.common.*
import xin.lrvik.taskcicleandroid.data.protocol.Task
import xin.lrvik.taskcicleandroid.ui.activity.AddressPickerActivity
import xin.lrvik.taskcicleandroid.ui.activity.ClassActivity
import xin.lrvik.taskcicleandroid.ui.adapter.EvpTypeItemAdapter
import xin.lrvik.taskcicleandroid.ui.adapter.RvRecommendAdapter
import java.sql.Timestamp
import java.util.*


class HomeFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mEvpBanner.setBannerAnimation(Transformer.Accordion)
                .setIndicator(mEdiBanner)
                .setAdapter(object : EasyImageAdapter<String>() {
                    override fun convert(view: ImageView, data: String) {
                        view.loadUrl(data)
                    }

                }).data = listOf(HOME_BANNER_ONE, HOME_BANNER_TWO)


        mEvpType.setIndicator(mEdiType)
                .setAdapter(EvpTypeItemAdapter())
                .setOnItemClickListner { v, t ->
                    startActivity<ClassActivity>("CLASSTYPE" to (t as TypeItem).title)
                }.data = listOf(TypeItem(HOME_TYPE_IMG_1, HOME_TYPE_TITLE_1),
                TypeItem(HOME_TYPE_IMG_2, HOME_TYPE_TITLE_2),
                TypeItem(HOME_TYPE_IMG_3, HOME_TYPE_TITLE_3),
                TypeItem(HOME_TYPE_IMG_4, HOME_TYPE_TITLE_4),
                TypeItem(HOME_TYPE_IMG_5, HOME_TYPE_TITLE_5),
                TypeItem(HOME_TYPE_IMG_6, HOME_TYPE_TITLE_6),
                TypeItem(HOME_TYPE_IMG_7, HOME_TYPE_TITLE_7),
                TypeItem(HOME_TYPE_IMG_8, HOME_TYPE_TITLE_8),
                TypeItem(HOME_TYPE_IMG_9, HOME_TYPE_TITLE_9),
                TypeItem(HOME_TYPE_IMG_10, HOME_TYPE_TITLE_10),
                TypeItem(HOME_TYPE_IMG_11, HOME_TYPE_TITLE_11),
                TypeItem(HOME_TYPE_IMG_12, HOME_TYPE_TITLE_12),
                TypeItem(HOME_TYPE_IMG_13, HOME_TYPE_TITLE_13),
                TypeItem(HOME_TYPE_IMG_14, HOME_TYPE_TITLE_14),
                TypeItem(HOME_TYPE_IMG_15, HOME_TYPE_TITLE_15),
                TypeItem(HOME_TYPE_IMG_16, HOME_TYPE_TITLE_16))



        mNiceSpinner.attachDataSource(listOf(SORT_TOTAL, SORT_PRICE_UP, SORT_TIME_UP, SORT_DISTANCE_UP))

        var linearLayoutManager = LinearLayoutManager(context)
        mRvRecommend.layoutManager = linearLayoutManager
        linearLayoutManager.orientation = OrientationHelper.VERTICAL
        var list = ArrayList<Task>()

        for (i in 0..10) {
            list.add(Task("$i ", "急需$i 名跑腿小老弟", "${i * 5}", "跑腿$i",
                    "马上需要跑腿小老弟一只啊，有没有跑腿小老弟马上就要跑了。马上需要跑腿小老弟一只啊，有没有跑腿小老弟马上就要跑了。马上需要跑腿小老弟一只啊，" +
                            "有没有跑腿小老弟马上就要跑了。马上需要跑腿小老弟一只啊，有没有跑腿小老弟马上就要跑了。马上需要跑腿小老弟一只啊，有没有跑腿小老弟马上就要跑了。",
                    i + 5, Timestamp(Date().time), Timestamp(Date().time + 1000 * 60 * 60 * 24), "200米", i + 5, R.mipmap.def))
        }
        mRvRecommend.adapter = RvRecommendAdapter(list)

        mTvAddress.onClick {
            startActivity<AddressPickerActivity>()
        }
    }

    override fun onPause() {
        super.onPause()
        mEvpBanner.stopAutoPlay()
    }

    override fun onResume() {
        super.onResume()
        mEvpBanner.startAutoPlay()
    }

}
