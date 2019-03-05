package xin.lrvik.taskcicleandroid.ui.activity

import android.app.Activity
import android.content.Intent
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
import org.jetbrains.anko.toast
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseMvpActivity
import xin.lrvik.taskcicleandroid.baselibrary.utils.DateUtils
import xin.lrvik.taskcicleandroid.data.protocol.TaskDetail
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.ReleaseTaskPresenter
import xin.lrvik.taskcicleandroid.presenter.view.ReleaseTaskView
import xin.lrvik.taskcicleandroid.ui.widget.KeyboardUtil
import java.util.*
import cn.qqtheme.framework.picker.OptionPicker
import org.jetbrains.anko.alert


class ReleaseTaskActivity : BaseMvpActivity<ReleaseTaskPresenter>(), ReleaseTaskView {

    var longitude: Double = 0.0
    var latitude: Double = 0.0
    var taskid = ""

    companion object {
        const val TASKID = "HUNTERTASKID"
    }

    override fun injectComponent() {
        DaggerTaskCircleComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onReleaseTaskResult(data: TaskDetail) {
        toast("发布成功")
        finish()
    }


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

        taskid = intent.getStringExtra(TASKID)

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
            showMinutePicker(mTvPermitAbandonMinute)
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

        mBtnRelease.onClick {
            if (validation()) {
                alert("即将扣除押金 ${mTvTotalMoney.text.toString().toFloat()}元，是否确认发布？") {
                    positiveButton("是") {
                        mPresenter.issueTask(taskid,
                                mTvTotalMoney.text.toString().toFloat(),
                                mEtPeoNum.text.toString().toInt(),
                                DateUtils.str2Timestamp(mTvBeginTime.text.toString()).time,
                                DateUtils.str2Timestamp(mTvDeadline.text.toString()).time,
                                mTvPermitAbandonMinute.text.toString().toInt(),
                                longitude, latitude,
                                mTvLocation.text.toString(),
                                mSwTaskRework.isChecked,
                                mSwCompensate.isChecked,
                                if (mSwCompensate.isChecked) mEtCompensateMoney.text.toString().toFloat() else 0f
                        )
                    }
                    negativeButton("否") { }
                }.show()

            }

        }
    }


    fun validation(): Boolean {
        if (taskid.isEmpty()) {
            toast("任务编号不能为空")
            return false
        }

        if (mEtPeoNum.text.toString().isEmpty() || mEtPeoNum.text.toString().toInt() <= 0) {
            toast("可接人数必须大于0")
            return false
        }

        try {
            var moneyNum = mEtMoneyNum.text.toString().toFloat()
            if (moneyNum <= 0f) {
                toast("佣金必须大于0")
                return false
            }
        } catch (e: Exception) {
            toast("佣金输入有误")
            return false
        }

        if (mTvBeginTime.text.toString() == "请选择任务开始时间") {
            toast("请选择任务开始时间")
            return false
        }

        if (mTvDeadline.text.toString() == "请选择任务截至时间") {
            toast("请选择任务截至时间")
            return false
        }

        var beginTime = DateUtils.str2Timestamp(mTvBeginTime.text.toString()).time
        var deadline = DateUtils.str2Timestamp(mTvDeadline.text.toString()).time

        if (beginTime <= DateUtils.curTime) {
            toast("开始时间不能是过去的时间")
            return false
        }

        if ((deadline - beginTime) <= (1000 * 60 * 30)) {
            toast("开始和截至时间间隔必须大于30分钟")
            return false
        }

        if (mTvPermitAbandonMinute.text.toString() == "请选择放弃时长") {
            toast("请选择放弃时长")
            return false
        }

        if (mTvLocation.text.toString() == "请选择任务位置") {
            toast("请选择任务位置")
            return false
        }

        if (mSwCompensate.isChecked) {

            try {
                var moneyNum = mEtCompensateMoney.text.toString().toFloat()
                if (moneyNum <= 0f) {
                    toast("赔偿金必须大于0")
                    return false
                }
            } catch (e: java.lang.Exception) {
                toast("赔偿金输入有误")
                return false
            }

        }

        return true
    }

    private fun calcMoney() {
        try {
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
        } catch (e: Exception) {
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
        var picker = DateTimePicker(this, DateTimePicker.HOUR_24)//24小时值
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

    private fun showMinutePicker(textView: TextView) {
        val picker = OptionPicker(this,
                arrayOf("5", "6", "7", "8", "9", "10", "15", "20", "25", "30"))
        picker.setLabel("分钟")
        picker.selectedIndex = 0//默认选中项
        picker.setOnOptionPickListener(object : OptionPicker.OnOptionPickListener() {
            override fun onOptionPicked(index: Int, item: String) {
                textView.text = "$item"
            }
        })
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
