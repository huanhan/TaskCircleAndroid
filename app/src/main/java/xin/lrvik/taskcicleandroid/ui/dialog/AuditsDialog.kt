package xin.lrvik.taskcicleandroid.ui.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.dialog_audits.*
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.data.protocol.Audit
import xin.lrvik.taskcicleandroid.ui.adapter.RvAuditAdapter


/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/6.
 */
class AuditsDialog : DialogFragment() {

    lateinit var data: ArrayList<Audit>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        val bundle = arguments
        data = bundle!!.getParcelableArrayList<Audit>(AUDITS)
        return inflater.inflate(R.layout.dialog_audits, container)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = OrientationHelper.VERTICAL

        mRvAudits.layoutManager=linearLayoutManager

        mRvAudits.adapter = RvAuditAdapter(data)
    }

    companion object {
        private val AUDITS = "AUDITS"

        fun showDialog(fm: FragmentManager, data: ArrayList<Audit>): AuditsDialog {
            val dialog = AuditsDialog()
            val bundle = Bundle()
            bundle.putParcelableArrayList(AUDITS, data)
            dialog.arguments = bundle
            dialog.show(fm, "AuditDialog")
            return dialog
        }
    }

}
