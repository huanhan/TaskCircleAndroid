package xin.lrvik.taskcicleandroid.ui.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.search.core.PoiInfo
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.startActivityForResult
import xin.lrvik.easybanner.Transformer
import xin.lrvik.easybanner.adapter.viewpager.EasyImageAdapter
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.loadUrl
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.baselibrary.ui.fragment.BaseMvpFragment
import xin.lrvik.taskcicleandroid.common.*
import xin.lrvik.taskcicleandroid.data.protocol.Home
import xin.lrvik.taskcicleandroid.data.protocol.Task
import xin.lrvik.taskcicleandroid.data.protocol.TaskClassifyAppDto
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.HomePresenter
import xin.lrvik.taskcicleandroid.presenter.view.HomeView
import xin.lrvik.taskcicleandroid.ui.activity.AddressPickerActivity
import xin.lrvik.taskcicleandroid.ui.activity.ClassActivity
import xin.lrvik.taskcicleandroid.ui.activity.SearchActivity
import xin.lrvik.taskcicleandroid.ui.adapter.EvpTypeItemAdapter
import xin.lrvik.taskcicleandroid.ui.adapter.RvRecommendAdapter
import java.util.*


class HomeFragment : BaseMvpFragment<HomePresenter>(), HomeView {

    override fun onHomeDataResult(data: Home) {
        mEvpBanner.data = data.banners
        mEvpType.data = data.taskClassifyAppDtos
        rvRecommendAdapter.setNewData(data.taskAppDtos)
    }


    lateinit var mLocationClient: LocationClient
    lateinit var rvRecommendAdapter: RvRecommendAdapter

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
                })

        mEvpType.setIndicator(mEdiType)
                .setAdapter(EvpTypeItemAdapter())
                .setOnItemClickListner { v, t ->
                    startActivity<ClassActivity>("CLASSTYPE" to (t as TaskClassifyAppDto).name!!)
                }


        mNiceSpinner.attachDataSource(listOf(SORT_TOTAL, SORT_PRICE_UP, SORT_TIME_UP, SORT_DISTANCE_UP))

        var linearLayoutManager = LinearLayoutManager(context)
        mRvRecommend.layoutManager = linearLayoutManager
        linearLayoutManager.orientation = OrientationHelper.VERTICAL
        var list = ArrayList<Task>()
        rvRecommendAdapter = RvRecommendAdapter(list)
        mRvRecommend.adapter = rvRecommendAdapter

        mTvAddress.onClick {
            startActivityForResult<AddressPickerActivity>(requestCode = 1)
        }

        mRLSearch.onClick {
            startActivity<SearchActivity>()
        }

        initLocation()
        mPresenter.homeData()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            var poiInfo = data?.getParcelableExtra<PoiInfo>("RESULT")
            poiInfo?.let {
                mTvAddress.text = poiInfo.name
                poiInfo.location?.let {
                    UserInfo.latitude = poiInfo.location.latitude
                    UserInfo.longitude = poiInfo.location.latitude
                }

            }

        }
    }

    /***
     * 初始化定位
     */
    private fun initLocation() {
        mLocationClient = LocationClient(activity)
        mLocationClient.registerLocationListener(object : BDAbstractLocationListener() {
            override fun onReceiveLocation(location: BDLocation) {
                if (!TextUtils.isEmpty(location.city)) {
                    UserInfo.latitude = location.latitude
                    UserInfo.longitude = location.longitude
                    mTvAddress.text = location.street
                    mLocationClient.stop()
                }
            }

        })

        val option = LocationClientOption()
        option.locationMode = LocationClientOption.LocationMode.Hight_Accuracy
        option.setCoorType("bd09ll")
        option.isOpenGps = true
        option.isLocationNotify = true
        option.SetIgnoreCacheException(false)
        option.isIgnoreKillProcess = false
        option.setIsNeedAddress(true)
        option.setWifiCacheTimeOut(5 * 60 * 1000)
        option.setEnableSimulateGps(false)
        mLocationClient.locOption = option

        mLocationClient.start()
    }

    override fun injectComponent() {
        DaggerTaskCircleComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
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
