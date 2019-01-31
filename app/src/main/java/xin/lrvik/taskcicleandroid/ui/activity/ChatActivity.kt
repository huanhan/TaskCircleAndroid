package xin.lrvik.taskcicleandroid.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_chat.*
import org.jetbrains.anko.toast
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseMvpActivity
import xin.lrvik.taskcicleandroid.common.UserInfo
import java.util.*
import xin.lrvik.taskcicleandroid.data.protocol.Chat
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.ChatPresenter
import xin.lrvik.taskcicleandroid.presenter.view.ChatView
import xin.lrvik.taskcicleandroid.ui.adapter.RvChatAdapter


class ChatActivity : BaseMvpActivity<ChatPresenter>(), ChatView {
    override fun injectComponent() {
        DaggerTaskCircleComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onChatListResult(data: Page<Chat>) {
        mRvChatAdapter.setNewData(data.content.reversed())
        mRvChat.scrollToPosition(mRvChatAdapter.itemCount - 1)
    }

    override fun onResult(result: String) {
        toast(result)
    }

    //任务id,猎刃id，用户id
    var hunterid: Long = 0
    lateinit var taskid: String
    var userid: Long = 0
    lateinit var mRvChatAdapter: RvChatAdapter

    companion object {
        val HUNTERID = "HUNTERID"
        val TASKID = "TASKID"
        val USERID = "USERID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        initView()
    }

    private fun initView() {
        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "聊天"
        }
        try {
            hunterid = intent.getLongExtra(ChatActivity.HUNTERID, 0)
            taskid = intent.getStringExtra(ChatActivity.TASKID)
            userid = intent.getLongExtra(ChatActivity.USERID, 0)
        } catch (e: Exception) {
            toast("信息不全")
            finish()
        }

        var linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = OrientationHelper.VERTICAL
        mRvChat.layoutManager = linearLayoutManager

        var data = ArrayList<Chat>()
        mRvChatAdapter = RvChatAdapter(data)
        mRvChat.adapter = mRvChatAdapter


        if (UserInfo.userId == userid) {
            mPresenter.chatDetail(taskid, hunterid, userid, 0, 30)
        } else {
            mPresenter.chatDetail(taskid, UserInfo.userId, userid, 0, 30)
        }
//        mPresenter.chatDetail(taskid, hunterid, userid, 0, 10)

        mBtSend.onClick {
            if (!mEtContent.text.toString().isEmpty()) {
                if (UserInfo.userId == userid) {
                    mPresenter.saveChat(hunterid, userid, taskid, mEtContent.text.toString())
                    mPresenter.chatDetail(taskid, hunterid, userid, 0, 20)
                } else {
                    mPresenter.saveChat(UserInfo.userId, userid, taskid, mEtContent.text.toString())
                    mPresenter.chatDetail(taskid, UserInfo.userId, userid, 0, 20)
                }
                mEtContent.text.clear()

            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
