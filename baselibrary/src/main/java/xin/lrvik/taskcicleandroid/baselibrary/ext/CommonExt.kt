package xin.lrvik.taskcicleandroid.baselibrary.ext

import android.view.View
import android.widget.ImageView
import com.trello.rxlifecycle2.LifecycleProvider
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import xin.lrvik.taskcicleandroid.baselibrary.presenter.view.BaseView
import xin.lrvik.taskcicleandroid.baselibrary.rx.BaseException
import xin.lrvik.taskcicleandroid.utils.GlideUtils
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


/**
 * isShowLoading是否显示Loading
 */
fun <T> Observable<T>.execute(lifecycleProvider: LifecycleProvider<*>,
                              mView: BaseView, isShowLoading: Boolean = true,
                              function: (data: T) -> Unit) {
    this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(lifecycleProvider.bindToLifecycle<T>())
            .subscribe(object : Observer<T> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onComplete() {
                    if (isShowLoading) {
                        mView.hideLoading()
                    }
                }

                override fun onNext(t: T) {
                    function(t)
                    if (isShowLoading) {
                        mView.hideLoading()
                    }
                }

                override fun onError(e: Throwable) {
                    if (isShowLoading) {
                        mView.hideLoading()
                    }
                    if (e is BaseException) {
                        mView.onError(e.msg)
                    }
                }
            })
}


fun View.onClick(method: (View) -> Unit) {
    this.setOnClickListener {
        method(it)
    }
}

fun View.onClick(listener: View.OnClickListener): View {
    setOnClickListener(listener)
    return this
}


/*
    ImageView加载网络图片
 */
fun ImageView.loadUrl(url: Any?) {
    GlideUtils.loadUrlImage(context, url, this)
}

fun ImageView.loadCircleUrl(url: Any) {
    GlideUtils.loadUrlCircleImage(context, url, this)
}

fun md5Encode(text: String): String {
    try {
        //获取md5加密对象
        val instance: MessageDigest = MessageDigest.getInstance("MD5")
        //对字符串加密，返回字节数组
        val digest: ByteArray = instance.digest(text.toByteArray())
        var sb: StringBuffer = StringBuffer()
        for (b in digest) {
            //获取低八位有效值
            var i: Int = b.toInt() and 0xff
            //将整数转化为16进制
            var hexString = Integer.toHexString(i)
            if (hexString.length < 2) {
                //如果是一位的话，补0
                hexString = "0" + hexString
            }
            sb.append(hexString)
        }
        return sb.toString()

    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }

    return ""
}

//判断界面是否根据权限显示
fun <T> isShow(view: View, state: T, list: List<T>) {
    view.visibility = if (list.contains(state)) View.VISIBLE else View.GONE
}