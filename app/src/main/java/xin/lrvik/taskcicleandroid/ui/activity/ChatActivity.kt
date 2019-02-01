package xin.lrvik.taskcicleandroid.ui.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.MenuItem
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_chat.*
import org.jetbrains.anko.toast
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseMvpActivity
import xin.lrvik.taskcicleandroid.baselibrary.utils.DateUtils
import xin.lrvik.taskcicleandroid.common.UserInfo
import xin.lrvik.taskcicleandroid.common.UserInfo.userId
import java.util.*
import xin.lrvik.taskcicleandroid.data.protocol.Chat
import xin.lrvik.taskcicleandroid.data.protocol.ChatMsg
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.ChatPresenter
import xin.lrvik.taskcicleandroid.presenter.view.ChatView
import xin.lrvik.taskcicleandroid.ui.adapter.RvChatAdapter


class ChatActivity : BaseMvpActivity<ChatPresenter>(), ChatView {
    override fun onSendResult(chat: Chat) {
        mRvChatAdapter.addData(chat)
        mRvChat.scrollToPosition(mRvChatAdapter.itemCount - 1)
    }

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
        var isForeground = false
        val MESSAGE_RECEIVED_ACTION = "xin.lrvik.taskcicleandroid.MESSAGE_RECEIVED_ACTION"
        val CHATMSG = "chatMsg"
    }


    override fun onResume() {
        isForeground = true
        super.onResume()
    }

    override fun onPause() {
        isForeground = false
        super.onPause()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        initView()
        registerMessageReceiver()
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
                } else {
                    mPresenter.saveChat(UserInfo.userId, userid, taskid, mEtContent.text.toString())
                }
                mEtContent.text.clear()

            }
        }
    }

    //这里实现动态广播接收推送来的消息
    private lateinit var mMessageReceiver: MessageReceiver


    fun registerMessageReceiver() {
        mMessageReceiver = MessageReceiver()
        val filter = IntentFilter()
        filter.priority = IntentFilter.SYSTEM_HIGH_PRIORITY
        filter.addAction(MESSAGE_RECEIVED_ACTION)
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter)
    }

    inner class MessageReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION == intent.action) {
                    val chatMsgStr = intent.getStringExtra(CHATMSG)
                    var chatMsg = Gson().fromJson(chatMsgStr, ChatMsg::class.java)

                    var chat = Chat(chatMsg.hunterId,
                            chatMsg.userId,
                            chatMsg.sender,
                            chatMsg.taskId,
                            chatMsg.createTime,
                            chatMsg.content,
                            chatMsg.userIcon,
                            chatMsg.hunterIcon)

                    //将消息从新序列化会RV中
                    mRvChatAdapter.addData(chat)
                    mRvChat.scrollToPosition(mRvChatAdapter.itemCount - 1)
                }
            } catch (e: Exception) {
            }

        }
    }


    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
        super.onDestroy()
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
