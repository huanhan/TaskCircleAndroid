package xin.lrvik.taskcicleandroid.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.margin
import org.jetbrains.anko.startActivity
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseActivity
import xin.lrvik.taskcicleandroid.baselibrary.utils.AppPrefsUtils

class SearchActivity : BaseActivity() {

    internal var colors = intArrayOf(Color.parseColor("#90C5ED"),
            Color.parseColor("#92CED6"),
            Color.parseColor("#F69153"),
            Color.parseColor("#BFAED0"),
            Color.parseColor("#E58F8E"),
            Color.parseColor("#66CCB7"),
            Color.parseColor("#F4BB7E"),
            Color.parseColor("#90C5ED"),
            Color.parseColor("#F79153"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initView()
    }

    private fun initView() {

        //设置搜索按钮
        mSearchView.setHint("你想找的都在这里♪(´ε｀)")
        mSearchView.setVoiceSearch(false)
        //mSearchView.setCursorDrawable(R.drawable.custom_cursor)
        mSearchView.setEllipsize(true)
        mSearchView.setSuggestions(resources.getStringArray(R.array.query_suggestions))
        mSearchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                AppPrefsUtils.putStringSet("HISTORYKEYS", setOf(query))
                startActivity<SearchListActivity>("QUERY" to query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        mSearchView.setSubmitOnClick(true)
        mSearchView.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewShown() {
            }

            override fun onSearchViewClosed() {
                finish()
            }
        })

        //  从sp获取搜索历史
        var historyKeys = AppPrefsUtils.getStringSet("HISTORYKEYS").toList()

        //var historyKeys = listOf<String>("历史1", "历史0", "历史3", "历史2")

        mFlowlayout.adapter = object : TagAdapter<String>(historyKeys) {
            override fun getView(parent: FlowLayout, position: Int, s: String): View {
                //动态创建热词布局
                var tv = TextView(this@SearchActivity)
                var params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                params.margin = dip(6)
                tv.layoutParams = params
                tv.setPadding(dip(9), dip(6), dip(9), dip(6))
                tv.setBackgroundColor(colors[position % colors.size])
                tv.text = s
                return tv
            }
        }

        mFlowlayout.setOnTagClickListener { view, position, parent ->
            startActivity<SearchListActivity>("QUERY" to historyKeys[position])
            true
        }

    }

    override fun onResume() {
        super.onResume()
        mSearchView.showSearch(false)
    }
}
