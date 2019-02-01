package xin.lrvik.taskcicleandroid.ui.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.loadCircleUrl



/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/2/1.
 */
class TitleTextWindow(private val mContext: Context) : View.OnTouchListener {
    private var wm: WindowManager? = null
    private var linearLayout: LinearLayout? = null
    private var downY: Int = 0

    private val mHander = object : android.os.Handler(Looper.myLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            animDismiss()
        }
    }

    /**
     * 动画，从顶部弹出
     */
    private fun animShow() {
        //使用动画从顶部弹出
        val animator = ObjectAnimator.ofFloat(linearLayout!!, "translationY", -linearLayout!!.measuredHeight.toFloat(), 0f)
        animator.setDuration(600)
        animator.start()
    }

    /**
     * 动画，从顶部收回
     */
    private fun animDismiss() {
        if (linearLayout == null || linearLayout!!.parent == null) {
            return
        }
        val animator = ObjectAnimator.ofFloat(linearLayout!!, "translationY", linearLayout!!.translationY.toFloat(), -linearLayout!!.measuredHeight.toFloat())
        animator.setDuration(600)
        animator.start()
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationCancel(animation: Animator) {
                super.onAnimationCancel(animation)
            }

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                //移除HeaderToast  (一定要在动画结束的时候移除,不然下次进来的时候由于wm里边已经有控件了，所以会导致卡死)
                if (null != linearLayout && null != linearLayout!!.parent) {
                    wm!!.removeView(linearLayout)
                }
            }

            override fun onAnimationRepeat(animation: Animator) {
                super.onAnimationRepeat(animation)
            }

            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
            }

            override fun onAnimationPause(animation: Animator) {
                super.onAnimationPause(animation)
            }

            override fun onAnimationResume(animation: Animator) {
                super.onAnimationResume(animation)
            }
        })
    }

    /**
     * 向外部暴露显示的方法
     */
    fun show(iconUrl: String, title: String, content: String) {
        createTitleView(iconUrl, title, content)
        animShow()
        //3S后自动关闭
        mHander.sendEmptyMessageDelayed(20, 3000)
    }

    /**
     * 向外部暴露关闭的方法
     */
    fun dismiss() {
        animDismiss()
    }



    /**
     * 视图创建方法
     */
    private fun createTitleView(iconUrl: String, title: String, content: String) {
        //准备Window要添加的View
        linearLayout = LinearLayout(mContext)
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        linearLayout!!.layoutParams = layoutParams
        val titleView = View.inflate(mContext, R.layout.header_toast, null)//这里是你弹窗的UI
        val mIvIcon = titleView.findViewById<ImageView>(R.id.mIvIcon)
        val mTvtitle = titleView.findViewById<TextView>(R.id.mTvtitle)
        val mTvContent = titleView.findViewById<TextView>(R.id.mTvContent)
        mIvIcon.loadCircleUrl(iconUrl)
        mTvtitle.text = title
        mTvContent.text = content

        // 为titleView设置Touch事件
        linearLayout!!.setOnTouchListener(this)
        linearLayout!!.addView(titleView)
        // 定义WindowManager 并且将View添加到WindowManagar中去
        wm = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val wm_params = WindowManager.LayoutParams()

        wm_params.flags = (WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                or WindowManager.LayoutParams.FLAG_FULLSCREEN
                or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        //这里需要注意，因为不同系统版本策略不一，所以需要根据版本判断设置type，否则会引起崩溃。
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {//大于android SDK 7.1.1
            wm_params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            wm_params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        }
        wm_params.gravity = Gravity.TOP
        wm_params.x = 0
        wm_params.y = 0
        wm_params.format = -3  // 会影响Toast中的布局消失的时候父控件和子控件消失的时机不一致，比如设置为-1之后就会不同步
        wm_params.alpha = 1f
        linearLayout!!.measure(0, 0)
        wm_params.height = linearLayout!!.measuredHeight
        wm!!.addView(linearLayout, wm_params)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> downY = event.rawY.toInt()
            MotionEvent.ACTION_MOVE -> {
                val moveY = event.rawY.toInt()
                if (moveY - downY < 0) {//如果是向上滑动
                    linearLayout!!.translationY = (moveY - downY).toFloat()
                }
            }
            MotionEvent.ACTION_UP ->
                //达到一定比例后，松开手指将关闭弹窗
                if (Math.abs(linearLayout!!.translationY) > linearLayout!!.measuredHeight / 1.5) {
                    Log.e("TAG", "回弹")
                    animDismiss()
                } else {
                    linearLayout!!.translationY = 0f
                }
            else -> {
            }
        }
        return true
    }
}
