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
import xin.lrvik.taskcicleandroid.data.protocol.CommentHunter
import xin.lrvik.taskcicleandroid.data.protocol.CommentUser
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.HunterReEvaPresenter
import xin.lrvik.taskcicleandroid.presenter.view.HunterReEvaView
import xin.lrvik.taskcicleandroid.ui.adapter.RvUserSdEvaAdapter
import java.util.ArrayList

class HunterReEvaFragment : BaseMvpFragment<HunterReEvaPresenter>(), HunterReEvaView {
    override fun onListResult(data: Page<CommentHunter>) {
        mRvUserSdEvaAdapter.setNewData(data.content)

        //下拉刷新
        if (mSwipeRefresh.isRefreshing) {
            mSwipeRefresh.isRefreshing = false
            mRvUserSdEvaAdapter.setNewData(data.content)
            if (data.pageNum == data.totalPage - 1) {
                mRvUserSdEvaAdapter.loadMoreEnd()
            }
//            mRvHunterRunningAdapter.notifyDataSetChanged()
        } else {//上拉加载数据
            if (data.pageNum == data.totalPage - 1) {//到底了
                mRvUserSdEvaAdapter.loadMoreEnd()
            } else {//还可以上拉
                mRvUserSdEvaAdapter.loadMoreComplete()
            }
            mRvUserSdEvaAdapter.addData(data.content)
        }
        curPage = data.pageNum
    }


    var curPage: Int = 0
    var pageSize: Int = 20
    lateinit var mRvUserSdEvaAdapter: RvUserSdEvaAdapter

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
        var list = ArrayList<CommentHunter>()
        mRvUserSdEvaAdapter = RvUserSdEvaAdapter(list)
        mRvRecord.adapter = mRvUserSdEvaAdapter


        mSwipeRefresh.setOnRefreshListener {
            curPage = 0
            mPresenter.evaHunterByHunter(curPage, pageSize)
        }

        mRvUserSdEvaAdapter.setOnLoadMoreListener({
            mPresenter.evaHunterByHunter(++curPage, pageSize)
        }, mRvRecord)
        mSwipeRefresh.isRefreshing = true
        mPresenter.evaHunterByHunter(curPage, pageSize)
    }


    companion object {
        fun newInstance(): HunterReEvaFragment {
            var instance = HunterReEvaFragment()
            return instance
        }
    }
}
