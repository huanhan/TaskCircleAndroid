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
import xin.lrvik.taskcicleandroid.data.protocol.CommentUser
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.UserReEvaPresenter
import xin.lrvik.taskcicleandroid.presenter.view.UserReEvaView
import xin.lrvik.taskcicleandroid.ui.adapter.RvUserReEvaAdapter
import java.util.ArrayList

class UserReEvaFragment : BaseMvpFragment<UserReEvaPresenter>(), UserReEvaView {

    override fun onListResult(data: Page<CommentUser>) {
        mRvUserReEvaAdapter.setNewData(data.content)

        //下拉刷新
        if (mSwipeRefresh.isRefreshing) {
            mSwipeRefresh.isRefreshing = false
            mRvUserReEvaAdapter.setNewData(data.content)
            if (data.pageNum == data.totalPage - 1) {
                mRvUserReEvaAdapter.loadMoreEnd()
            }
//            mRvHunterRunningAdapter.notifyDataSetChanged()
        } else {//上拉加载数据
            if (data.pageNum == data.totalPage - 1) {//到底了
                mRvUserReEvaAdapter.loadMoreEnd()
            } else {//还可以上拉
                mRvUserReEvaAdapter.loadMoreComplete()
            }
            mRvUserReEvaAdapter.addData(data.content)
        }
        curPage = data.pageNum

    }

    var curPage: Int = 0
    var pageSize: Int = 20
    lateinit var mRvUserReEvaAdapter: RvUserReEvaAdapter

    override fun injectComponent() {
        DaggerTaskCircleComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_eva, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var linearLayoutManager = LinearLayoutManager(context)
        mRvRecord.layoutManager = linearLayoutManager
        linearLayoutManager.orientation = OrientationHelper.VERTICAL
        var list = ArrayList<CommentUser>()
        mRvUserReEvaAdapter = RvUserReEvaAdapter(list)
        mRvRecord.adapter = mRvUserReEvaAdapter

        mRvUserReEvaAdapter.setEmptyView(R.layout.view_empty,mRvRecord)

        mSwipeRefresh.setOnRefreshListener {
            curPage = 0
            mPresenter.evaUserByUser(curPage, pageSize)
        }

        mRvUserReEvaAdapter.setOnLoadMoreListener({
            mPresenter.evaUserByUser(++curPage, pageSize)
        }, mRvRecord)
        mSwipeRefresh.isRefreshing = true
        mPresenter.evaUserByUser(curPage, pageSize)
    }


    companion object {
        fun newInstance(): UserReEvaFragment {
            var instance = UserReEvaFragment()
            return instance
        }
    }
}
