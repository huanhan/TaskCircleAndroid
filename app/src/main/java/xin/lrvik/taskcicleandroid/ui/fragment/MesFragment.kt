package xin.lrvik.taskcicleandroid.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_mes.*
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ui.fragment.BaseMvpFragment
import xin.lrvik.taskcicleandroid.data.protocol.Message
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.MesPresenter
import xin.lrvik.taskcicleandroid.presenter.view.MesView
import xin.lrvik.taskcicleandroid.ui.adapter.RvMesAdapter
import java.util.*

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/3.
 *
 */
class MesFragment : BaseMvpFragment<MesPresenter>(), MesView {
    override fun injectComponent() {
        DaggerTaskCircleComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onMesResult(data: Page<Message>) {
        mAdapter.setNewData(data.content)

        //下拉刷新
        if (mSwipeRefresh.isRefreshing) {
            mSwipeRefresh.isRefreshing = false
            mAdapter.setNewData(data.content)
            if (data.pageNum == data.totalPage - 1) {
                mAdapter.loadMoreEnd()
            }
//            mRvHunterRunningAdapter.notifyDataSetChanged()
        } else {//上拉加载数据
            if (data.pageNum == data.totalPage - 1) {//到底了
                mAdapter.loadMoreEnd()
            } else {//还可以上拉
                mAdapter.loadMoreComplete()
            }
            mAdapter.addData(data.content)
        }
        curPage = data.pageNum
    }

    var curPage: Int = 0
    var pageSize: Int = 20

    lateinit var mAdapter: RvMesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mes, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var linearLayoutManager = LinearLayoutManager(context)
        mRvMes.layoutManager = linearLayoutManager
        linearLayoutManager.orientation = OrientationHelper.VERTICAL

        var list = ArrayList<Message>()
        mAdapter = RvMesAdapter(list)
        mRvMes.adapter = mAdapter

        mAdapter.setEmptyView(R.layout.view_empty,mRvMes)
        mAdapter.setOnItemClickListener { adapter, view, position ->
            //todo 公告点击事件
        }

        mSwipeRefresh.setOnRefreshListener {
            curPage = 0
            mPresenter.message(curPage, pageSize)
        }

        mAdapter.setOnLoadMoreListener({
            mPresenter.message(++curPage, pageSize)
        }, mRvMes)
        mSwipeRefresh.isRefreshing = true
        mPresenter.message(curPage, pageSize)
    }
}