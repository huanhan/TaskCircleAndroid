package xin.lrvik.taskcicleandroid.ui.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.util.Log
import android.view.MenuItem
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_chat.*
import org.jetbrains.anko.toast
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseMvpActivity
import xin.lrvik.taskcicleandroid.baselibrary.common.UserInfo
import xin.lrvik.taskcicleandroid.data.protocol.Chat
import xin.lrvik.taskcicleandroid.data.protocol.ChatMsg
import xin.lrvik.taskcicleandroid.data.protocol.Page
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.ChatPresenter
import xin.lrvik.taskcicleandroid.presenter.view.ChatView
import xin.lrvik.taskcicleandroid.ui.adapter.RvChatAdapter
import xin.lrvik.taskcicleandroid.util.NotificationUtils
import kotlin.collections.ArrayList


class ChatActivity : BaseMvpActivity<ChatPresenter>(), ChatView {
    override fun onSendResult(chat: Chat) {
        addData(chat)
    }

    override fun injectComponent() {
        DaggerTaskCircleComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onChatListResult(data: Page<Chat>) {
        if (!data.content.isNullOrEmpty()) {
            data.content.reverse()
            mRvChatAdapter.setNewData(data.content)
            mRvChat.scrollToPosition(mRvChatAdapter.itemCount - 1)
        }
    }

    override fun onResult(result: String) {
        toast(result)
    }


    //任务id,猎刃id，用户id
    var hunterid: Long = 0
    lateinit var hunterTaskId: String
    var userid: Long = 0
    lateinit var mRvChatAdapter: RvChatAdapter

    companion object {
        val HUNTERID = "HUNTERID"
        val HUNTERTASKID = "HUNTERTASKID"
        val USERID = "USERID"
        var isForeground = false
        val MESSAGE_RECEIVED_ACTION = "xin.lrvik.taskcicleandroid.MESSAGE_RECEIVED_ACTION"
        val CHATMSG = "chatMsg"
    }


    override fun onResume() {
        isForeground = true

        mRvChatAdapter.data.clear()
        if (UserInfo.userId == userid) {
            mPresenter.chatDetail(hunterTaskId, hunterid, userid, 0, 50)
        } else {
            mPresenter.chatDetail(hunterTaskId, UserInfo.userId, userid, 0, 50)
        }
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
            hunterTaskId = intent.getStringExtra(ChatActivity.HUNTERTASKID)
            userid = intent.getLongExtra(ChatActivity.USERID, 0)
            Log.d("test", "hunterid $hunterid 任务id $hunterTaskId  userid:$userid ")
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


//        mPresenter.chatDetail(huntertaskid, hunterid, userid, 0, 10)

        mBtSend.onClick {
            if (!mEtContent.text.toString().isEmpty()) {
                if (UserInfo.userId == userid) {
                    mPresenter.saveChat(hunterid, userid, hunterTaskId, mEtContent.text.toString())
                } else {
                    mPresenter.saveChat(UserInfo.userId, userid, hunterTaskId, mEtContent.text.toString())
                }
                mEtContent.text.clear()

            }
        }
    }

    fun addData(chat: Chat) {
        if (mRvChatAdapter.data.size == 0) {
            mRvChatAdapter.setNewData(arrayListOf(chat))
        } else {
//            mRvChatAdapter.data.add(chat)
//            mRvChatAdapter.notifyDataSetChanged()
            mRvChatAdapter.addData(chat)
        }
        mRvChat.scrollToPosition(mRvChatAdapter.itemCount - 1)
    }

    override fun onNewIntent(intent: Intent) {
        setIntent(intent)
        super.onNewIntent(intent)
        try {
            hunterid = intent.getLongExtra(ChatActivity.HUNTERID, 0)
            hunterTaskId = intent.getStringExtra(ChatActivity.HUNTERTASKID)
            userid = intent.getLongExtra(ChatActivity.USERID, 0)

            intent.getStringExtra("test")
        } catch (e: Exception) {
            toast("信息不全")
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
                    if (chatMsg.hunterTaskId == hunterTaskId) {
                        if (UserInfo.userId == userid) {
                            if (hunterid == chatMsg.sender) {
                                addData(Chat.toChat(chatMsg))
                            } else {
                                var intent = Intent(this@ChatActivity, ChatActivity::class.java)
                                intent.putExtra(ChatActivity.HUNTERID, chatMsg.hunterId)
                                intent.putExtra(ChatActivity.HUNTERTASKID, chatMsg.hunterTaskId)
                                intent.putExtra(ChatActivity.USERID, chatMsg.userId)
                                NotificationUtils(context).sendNotification(chatMsg.title, chatMsg.content, intent)
                            }
                        } else {
                            if (userid == chatMsg.sender) {
                                addData(Chat.toChat(chatMsg))
                            } else {
                                var intent = Intent(this@ChatActivity, ChatActivity::class.java)
                                intent.putExtra(ChatActivity.HUNTERID, chatMsg.hunterId)
                                intent.putExtra(ChatActivity.HUNTERTASKID, chatMsg.hunterTaskId)
                                intent.putExtra(ChatActivity.USERID, chatMsg.userId)
                                NotificationUtils(context).sendNotification(chatMsg.title, chatMsg.content, intent)
                            }
                        }
                    } else {
                        var intent = Intent(this@ChatActivity, ChatActivity::class.java)
                        intent.putExtra(ChatActivity.HUNTERID, chatMsg.hunterId)
                        intent.putExtra(ChatActivity.HUNTERTASKID, chatMsg.hunterTaskId)
                        intent.putExtra(ChatActivity.USERID, chatMsg.userId)
                        NotificationUtils(context).sendNotification(chatMsg.title, chatMsg.content, intent)
                    }
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
