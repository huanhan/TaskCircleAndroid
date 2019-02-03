package xin.lrvik.taskcicleandroid.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.MenuItem
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_task_history.*
import org.jetbrains.anko.startActivity
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.common.BaseApplication.Companion.context
import xin.lrvik.taskcicleandroid.baselibrary.common.BaseConstant
import xin.lrvik.taskcicleandroid.baselibrary.utils.AppPrefsUtils
import xin.lrvik.taskcicleandroid.data.protocol.Task
import xin.lrvik.taskcicleandroid.data.protocol.TaskHistory
import xin.lrvik.taskcicleandroid.ui.adapter.RvRecommendAdapter

class TaskHistoryActivity : AppCompatActivity() {

    lateinit var mRvRecommendAdapter: RvRecommendAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_history)

        initView()
    }

    private fun initView() {
        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "足迹"
        }

        var historys = AppPrefsUtils.getString(BaseConstant.KEY_SP_HISTORY)
        if (historys.isNullOrEmpty()) {
            var taskHistory = TaskHistory()
            taskHistory.size = 0
            taskHistory.tasks = ArrayList()
            var taskHistoryStr = Gson().toJson(taskHistory)
            AppPrefsUtils.putString(BaseConstant.KEY_SP_HISTORY, taskHistoryStr)
            historys = taskHistoryStr
        }

        var taskHistorys = Gson().fromJson(historys, TaskHistory::class.java)

        var linearLayoutManager = LinearLayoutManager(context)
        mRvTask.layoutManager = linearLayoutManager
        linearLayoutManager.orientation = OrientationHelper.VERTICAL
        var list = java.util.ArrayList<Task>()
        mRvRecommendAdapter = RvRecommendAdapter(taskHistorys.tasks!!.reversed())
        mRvTask.adapter = mRvRecommendAdapter

        mRvRecommendAdapter.setOnItemClickListener { adapter, view, position ->
            var task = adapter.data[position] as Task
            startActivity<TaskDetailActivity>(TaskDetailActivity.TASKID to task.id!!)
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
