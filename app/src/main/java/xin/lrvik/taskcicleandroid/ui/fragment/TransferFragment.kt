package xin.lrvik.taskcicleandroid.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ui.fragment.BaseFragment

class TransferFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_transfer, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }


    companion object {
        fun newInstance(): TransferFragment {
            var transferFragment = TransferFragment()
            return transferFragment
        }
    }
}
