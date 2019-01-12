package xin.lrvik.taskcicleandroid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.text.Editable
import android.util.Log
import android.view.MenuItem
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.zaaach.citypicker.CityPicker
import com.zaaach.citypicker.adapter.OnPickListener
import com.zaaach.citypicker.model.City
import com.zaaach.citypicker.model.HotCity
import com.zaaach.citypicker.model.LocatedCity
import kotlinx.android.synthetic.main.activity_address_picker.*
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseActivity
import com.baidu.location.LocationClientOption
import com.zaaach.citypicker.model.LocateState
import android.text.TextUtils
import android.text.TextWatcher
import com.baidu.mapapi.SDKInitializer
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.core.PoiInfo
import com.baidu.mapapi.search.core.SearchResult
import com.baidu.mapapi.search.poi.*
import xin.lrvik.taskcicleandroid.ui.adapter.RvAddressAdapter


class AddressPickerActivity : BaseActivity() {

    lateinit var poiSearch: PoiSearch
    lateinit var mLocationClient: LocationClient
    lateinit var cityPicker: CityPicker
    lateinit var mAdapter: RvAddressAdapter
    var poiInfos = ArrayList<PoiInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_picker)
        initView()
    }

    private fun initView() {
        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "选择地址"
        }
        SDKInitializer.initialize(applicationContext)

        initPoiSearch()
        initLocation()
        initCityPicker()

        mTvCity.onClick {
            cityPicker.show()
        }

        mEtKyeWord.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                if (!p0.isEmpty() && mTvCity.text.toString() != "城市") {
                    var option = PoiCitySearchOption()
                    option.city(mTvCity.text.toString())
                            .pageCapacity(25)
                            .keyword(p0.toString())
                    poiSearch.searchInCity(option)
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        //初始化地址列表
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = OrientationHelper.VERTICAL
        mRvAddress.layoutManager = linearLayoutManager
        mAdapter = RvAddressAdapter(poiInfos)
        mRvAddress.adapter = mAdapter

        mAdapter.setOnItemClickListener { adapter, view, position ->
            var poiInfo = adapter.data[position] as PoiInfo
            val intent = Intent()
            intent.putExtra("RESULT", poiInfo)
            this.setResult(RESULT_OK, intent)
            this.finish()
        }


    }

    /**
     * 初始化城市选择器
     */
    private fun initCityPicker() {
        val hotCities = ArrayList<HotCity>()
        hotCities.add(HotCity("北京", "北京", "101010100")) //code为城市代码
        hotCities.add(HotCity("上海", "上海", "101020100"))
        hotCities.add(HotCity("广州", "广东", "101280101"))
        hotCities.add(HotCity("深圳", "广东", "101280601"))
        hotCities.add(HotCity("杭州", "浙江", "101210101"))
        cityPicker = CityPicker.from(this)
                .enableAnimation(true)
                .setLocatedCity(LocatedCity("杭州", "浙江", "101210101"))
                .setLocatedCity(null)
                .setHotCities(hotCities)
                .setOnPickListener(object : OnPickListener {
                    override fun onPick(position: Int, data: City) {
                        mTvCity.text = data.name
                    }

                    override fun onCancel() {
                    }

                    override fun onLocate() {
                        mLocationClient.start()
                    }

                })
    }

    /***
     * 初始化Poi搜索
     */
    private fun initPoiSearch() {
        poiSearch = PoiSearch.newInstance()
        //地址信息搜索回调
        poiSearch.setOnGetPoiSearchResultListener(object : OnGetPoiSearchResultListener {
            override fun onGetPoiIndoorResult(p0: PoiIndoorResult?) {
            }

            override fun onGetPoiResult(poiResult: PoiResult) {
                if (poiResult == null || poiResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    return
                } else if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    mAdapter.setNewData(poiResult.allPoi)
                }
            }

            override fun onGetPoiDetailResult(p0: PoiDetailResult?) {
            }

            override fun onGetPoiDetailResult(p0: PoiDetailSearchResult?) {
                Log.d("test", p0.toString())
            }

        })
    }

    /***
     * 初始化定位
     */
    private fun initLocation() {
        mLocationClient = LocationClient(this)
        mLocationClient.registerLocationListener(object : BDAbstractLocationListener() {
            override fun onReceiveLocation(location: BDLocation) {
                if (!TextUtils.isEmpty(location.city) && cityPicker != null) {
                    if (mTvCity.text.toString() == "城市") {
                        mTvCity.text = location.city
                        //默认搜索定位周边信息
                        var option = PoiNearbySearchOption()
                        //获取纬度信息
                        var latitude = location.latitude
                        //获取经度信息
                        var longitude = location.longitude
                        option.location(LatLng(latitude, longitude))
                                .pageCapacity(25)
                                .keyword("小区\$写字楼\$学校")
                                .radius(1000)
                        poiSearch.searchNearby(option)
                    } else {
                        cityPicker.locateComplete(LocatedCity(location.city, location.province, location.adCode), LocateState.SUCCESS)
                    }
                    mLocationClient.stop()
                }
            }

        })

        val option = LocationClientOption()
        option.locationMode = LocationClientOption.LocationMode.Hight_Accuracy
        option.setCoorType("bd09ll")
//        option.setScanSpan(100)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
