package xin.lrvik.taskcicleandroid.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import xin.lrvik.taskcicleandroid.baselibrary.R


/*
    Glide工具类
 */
object GlideUtils {

    fun loadUrlImage(context: Context, url: Any, imageView: ImageView) {
        Glide.with(context).load(url).apply(RequestOptions.placeholderOf(R.drawable.def).error(R.drawable.def)).into(imageView)
    }

    fun loadUrlCircleImage(context: Context, url: Any, imageView: ImageView) {
        Glide.with(context).load(url).apply(RequestOptions.circleCropTransform().placeholder(R.drawable.def).error(R.drawable.def)).into(imageView)
    }
}
