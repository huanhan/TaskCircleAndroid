package xin.lrvik.taskcicleandroid.ui.receiver

import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils

import org.json.JSONException
import org.json.JSONObject

import cn.jpush.android.api.JPushInterface
import com.google.gson.Gson
import com.google.gson.JsonParser
import xin.lrvik.taskcicleandroid.data.protocol.ChatMsg
import xin.lrvik.taskcicleandroid.data.protocol.PushMsgState
import xin.lrvik.taskcicleandroid.data.protocol.TaskMsg
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import xin.lrvik.taskcicleandroid.baselibrary.common.BaseApplication
import xin.lrvik.taskcicleandroid.ui.activity.*
import xin.lrvik.taskcicleandroid.util.NotificationUtils


/**
 * 自定义接收器
 *
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        try {
            val bundle = intent.extras
            Log.d(TAG, "[MyReceiver] onReceive - " + intent.action + ", extras: " + printBundle(bundle!!))

            if (JPushInterface.ACTION_MESSAGE_RECEIVED == intent.action) {
                Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE)!!)
                processCustomMessage(context, bundle)

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED == intent.action) {
                val notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID)
                Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: $notifactionId")

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED == intent.action) {
                Log.d(TAG, "[MyReceiver] 用户点击打开了通知")
                if (!getCurrentTask(context)) {
                    var i = Intent(context, MainActivity::class.java)
                    i.putExtras(bundle)
                    //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    context.startActivity(i)
                }
            } else {
                Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.action!!)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getCurrentTask(context: Context): Boolean {

        var activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        //获取当前所有存活task的信息
        var appProcessInfos = activityManager.getRunningTasks(Integer.MAX_VALUE)

        //遍历，若task的name与当前task的name相同，则返回true，否则，返回false
        for (process in appProcessInfos) {
            if (process.baseActivity.packageName == context.packageName
                    || process.topActivity.packageName == context.packageName) {
                return true
            }
        }
        return false
    }

    //send msg to MainActivity
    private fun processCustomMessage(context: Context, bundle: Bundle) {
        var type = JsonParser().parse(bundle.getString(JPushInterface.EXTRA_EXTRA)).asJsonObject["type"].asString
        var extra = JsonParser().parse(bundle.getString(JPushInterface.EXTRA_EXTRA)).asJsonObject["extra"].asString

        when (PushMsgState.valueOf(type)) {
            PushMsgState.TASK -> {//有关系到任务id的通知
                var task = Gson().fromJson(extra, TaskMsg::class.java)
                val intent = Intent(context, TaskDetailActivity::class.java)
                intent.putExtra(TaskDetailActivity.TASKID, task.extraData)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                NotificationUtils(context).sendNotification(task.title, task.content, intent)
            }
            PushMsgState.HUNTER_TASK -> {//有关系到猎刃任务id的通知
                var task = Gson().fromJson(extra, TaskMsg::class.java)
                val intent = Intent(context, HunterTaskDetailActivity::class.java)
                intent.putExtra(HunterTaskDetailActivity.TASKID, task.extraData)
                intent.putExtra(HunterTaskDetailActivity.MODE, HunterTaskDetailActivity.Mode.LOOK.name)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                NotificationUtils(context).sendNotification(task.title, task.content, intent)
            }
            PushMsgState.HUNTER_LIST -> {//有关系到猎刃列表的通知
                var task = Gson().fromJson(extra, TaskMsg::class.java)
                val intent = Intent(context, HunterRunningActivity::class.java)
                intent.putExtra(HunterRunningActivity.TASKID, task.extraData)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                NotificationUtils(context).sendNotification(task.title, task.content, intent)
            }
            PushMsgState.NOTICE -> {//有关系到消息通知的
                //NotificationUtils(context).sendNotification()

            }
            PushMsgState.CHAT -> {//有关系到聊天消息的通知
                //如果当前聊天activity是前台显示，则将消息传递给他
                if (ChatActivity.isForeground) {
                    var msgIntent = Intent(ChatActivity.MESSAGE_RECEIVED_ACTION)
                    msgIntent.putExtra(ChatActivity.CHATMSG, extra)

                    LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent)
                } else {//否则打开该消息界面
                    var chatMsg = Gson().fromJson(extra, ChatMsg::class.java)
                    var intent = Intent(BaseApplication.context, ChatActivity::class.java)
                    intent.putExtra(ChatActivity.HUNTERID, chatMsg.hunterId)
                    intent.putExtra(ChatActivity.HUNTERTASKID, chatMsg.hunterTaskId)
                    intent.putExtra(ChatActivity.USERID, chatMsg.userId)
                    NotificationUtils(context).sendNotification(chatMsg.title, chatMsg.content, intent)
                }
            }
            else -> {
            }
        }

    }

    companion object {
        private val TAG = "JIGUANG-Example"
        private val TYPE = "TYPE"

        // 打印所有的 intent extra 数据
        private fun printBundle(bundle: Bundle): String {
            val sb = StringBuilder()
            for (key in bundle.keySet()) {
                if (key == JPushInterface.EXTRA_NOTIFICATION_ID) {
                    sb.append("\nkey:" + key + ", value:" + bundle.getInt(key))
                } else if (key == JPushInterface.EXTRA_CONNECTION_CHANGE) {
                    sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key))
                } else if (key == JPushInterface.EXTRA_EXTRA) {
                    if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                        Log.i(TAG, "This message has no Extra data")
                        continue
                    }

                    try {
                        val json = JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA))
                        val it = json.keys()

                        while (it.hasNext()) {
                            val myKey = it.next()
                            sb.append("\nkey:" + key + ", value: [" +
                                    myKey + " - " + json.optString(myKey) + "]")
                        }
                    } catch (e: JSONException) {
                        Log.e(TAG, "Get message extra JSON error!")
                    }

                } else {
                    sb.append("\nkey:" + key + ", value:" + bundle.get(key))
                }
            }
            return sb.toString()
        }
    }
}
