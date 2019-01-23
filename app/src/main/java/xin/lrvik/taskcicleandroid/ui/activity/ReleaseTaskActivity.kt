package xin.lrvik.taskcicleandroid.ui.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import cn.qqtheme.framework.picker.DateTimePicker
import cn.qqtheme.framework.picker.TimePicker
import com.baidu.mapapi.search.core.PoiInfo
import kotlinx.android.synthetic.main.activity_release_task.*
import org.jetbrains.anko.startActivityForResult
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.ui.widget.KeyboardUtil
import java.util.*

class ReleaseTaskActivity : AppCompatActivity() {

    var longitude: Double = 0.0
    var latitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_release_task)
        initView()
    }

    private fun initView() {

        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "发布任务"
        }

        KeyboardUtil(mKeyBoardView, mEtPeoNum)
        KeyboardUtil(mKeyBoardView, mEtMoneyNum)
        KeyboardUtil(mKeyBoardView, mEtCompensateMoney)

        var mTextWatcher = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                calcMoney()
            }
        }

        mEtPeoNum.addTextChangedListener(mTextWatcher)
        mEtMoneyNum.addTextChangedListener(mTextWatcher)



        mCvBeginTime.onClick {
            showDateTimePicker(mTvBeginTime)
        }

        mCvDeadline.onClick {
            showDateTimePicker(mTvDeadline)
        }

        mCvPermitAbandonMinute.onClick {
            showTimePicker(mTvPermitAbandonMinute)
        }

        mCvLocation.onClick {
            startActivityForResult<AddressPickerActivity>(requestCode = 1)
        }

        mSwCompensate.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                mCvCompensateMoney.visibility = View.VISIBLE
            } else {
                mCvCompensateMoney.visibility = View.GONE
            }
        }
    }

    private fun calcMoney() {
        var peoNumStr = mEtPeoNum.text.toString()
        var moneyNumStr = mEtMoneyNum.text.toString()
        if (peoNumStr.isEmpty() || moneyNumStr.isEmpty()) {
            mTvTotalMoney.text = "0"
            return
        }

        var peoNum = peoNumStr.toBigDecimal()
        var money = moneyNumStr.toBigDecimal()
        if (peoNum.toFloat() != 0.0f && money.toFloat() != 0.0f) {
            mTvTotalMoney.text = money.multiply(peoNum).toString()
        } else {
            mTvTotalMoney.text = "0"
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            var poiInfo = data?.getParcelableExtra<PoiInfo>("RESULT")
            poiInfo?.let {
                mTvLocation.text = poiInfo.name
                poiInfo.location?.let {
                    latitude = poiInfo.location.latitude
                    longitude = poiInfo.location.longitude
                }
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mKeyBoardView.visibility == View.VISIBLE) {
                mKeyBoardView.visibility = View.GONE
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun showDateTimePicker(textView: TextView) {

        //获取当前时间
        val calendar = Calendar.getInstance()
        var picker = DateTimePicker(this, DateTimePicker.HOUR_24);//24小时值
        picker.setDateRangeStart(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE))//日期起点
        //加上2个月
        calendar.add(Calendar.MONTH, 2)
        picker.setDateRangeEnd(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE))//日期终点
        picker.setTimeRangeStart(0, 0)//时间范围起点
        picker.setTimeRangeEnd(23, 59)//时间范围终点
        picker.setOnDateTimePickListener(DateTimePicker.OnYearMonthDayTimePickListener { year, month, day, hour, minute ->
            textView.text = "$year-$month-$day $hour:$minute"
        })
        picker.show()
    }

    private fun showTimePicker(textView: TextView) {
        var picker = TimePicker(this, TimePicker.HOUR_24)
        picker.setRangeStart(0, 0)//时间范围起点
        picker.setRangeEnd(23, 59)//时间范围终点
        picker.setOnTimePickListener { hour, minute ->
            textView.text = "$hour:$minute"
        }
        picker.show()
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
