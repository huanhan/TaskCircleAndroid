package xin.lrvik.taskcicleandroid.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_chat.*
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseActivity
import java.util.*
import xin.lrvik.taskcicleandroid.data.protocol.Chat
import xin.lrvik.taskcicleandroid.ui.adapter.RvChatAdapter


class ChatActivity : BaseActivity() {

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

        var linearLayoutManager = LinearLayoutManager(this)
        mRvChat.layoutManager = linearLayoutManager
        linearLayoutManager.orientation = OrientationHelper.VERTICAL

        var data = ArrayList<Chat>()
        data.add(Chat("", "老板，你这个任务难度大吗", Chat.RECEIVE_TYPE))
        data.add(Chat("", "你先看看步骤介绍", Chat.SEND_TYPE))
        data.add(Chat("", "还行", Chat.RECEIVE_TYPE))
        data.add(Chat("", "问题不大，你在附近就可以马上做的，我就在这里", Chat.SEND_TYPE))
        data.add(Chat("", "好的，那我开始了", Chat.RECEIVE_TYPE))
        data.add(Chat("", "合作愉快", Chat.RECEIVE_TYPE))
        data.add(Chat("", "合作愉快！", Chat.SEND_TYPE))
        data.add(Chat("", "老板，你这个任务难度大吗", Chat.RECEIVE_TYPE))
        data.add(Chat("", "你先看看步骤介绍", Chat.SEND_TYPE))
        data.add(Chat("", "还行", Chat.RECEIVE_TYPE))
        data.add(Chat("", "问题不大，你在附近就可以马上做的，我就在这里", Chat.SEND_TYPE))
        data.add(Chat("", "好的，那我开始了", Chat.RECEIVE_TYPE))
        data.add(Chat("", "合作愉快", Chat.RECEIVE_TYPE))
        data.add(Chat("", "合作愉快！", Chat.SEND_TYPE))

        var mAdapter = RvChatAdapter(data)
        mRvChat.adapter = mAdapter

        mRvChat.scrollToPosition(mAdapter.itemCount - 1)
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
