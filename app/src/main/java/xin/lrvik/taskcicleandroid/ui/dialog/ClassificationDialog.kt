package xin.lrvik.taskcicleandroid.ui.dialog

import android.animation.Animator
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.dialog_classification.*
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.data.protocol.TaskClass
import xin.lrvik.taskcicleandroid.ui.adapter.RvClassLeftAdapter
import xin.lrvik.taskcicleandroid.ui.adapter.RvClassRightAdapter

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/6.
 *
 */
class ClassificationDialog : DialogFragment() {

    var listener: OnClassificationClickListener? = null
    var classList: ArrayList<TaskClass> = ArrayList()
    var classListRight: ArrayList<TaskClass> = ArrayList()
    var mRvClassRightAapter: RvClassRightAdapter? = null
    var mRvClassLeftAapter: RvClassLeftAdapter? = null

    var mPPos: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_classification, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var linearLayoutManagerLeft = LinearLayoutManager(context)
        var linearLayoutManagerRight = LinearLayoutManager(context)
        linearLayoutManagerLeft.orientation = OrientationHelper.VERTICAL
        linearLayoutManagerRight.orientation = OrientationHelper.VERTICAL

        mRvClassLeft.layoutManager = linearLayoutManagerLeft
        mRvClassRight.layoutManager = linearLayoutManagerRight

        if (mRvClassRightAapter == null) {
            mRvClassRightAapter = RvClassRightAdapter(classListRight)
        }

        if (mRvClassLeftAapter == null) {
            mRvClassLeftAapter = RvClassLeftAdapter(classList)
        }

        mRvClassRight.adapter = mRvClassRightAapter
        mRvClassLeft.adapter = mRvClassLeftAapter

        mRvClassRightAapter!!.setOnItemClickListener { adapter, view, position ->
            var arrayList = adapter.data as ArrayList<TaskClass>
            clearSelectSingleData(arrayList)
            arrayList[position].isSelect = true
            mRvClassRightAapter!!.notifyDataSetChanged()
            listener?.onClassClick(mPPos, position, arrayList[position])
        }

        mRvClassLeftAapter!!.setOnItemClickListener { adapter, view, position ->
            var arrayList = adapter.data as ArrayList<TaskClass>
            clearSelectData(arrayList)
            arrayList[position].isSelect = true
            mRvClassRightAapter!!.setNewData(arrayList[position].taskClassifies)
            mRvClassLeftAapter!!.notifyDataSetChanged()
            mPPos = position

        }
        mLlClass.scaleY = 0f
        mLlClass.pivotX = 0f
        mLlClass.animate().scaleY(1f)
        mIvBt.animate().rotation(180f)

        mIvBt.onClick {
            dismissDialog()
        }

        mLlClassBg.onClick {
            dismissDialog()
        }

    }

    fun dismissDialog() {
        mIvBt.animate().rotation(360f)
        mLlClass.animate().scaleY(0f).setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                dialog.dismiss()
            }
        })
    }

    /**
     * 设置选中的位置
     */
    fun setCurrData(parentItem: Int = 0, childItem: Int = -1) {
        clearSelectData(classList)
        //父分类是否被选中
        if (parentItem != -1) {
            classList[parentItem].isSelect = true
            if (childItem != -1) {//子分类是否被选中
                classList[parentItem].taskClassifies!![childItem].isSelect = true
            }
            mRvClassRightAapter!!.setNewData(classList[parentItem].taskClassifies!!)
        } else {
            classList[0].isSelect = true
            mRvClassRightAapter!!.setNewData(classList[0].taskClassifies!!)
        }
        mRvClassLeftAapter!!.setNewData(classList)
    }


    /**
     * 清空一层分类选中状态
     */
    private fun clearSelectSingleData(data: ArrayList<TaskClass>) {
        //获取到的数据做清空选中处理
        data.forEach {
            it.isSelect = false
        }
    }

    /**
     * 清空2层分类选中状态
     */
    fun clearSelectData(data: ArrayList<TaskClass>) {
        //获取到的数据做清空选中处理
        data.forEach {
            it.taskClassifies!!.forEach { item ->
                item.isSelect = false
            }
            it.isSelect = false
        }
    }

    /**
     * 设置数据
     */
    fun setData(data: List<TaskClass>) {
        classList.clear()
        classList.addAll(data)
        mRvClassLeftAapter!!.notifyDataSetChanged()
    }

    fun getData(): List<TaskClass> {
        return classList
    }


    override fun onResume() {
        super.onResume()

        val window = dialog.window
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        var attributes = window.attributes
        //attributes.gravity = Gravity.TOP//对齐方式
        window.attributes = attributes

        //attributes.dimAmount=0f
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    fun showDialog(supportFragmentManager: FragmentManager) {
        val bundle = Bundle()
        this.arguments = bundle
        this.show(supportFragmentManager, "classDialog")
    }

    interface OnClassificationClickListener {
        fun onClassClick(pPos: Int, cPos: Int, taskClass: TaskClass)
    }

    companion object {
        private val PARENTITEM = "PARENTITEM"
        private val CHILDITEM = "CHILDITEM"

    }

}