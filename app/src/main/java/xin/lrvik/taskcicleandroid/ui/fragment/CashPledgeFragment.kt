package xin.lrvik.taskcicleandroid.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_transfer.*
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ui.fragment.BaseFragment
import xin.lrvik.taskcicleandroid.baselibrary.ui.fragment.BaseMvpFragment
import xin.lrvik.taskcicleandroid.data.protocol.CashPledge
import xin.lrvik.taskcicleandroid.data.protocol.Transfer
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.CashPledgePresenter
import xin.lrvik.taskcicleandroid.presenter.view.CashPledgeView
import xin.lrvik.taskcicleandroid.service.WalletService
import xin.lrvik.taskcicleandroid.ui.adapter.RvCashPledgeAdapter
import xin.lrvik.taskcicleandroid.ui.adapter.RvTransferAdapter
import java.util.ArrayList
import javax.inject.Inject

class CashPledgeFragment : BaseMvpFragment<CashPledgePresenter>(), CashPledgeView {
    lateinit var mRvCashPledgeAdapter: RvCashPledgeAdapter

    override fun injectComponent() {
        DaggerTaskCircleComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onRecordResult(data: List<CashPledge>) {
        //下拉刷新
        if (mSwipeRefresh.isRefreshing) {
            mSwipeRefresh.isRefreshing = false
            mRvCashPledgeAdapter.setNewData(data)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cash_pledge, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var linearLayoutManager = LinearLayoutManager(context)
        mRvRecord.layoutManager = linearLayoutManager
        linearLayoutManager.orientation = OrientationHelper.VERTICAL
        var list = ArrayList<CashPledge>()
        mRvCashPledgeAdapter = RvCashPledgeAdapter(list)
        mRvRecord.adapter = mRvCashPledgeAdapter


        mSwipeRefresh.setOnRefreshListener {
            mPresenter.cashPledgeList()
        }
        mSwipeRefresh.isRefreshing = true
        mPresenter.cashPledgeList()
    }


    companion object {
        fun newInstance(): CashPledgeFragment {
            var cashPledgeFragment = CashPledgeFragment()
            return cashPledgeFragment
        }
    }
}
