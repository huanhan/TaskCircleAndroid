package xin.lrvik.taskcicleandroid.ui.receiver

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.text.TextUtils

import org.json.JSONException
import org.json.JSONObject

import cn.jpush.android.api.JPushInterface
import com.google.gson.Gson
import com.google.gson.JsonParser
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.data.protocol.ChatMsg
import xin.lrvik.taskcicleandroid.data.protocol.PushMsgState
import xin.lrvik.taskcicleandroid.data.protocol.TaskMsg
import xin.lrvik.taskcicleandroid.ui.activity.HunterRunningActivity
import xin.lrvik.taskcicleandroid.ui.activity.HunterTaskDetailActivity
import xin.lrvik.taskcicleandroid.ui.activity.TaskDetailActivity
import android.graphics.BitmapFactory
import android.net.Uri
import android.content.Context.NOTIFICATION_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import xin.lrvik.taskcicleandroid.baselibrary.common.BaseApplication
import xin.lrvik.taskcicleandroid.ui.activity.ChatActivity
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
            Logger.d(TAG, "[MyReceiver] onReceive - " + intent.action + ", extras: " + printBundle(bundle!!))

            if (JPushInterface.ACTION_MESSAGE_RECEIVED == intent.action) {
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE)!!)
                processCustomMessage(context, bundle)

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED == intent.action) {
                val notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID)
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: $notifactionId")

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED == intent.action) {
                Logger.d(TAG, "[MyReceiver] 用户点击打开了通知")

//                var type = JsonParser().parse(bundle.getString(JPushInterface.EXTRA_EXTRA)).asJsonObject["type"].asString
//                var extra = JsonParser().parse(bundle.getString(JPushInterface.EXTRA_EXTRA)).asJsonObject["extra"].asString

//                var type = JsonParser().parse(extraStr).asJsonObject["type"].asInt
//                var extra = JsonParser().parse(extraStr).asJsonObject["extra"].asString

                /* when (PushMsgState.valueOf(type)) {
                     PushMsgState.TASK -> {//有关系到任务id的通知
                         val i = Intent(context, TaskDetailActivity::class.java)
                         i.putExtra(TaskDetailActivity.TASKID, extra)
                         i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                         context.startActivity(i)
                     }
                     PushMsgState.HUNTER_TASK -> {//有关系到猎刃任务id的通知
                         val i = Intent(context, HunterTaskDetailActivity::class.java)
                         i.putExtra(HunterTaskDetailActivity.TASKID, extra)
                         i.putExtra(HunterTaskDetailActivity.MODE, HunterTaskDetailActivity.Mode.LOOK.name)
                         i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                         context.startActivity(i)
                     }
                     PushMsgState.HUNTER_LIST -> {//有关系到聊天消息的通知
                         val i = Intent(context, HunterRunningActivity::class.java)
                         i.putExtra(HunterRunningActivity.TASKID, extra)
                         i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                         context.startActivity(i)
                     }
                     PushMsgState.NOTICE -> {//有关系到聊天消息的通知
                         *//*val i = Intent(context, HunterRunningActivity::class.java)
                        i.putExtra(HunterRunningActivity.TASKID, extra)
                        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        context.startActivity(i)*//*
                    }
                    else -> {
                    }
                }*/

                //打开自定义的Activity
                /*Intent i = new Intent(context, TestActivity.class);
				i.putExtras(bundle);
				//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
				context.startActivity(i);*/

            } else {
                Logger.d(TAG, "[MyReceiver] Unhandled intent - " + intent.action!!)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

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
            PushMsgState.HUNTER_LIST -> {//有关系到聊天消息的通知
                var task = Gson().fromJson(extra, TaskMsg::class.java)
                val intent = Intent(context, HunterRunningActivity::class.java)
                intent.putExtra(HunterRunningActivity.TASKID, task.extraData)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP

                NotificationUtils(context).sendNotification(task.title, task.content, intent)
            }
            PushMsgState.NOTICE -> {//有关系到消息通知的
                //todo 通知相关的推送
            }
            PushMsgState.CHAT -> {//有关系到聊天消息的通知
                var chatMsg = Gson().fromJson(extra, ChatMsg::class.java)
//                TitleTextWindow(context).show(chatMsg.icon, chatMsg.title, chatMsg.content)

                var intent = Intent(BaseApplication.context, ChatActivity::class.java)
                intent.putExtra(ChatActivity.HUNTERID, chatMsg.hunterId)
                intent.putExtra(ChatActivity.TASKID, chatMsg.taskId)
                intent.putExtra(ChatActivity.USERID, chatMsg.userId)
                NotificationUtils(context).sendNotification(chatMsg.title, chatMsg.content, intent)

            }
            else -> {
            }
        }



        /*if (MainActivity.isForeground) {
			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
			if (!ExampleUtil.isEmpty(extras)) {
				try {
					JSONObject extraJson = new JSONObject(extras);
					if (extraJson.length() > 0) {
						msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
					}
				} catch (JSONException e) {

				}

			}
			LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
		}*/
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
                        Logger.i(TAG, "This message has no Extra data")
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
                        Logger.e(TAG, "Get message extra JSON error!")
                    }

                } else {
                    sb.append("\nkey:" + key + ", value:" + bundle.get(key))
                }
            }
            return sb.toString()
        }
    }
}
