package xin.lrvik.taskcicleandroid.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_transfer.*
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ui.fragment.BaseMvpFragment
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.data.protocol.Transfer
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.TransferPresenter
import xin.lrvik.taskcicleandroid.presenter.view.TransferView
import xin.lrvik.taskcicleandroid.ui.adapter.RvTransferAdapter
import java.util.ArrayList

class TransferFragment : BaseMvpFragment<TransferPresenter>(),TransferView {

    var curPage: Int = 0
    var pageSize: Int = 20
    lateinit var mTransferAdapter: RvTransferAdapter

    override fun injectComponent() {
        DaggerTaskCircleComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onRecordResult(data: Page<Transfer>) {
        mTransferAdapter.setNewData(data.content)

        //下拉刷新
        if (mSwipeRefresh.isRefreshing) {
            mSwipeRefresh.isRefreshing = false
            mTransferAdapter.setNewData(data.content)
            if (data.pageNum == data.totalPage - 1) {
                mTransferAdapter.loadMoreEnd()
            }
//            mRvHunterRunningAdapter.notifyDataSetChanged()
        } else {//上拉加载数据
            if (data.pageNum == data.totalPage - 1) {//到底了
                mTransferAdapter.loadMoreEnd()
            } else {//还可以上拉
                mTransferAdapter.loadMoreComplete()
            }
            mTransferAdapter.addData(data.content)
        }
        curPage = data.pageNum
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_transfer, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var linearLayoutManager = LinearLayoutManager(context)
        mRvRecord.layoutManager = linearLayoutManager
        linearLayoutManager.orientation = OrientationHelper.VERTICAL
        var list = ArrayList<Transfer>()
        mTransferAdapter = RvTransferAdapter(list)
        mRvRecord.adapter = mTransferAdapter

        mTransferAdapter.setEmptyView(R.layout.view_empty,mRvRecord)

        mSwipeRefresh.setOnRefreshListener {
            curPage = 0
            mPresenter.transferList(curPage, pageSize)
        }

        mTransferAdapter.setOnLoadMoreListener({
            mPresenter.transferList(++curPage, pageSize)
        }, mRvRecord)
        mSwipeRefresh.isRefreshing = true
        mPresenter.transferList(curPage, pageSize)
    }


    companion object {
        fun newInstance(): TransferFragment {
            var transferFragment = TransferFragment()
            return transferFragment
        }
    }
}
