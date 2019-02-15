package xin.lrvik.taskcicleandroid.ui.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import cn.qqtheme.framework.picker.DatePicker
import cn.qqtheme.framework.picker.OptionPicker
import com.leo.matisse.Glide4Engine
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import kotlinx.android.synthetic.main.activity_modify_user_info.*
import org.jetbrains.anko.toast
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.loadCircleUrl
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseMvpActivity
import xin.lrvik.taskcicleandroid.baselibrary.utils.DateUtils
import xin.lrvik.taskcicleandroid.baselibrary.utils.DateUtils.FORMAT_SHORT
import xin.lrvik.taskcicleandroid.data.protocol.User
import xin.lrvik.taskcicleandroid.data.protocol.enums.UserGender
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.ModifyUserPresenter
import xin.lrvik.taskcicleandroid.presenter.view.ModifyUserView
import xin.lrvik.taskcicleandroid.util.OssUtil
import java.util.*

class ModifyUserInfoActivity : BaseMvpActivity<ModifyUserPresenter>(), ModifyUserView {

    companion object {
        val REQUEST_CODE_CHOOSE = 1
        val REQUEST_CODE_CROP = 2
    }

    override fun injectComponent() {
        DaggerTaskCircleComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onUserResult(data: User) {

        mIvIcon.loadCircleUrl(data.headImg ?: R.mipmap.icon_default_user)

        mEtName.text.append(data.name)

        mTvGender.text = data.gender!!.gender

        mLevContext.contentText = (data.intro ?: "")

        mEtIdcard.text.append(data.idCard ?: "")
        mEtPhone.text.append(data.phone ?: "")
        mEtAddress.text.append(data.address ?: "")
        mEtSchool.text.append(data.school ?: "")
        mEtJob.text.append(data.major ?: "")
        mEtInterest.text.append(data.interest ?: "")
        mTvHeight.text = "${(data.height ?: 0)}"
        mTvWeight.text = "${(data.weight ?: 0)}"
        data.birthday?.let {
            mTvBirthday.text = DateUtils.convertTimeToString(it, format = FORMAT_SHORT)
        }

        mTvBirthday.onClick {
            showBirthdayPicker(mTvBirthday)
        }

        mTvGender.onClick {
            showGenderPicker(mTvGender)
        }

        mTvHeight.onClick {
            showHeightPicker(mTvHeight)
        }

        mTvWeight.onClick {
            showWeightPicker(mTvWeight)
        }

        mIvIcon.onClick {
            //调用图片选择器
            Matisse.from(this@ModifyUserInfoActivity)
                    .choose(MimeType.ofImage())//图片类型
                    .countable(false)//true:选中后显示数字;false:选中后显示对号
                    .maxSelectable(1)//可选的最大数
                    .capture(true)//选择照片时，是否显示拍照
                    .captureStrategy(CaptureStrategy(true, "xin.lrvik.taskcicleandroid.fileprovider"))//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                    .imageEngine(Glide4Engine())//图片加载引擎
                    .forResult(PostTaskActivity.REQUEST_CODE_CHOOSE)
        }

    }

    override fun onResult(result: String) {
        toast(result)
        finish()
    }

    override fun onImgResult(result: String) {
        toast(result)

    }

    private fun updateInfo() {
        if (validation()) {
            mPresenter.update(
                    mEtName.text.toString(),
                    if (mTvGender.text.toString() == "男") UserGender.MAN else UserGender.WOMAN,
                    mEtIdcard.text.toString(),
                    mEtAddress.text.toString(),
                    mEtSchool.text.toString(),
                    mEtJob.text.toString(),
                    mEtInterest.text.toString(),
                    mLevContext.contentText.toString(),
                    if (!mTvHeight.text.toString().isNullOrEmpty()) mTvHeight.text.toString().toInt() else null,
                    if (!mTvWeight.text.toString().isNullOrEmpty()) mTvWeight.text.toString().toInt() else null,
                    if (!mTvBirthday.text.toString().isNullOrEmpty()) DateUtils.stringToLong(mTvBirthday.text.toString(), formatType = FORMAT_SHORT) else null,
                    mEtPhone.text.toString())
        }
    }


    fun validation(): Boolean {

        if (mTvGender.text.isEmpty()) {
            toast("性别未选择")
            return false
        }

        if (mEtName.text.isEmpty()) {
            toast("昵称不能为空")
            return false
        }

        if (!mEtPhone.text.toString().isNullOrEmpty()) {
            if (mEtPhone.text.toString().length != 11) {
                toast("电话号码必须在11位")
                return false
            }
        }

        if (mEtIdcard.text.isEmpty()&&mEtIdcard.text.length==18) {
            toast("请输入18位身份证号码")
            return false
        }

        if (mEtPhone.text.isEmpty()&&mEtPhone.text.length==11) {
            toast("请输入11位电话号码")
            return false
        }

        return true
    }

    lateinit var mSelected: List<Uri>

    val IMAGE_FILE_LOCATION = "file:///" + Environment.getExternalStorageDirectory().getPath() + "/temp.jpg"
    var imageUri: Uri = Uri.parse(IMAGE_FILE_LOCATION)

    protected override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == Activity.RESULT_OK) {
            mSelected = Matisse.obtainResult(data!!)
            var intent = Intent("com.android.camera.action.CROP")
            intent.setDataAndType(mSelected[0], "image/*")
            // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
            intent.putExtra("crop", "true")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(intent, PostTaskActivity.REQUEST_CODE_CROP)
        } else if (requestCode == REQUEST_CODE_CROP && resultCode == Activity.RESULT_OK) {
            var mCrop = imageUri.path!!
            //taskStep.img = mCrop
            OssUtil.instance.putFile("test", mCrop, {
                runOnUiThread {
                    mPresenter.updateIcon(it)
                    mIvIcon.loadCircleUrl(it)
                }
            }, {
                runOnUiThread {
                    toast("上传失败")
                }
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_user_info)
        initView()
    }

    private fun initView() {
        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "修改我的信息"
        }
        mPresenter.detail()
    }


    private fun showGenderPicker(textView: TextView) {
        val picker = OptionPicker(this,
                arrayOf("男", "女"))
        picker.setLabel("性")
        picker.selectedIndex = 0//默认选中项
        picker.setOnOptionPickListener(object : OptionPicker.OnOptionPickListener() {
            override fun onOptionPicked(index: Int, item: String) {
                textView.text = "$item"
            }
        })
        picker.show()
    }

    private fun showBirthdayPicker(textView: TextView) {
        val calendar = Calendar.getInstance()
        //获取当前时间
        var picker = DatePicker(this);//24小时值
        picker.setRangeStart(1900, 1, 1)//日期起点
        picker.setSelectedItem(1995, 1, 1)
        picker.setRangeEnd(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE))//日期终点
        picker.setOnDatePickListener(DatePicker.OnYearMonthDayPickListener { year, month, day ->
            textView.text = "$year-$month-$day"
        })
        picker.show()
    }


    private fun showHeightPicker(textView: TextView) {

        var list = (10..300).map {
            "$it"
        }
        val picker = OptionPicker(this, list)
        picker.setLabel("cm")
        picker.selectedIndex = 160//默认选中项
        picker.setOnOptionPickListener(object : OptionPicker.OnOptionPickListener() {
            override fun onOptionPicked(index: Int, item: String) {
                textView.text = "$item"
            }
        })
        picker.show()
    }

    private fun showWeightPicker(textView: TextView) {

        intArrayOf(5)
        var list = (5..200).map {
            "$it"
        }
        val picker = OptionPicker(this, list)
        picker.setLabel("kg")
        picker.selectedIndex = 70//默认选中项
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
            R.id.save_user_info -> {
                updateInfo()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.userinfo_modify, menu)
        return true
    }
}
