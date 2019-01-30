package xin.lrvik.taskcicleandroid.ui.adapter

import android.content.pm.ActivityInfo
import android.support.v4.app.FragmentActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.leo.matisse.Glide4Engine
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.filter.Filter
import com.zhihu.matisse.internal.entity.CaptureStrategy
import com.zhihu.matisse.sample.GifSizeFilter
import xin.lrvik.easybanner.adapter.viewpager.BaseEasyViewPagerAdapter
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.loadUrl
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.data.protocol.TaskStep
import xin.lrvik.taskcicleandroid.ui.widget.LinesEditView


/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/6.
 *
 */
class EvpTaskStepAdapter(var isModify: Boolean,var activity: FragmentActivity) : BaseEasyViewPagerAdapter<TaskStep>(1, 1) {

    override fun createView(container: ViewGroup, data: MutableList<TaskStep>): View {
        var taskStep = data[0]
        val view = View.inflate(container.context, R.layout.item_task_step, null)
        val mIvStep = view.findViewById<ImageView>(R.id.mIvStep)
        val mEtStepTitle = view.findViewById<TextView>(R.id.mEtStepTitle)
        val mLevStepContent = view.findViewById<LinesEditView>(R.id.mLevStepContent)

        if (isModify) {
            mIvStep.isEnabled = true
            mEtStepTitle.isEnabled = true
            mLevStepContent.isEnabled = true
            mLevStepContent.id_et_input.isEnabled = true

            mEtStepTitle.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    taskStep.title = p0.toString()
                }

            })
            mLevStepContent.id_et_input.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    taskStep.context = p0.toString()
                }

            })
        } else {
            mIvStep.isEnabled = false
            mEtStepTitle.isEnabled = false
            mLevStepContent.id_et_input.isEnabled = false
        }

        mIvStep.loadUrl(taskStep.img)

        mIvStep.onClick {
            //todo 看大图
        }
        mEtStepTitle.text = taskStep.title
        mLevStepContent.contentText = taskStep.context

        return view
    }


}