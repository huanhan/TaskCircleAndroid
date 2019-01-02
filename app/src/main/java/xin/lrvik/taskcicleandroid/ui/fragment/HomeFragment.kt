package xin.lrvik.taskcicleandroid.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_home.*
import xin.lrvik.easybanner.Transformer
import xin.lrvik.easybanner.adapter.viewpager.EasyImageAdapter
import xin.lrvik.easybanner.adapter.viewpager.EasyTypeItemAdapter
import xin.lrvik.easybanner.dto.TypeItem
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.loadUrl
import xin.lrvik.taskcicleandroid.baselibrary.ui.fragment.BaseFragment
import xin.lrvik.taskcicleandroid.common.*


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
                .setAdapter(object : EasyTypeItemAdapter(10, 5) {
                    override fun convert(imageView: ImageView, textView: TextView, data: TypeItem) {
                        imageView.loadUrl(data.imgUrl)
                        textView.textSize = 12f
                        textView.text = data.title
                    }
                }).data = listOf(TypeItem(HOME_TYPE_IMG_1, HOME_TYPE_TITLE_1),
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
    }

}
