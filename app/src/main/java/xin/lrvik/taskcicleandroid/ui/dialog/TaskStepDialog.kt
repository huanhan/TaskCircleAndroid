package xin.lrvik.taskcicleandroid.ui.dialog

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.dialog_task_step.*
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.data.protocol.TaskStep
import xin.lrvik.taskcicleandroid.ui.adapter.EvpTaskStepAdapter


/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/6.
 */
class TaskStepDialog : DialogFragment() {

    lateinit var data: ArrayList<TaskStep>
    var isModify: Boolean = false
    var currentItem: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        val bundle = arguments
        data = bundle!!.getParcelableArrayList<TaskStep>(STEPDATA)
        isModify = bundle!!.getBoolean(ISMODIFY)
        currentItem = bundle!!.getInt(CURRENTITEM)
        return inflater.inflate(R.layout.dialog_task_step, container)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mEvpTaskStep.setIndicator(mEdiTaskStep)
                .setAdapter(EvpTaskStepAdapter(isModify, activity!!))
                .data = data

        mEvpTaskStep.currentItem = currentItem

        mIvClose.onClick {
            dialog.dismiss()
        }

    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        this.onCloseListener?.let {
            onCloseListener.onClose()
        }
    }

    interface OnCloseListener {
        fun onClose()
    }

    private lateinit var onCloseListener: OnCloseListener

    fun setOnCloseListener(onCloseListener: OnCloseListener) {
        this.onCloseListener = onCloseListener
    }

    override fun onResume() {
        super.onResume()
        val window = dialog.window
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    companion object {
        private val STEPDATA = "STEPDATA"
        private val ISMODIFY = "ISMODIFY"
        private val CURRENTITEM = "CURRENTITEM"

        fun showDialog(fm: FragmentManager, data: ArrayList<TaskStep>, isModify: Boolean, currentItem: Int): TaskStepDialog {
            val dialog = TaskStepDialog()
            val bundle = Bundle()
            bundle.putParcelableArrayList(STEPDATA, data)
            bundle.putBoolean(ISMODIFY, isModify)
            bundle.putInt("CURRENTITEM", currentItem)
            dialog.arguments = bundle
            dialog.show(fm, "TaskStepDialog")
            return dialog
        }
    }

}
