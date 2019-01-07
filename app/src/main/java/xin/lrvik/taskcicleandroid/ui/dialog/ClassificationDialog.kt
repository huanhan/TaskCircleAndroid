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

    private var currentParentItem: Int = -1
    private var currentItem: Int = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val bundle = arguments
        currentParentItem = bundle!!.getInt(CURRENTPARENTITEM)
        currentItem = bundle!!.getInt(CURRENTITEM)

        return inflater.inflate(R.layout.dialog_classification, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var linearLayoutManagerLeft = LinearLayoutManager(context)
        var linearLayoutManagerRight = LinearLayoutManager(context)
        linearLayoutManagerLeft.orientation = OrientationHelper.VERTICAL
        linearLayoutManagerRight.orientation = OrientationHelper.VERTICAL

        mRvClassLeft.layoutManager = linearLayoutManagerLeft
        mRvClassRight.layoutManager = linearLayoutManagerRight

        var data: ArrayList<TaskClass> = getData()

        //获取到的数据做清空选中处理
        clearSelectData(data)

        var mRvClassRightAapter = RvClassRightAdapter(data)
        var mRvClassLeftAapter = RvClassLeftAdapter(data)
        mRvClassRight.adapter = mRvClassRightAapter
        mRvClassLeft.adapter = mRvClassLeftAapter
        //父分类是否被选中
        if (currentParentItem != -1) {
            data[currentParentItem].isSelect = true
            if (currentItem != -1) {//子分类是否被选中
                data[currentParentItem].taskClassifies!![currentItem].isSelect = true
            }
            mRvClassRightAapter.setNewData(data[currentParentItem].taskClassifies!!)
        } else {
            data[0].isSelect = true
            mRvClassRightAapter.setNewData(data[0].taskClassifies!!)
        }
        mRvClassLeftAapter.setNewData(data)

        mRvClassRightAapter.setOnItemClickListener { adapter, view, position ->
            var arrayList = adapter.data as ArrayList<TaskClass>
            clearSelectSingleData(arrayList)
            arrayList[position].isSelect = true
            mRvClassRightAapter.notifyDataSetChanged()
        }

        mRvClassLeftAapter.setOnItemClickListener { adapter, view, position ->
            var arrayList = adapter.data as ArrayList<TaskClass>
            clearSelectData(arrayList)
            arrayList[position].isSelect = true
            mRvClassRightAapter.setNewData(arrayList[position].taskClassifies)
            mRvClassLeftAapter.notifyDataSetChanged()
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
     * 清空一层分类选中状态
     */
    fun clearSelectSingleData(data: ArrayList<TaskClass>) {
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


    fun getData(): ArrayList<TaskClass> {
        //todo 网络请求
        var parentItems = ArrayList<TaskClass>()
        for (i in 0L..10L) {
            var items = ArrayList<TaskClass>()
            for (j in i + 20..i + 30) {
                items.add(TaskClass(j, "分类$j", "", null, false))
            }
            parentItems.add(TaskClass(i, "分类$i", "", items, false))
        }

        return parentItems
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


    companion object {
        private val CURRENTPARENTITEM = "currentParentItem"
        private val CURRENTITEM = "currentItem"

        fun showDialog(fm: FragmentManager, currentParentItem: Int, currentItem: Int): ClassificationDialog {
            val dialog = ClassificationDialog()
            val bundle = Bundle()
            bundle.putInt(CURRENTPARENTITEM, currentParentItem)
            bundle.putInt(CURRENTITEM, currentItem)
            dialog.arguments = bundle
            dialog.show(fm, "ClassificationDialog")
            return dialog
        }
    }

}