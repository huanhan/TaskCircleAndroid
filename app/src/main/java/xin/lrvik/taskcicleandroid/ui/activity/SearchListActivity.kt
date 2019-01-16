package xin.lrvik.taskcicleandroid.ui.activity

import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_search_list.*
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseActivity

class SearchListActivity : BaseActivity() {

    lateinit var query: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_list)
        initView()
    }

    private fun initView() {
        query = intent.getStringExtra("QUERY")
        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "搜索结果:$query"
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
