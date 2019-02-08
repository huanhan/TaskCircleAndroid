package xin.lrvik.taskcicleandroid.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.leo.matisse.Glide4Engine
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import kotlinx.android.synthetic.main.activity_hunter_audit.*
import org.jetbrains.anko.toast
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.loadCircleUrl
import xin.lrvik.taskcicleandroid.baselibrary.ext.loadUrl
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseMvpActivity
import xin.lrvik.taskcicleandroid.data.protocol.HunterAudit
import xin.lrvik.taskcicleandroid.data.protocol.enums.UserState
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.HunterAuditPresenter
import xin.lrvik.taskcicleandroid.presenter.view.HunterAuditView
import xin.lrvik.taskcicleandroid.util.OssUtil

class HunterAuditActivity : BaseMvpActivity<HunterAuditPresenter>(), HunterAuditView {

    companion object {
        val REQUEST_CODE_FRONT = 1
        val REQUEST_CODE_BACK = 2
    }

    var fontImg: String = ""

    var backImg: String = ""


    override fun injectComponent() {
        DaggerTaskCircleComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onAuditResult(data: HunterAudit) {
        mEtIdcard.setText(data.idCard ?: "")
        mEtPhone.setText(data.phone ?: "")
        mEtAddress.setText(data.address ?: "")
        mIvIdCardFront.loadUrl(data.idCardImgFront ?: "")
        mIvIdCardBack.loadUrl(data.idCardImgBack ?: "")
        mMenuVisible = data.state == UserState.NORMAL
        mTvState.text=data.state.state
        fontImg=data.idCardImgFront ?: ""
        backImg=data.idCardImgBack ?: ""
        supportInvalidateOptionsMenu()
    }

    override fun onResult(result: String) {
        toast(result)
        mPresenter.hunterAudit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hunter_audit)

        initView()
    }

    private fun initView() {
        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "申请成为猎刃"
        }
        mIvIdCardFront.onClick {
            //调用图片选择器
            Matisse.from(this@HunterAuditActivity)
                    .choose(MimeType.ofImage())//图片类型
                    .countable(false)//true:选中后显示数字;false:选中后显示对号
                    .maxSelectable(1)//可选的最大数
                    .capture(true)//选择照片时，是否显示拍照
                    .captureStrategy(CaptureStrategy(true, "xin.lrvik.taskcicleandroid.fileprovider"))//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                    .imageEngine(Glide4Engine())//图片加载引擎
                    .forResult(HunterAuditActivity.REQUEST_CODE_FRONT)
        }

        mIvIdCardBack.onClick {
            //调用图片选择器
            Matisse.from(this@HunterAuditActivity)
                    .choose(MimeType.ofImage())//图片类型
                    .countable(false)//true:选中后显示数字;false:选中后显示对号
                    .maxSelectable(1)//可选的最大数
                    .capture(true)//选择照片时，是否显示拍照
                    .captureStrategy(CaptureStrategy(true, "xin.lrvik.taskcicleandroid.fileprovider"))//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                    .imageEngine(Glide4Engine())//图片加载引擎
                    .forResult(HunterAuditActivity.REQUEST_CODE_BACK)
        }

        mPresenter.hunterAudit()
    }

    lateinit var mSelected: List<String>

    protected override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_FRONT && resultCode == Activity.RESULT_OK) {
            mSelected = Matisse.obtainPathResult(data!!)
            var imgUrl = mSelected[0]
            OssUtil.instance.putFile("test", imgUrl, {
                fontImg = it
                runOnUiThread {
                    mIvIdCardFront.loadUrl(it)
                }
            }, {
                runOnUiThread {
                    toast("上传失败")
                }
            })
        } else if (requestCode == REQUEST_CODE_BACK && resultCode == Activity.RESULT_OK) {
            mSelected = Matisse.obtainPathResult(data!!)
            var imgUrl = mSelected[0]
            OssUtil.instance.putFile("test", imgUrl, {
                backImg = it
                runOnUiThread {
                    mIvIdCardBack.loadUrl(it)
                }
            }, {
                runOnUiThread {
                    toast("上传失败")
                }
            })
        }
    }

    fun validation(): Boolean {

        if (mEtIdcard.text.isEmpty()) {
            toast("身份证号不能为空")
            return false
        }

        if (mEtPhone.text.isEmpty()) {
            toast("电话不能为空")
            return false
        }

        if (mEtAddress.text.isEmpty()) {
            toast("地址不能为空")
            return false
        }

        if (fontImg.isEmpty()) {
            toast("身份证正面图不能为空")
            return false
        }

        if (backImg.isEmpty()) {
            toast("身份证反面图不能为空")
            return false
        }

        return true
    }

    var mMenuVisible: Boolean = false

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.hunter_audit, menu)
        var item = menu.getItem(0)
        item.isVisible = mMenuVisible
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.save_audit -> {
                if (validation()) {
                    mPresenter.upAudit(mEtIdcard.text.toString(), mEtPhone.text.toString(), mEtAddress.text.toString(), fontImg, backImg)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
