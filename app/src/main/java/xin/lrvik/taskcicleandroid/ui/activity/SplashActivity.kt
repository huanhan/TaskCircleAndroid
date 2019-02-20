package xin.lrvik.taskcicleandroid.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.utils.AppPrefsUtils
import xin.lrvik.taskcicleandroid.baselibrary.common.UserInfo
import xin.lrvik.taskcicleandroid.baselibrary.data.protocol.TokenResult
import xin.lrvik.taskcicleandroid.baselibrary.utils.DateUtils
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mTvInit.text="权限效验中"
        //授权
        RxPermissions(this@SplashActivity)
                .request(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.CAMERA)
                .subscribe {
                    if (!it) {
                        mTvInit.text="权限被拒绝"
                        toast("拒绝了应用需要的权限，请在应用权限内重新授予")
                        finish()
                    } else {
                        mTvInit.text="权限效验成功"
                        mTvInit.text="本地登录信息效验中"
                        //验证token是否存在
                        var token = AppPrefsUtils.getString("token")
                        if (token.isNullOrEmpty()) {
                            Observable.timer(1000, TimeUnit.MILLISECONDS)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe {
                                        finish()
                                        mTvInit.text="开始登录"
                                        //为空的话调用登录
                                        startActivity<LoginActivity>()
                                    }
                        } else {

                            mTvInit.text="已获取登录信息，验证身份信息中"
                            //获取本地token,给请求加上header
                            var tokenResult = Gson().fromJson(token, TokenResult::class.java)
                            UserInfo.access_token = tokenResult.access_token
                            UserInfo.refresh_token = tokenResult.refresh_token
                            UserInfo.userId = tokenResult.userId.toLong()
                            //根据token时间决定是否重新登录

                            Log.d("test", "当前时间${DateUtils.curTime} 保存进SP的时间：${tokenResult.expires_out}" )
                            if (DateUtils.curTime > tokenResult.expires_out) {
                                toast("登录信息失效，请重新登录")
                                mTvInit.text="登录信息失效，请重新登录"
                                //重新登录
                                Observable.timer(1000, TimeUnit.MILLISECONDS)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe {
                                            finish()
                                            startActivity<LoginActivity>()
                                        }
                            }else{

                                mTvInit.text="验证成功，进入中。。"
                                //1秒进入主界面
                                Observable.timer(1000, TimeUnit.MILLISECONDS)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe {
                                            finish()
                                            startActivity<MainActivity>()
                                        }
                            }

                        }

                    }

                }


    }
}
