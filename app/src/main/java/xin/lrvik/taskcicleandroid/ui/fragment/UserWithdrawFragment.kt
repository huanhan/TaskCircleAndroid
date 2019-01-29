package xin.lrvik.taskcicleandroid.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_user_withdraw.*
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ui.fragment.BaseMvpFragment
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.data.protocol.UserWithdraw
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.UserWithdrawPresenter
import xin.lrvik.taskcicleandroid.presenter.view.UserWithdrawView
import xin.lrvik.taskcicleandroid.ui.adapter.RvUserWithdrawAdapter
import java.util.ArrayList

class UserWithdrawFragment : BaseMvpFragment<UserWithdrawPresenter>(), UserWithdrawView {

    var curPage: Int = 0
    var pageSize: Int = 20
    lateinit var mRvUserWithdrawAdapter: RvUserWithdrawAdapter

    override fun injectComponent() {
        DaggerTaskCircleComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onRecordResult(data: Page<UserWithdraw>) {
        mRvUserWithdrawAdapter.setNewData(data.content)

        //下拉刷新
        if (mSwipeRefresh.isRefreshing) {
            mSwipeRefresh.isRefreshing = false
            mRvUserWithdrawAdapter.setNewData(data.content)
            if (data.pageNum == data.totalPage - 1) {
                mRvUserWithdrawAdapter.loadMoreEnd()
            }
//            mRvHunterRunningAdapter.notifyDataSetChanged()
        } else {//上拉加载数据
            if (data.pageNum == data.totalPage - 1) {//到底了
                mRvUserWithdrawAdapter.loadMoreEnd()
            } else {//还可以上拉
                mRvUserWithdrawAdapter.loadMoreComplete()
            }
            mRvUserWithdrawAdapter.addData(data.content)
        }
        curPage = data.pageNum
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_withdraw, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var linearLayoutManager = LinearLayoutManager(context)
        mRvRecord.layoutManager = linearLayoutManager
        linearLayoutManager.orientation = OrientationHelper.VERTICAL
        var list = ArrayList<UserWithdraw>()
        mRvUserWithdrawAdapter = RvUserWithdrawAdapter(list)
        mRvRecord.adapter = mRvUserWithdrawAdapter


        mSwipeRefresh.setOnRefreshListener {
            curPage = 0
            mPresenter.userWithdrawList(curPage, pageSize)
        }

        mRvUserWithdrawAdapter.setOnLoadMoreListener({
            mPresenter.userWithdrawList(++curPage, pageSize)
        }, mRvRecord)
        mSwipeRefresh.isRefreshing = true
        mPresenter.userWithdrawList(curPage, pageSize)
    }


    companion object {
        fun newInstance(): UserWithdrawFragment {
            var userWithdrawFragment = UserWithdrawFragment()
            return userWithdrawFragment
        }
    }
}
