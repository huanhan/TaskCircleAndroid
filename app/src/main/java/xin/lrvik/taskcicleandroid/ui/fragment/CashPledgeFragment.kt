package xin.lrvik.taskcicleandroid.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ui.fragment.BaseFragment
import xin.lrvik.taskcicleandroid.service.WalletService
import javax.inject.Inject

class CashPledgeFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cash_pledge, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }


    companion object {
        fun newInstance(): CashPledgeFragment {
            var cashPledgeFragment = CashPledgeFragment()
            return cashPledgeFragment
        }
    }
}
