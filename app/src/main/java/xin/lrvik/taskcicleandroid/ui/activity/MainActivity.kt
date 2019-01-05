package xin.lrvik.taskcicleandroid.ui.activity

import android.os.Bundle
import android.support.design.internal.BottomNavigationMenuView
import android.support.v4.app.Fragment
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseActivity
import xin.lrvik.taskcicleandroid.ui.fragment.HomeFragment
import xin.lrvik.taskcicleandroid.ui.fragment.MesFragment
import xin.lrvik.taskcicleandroid.ui.fragment.MyFragment
import xin.lrvik.taskcicleandroid.ui.fragment.TaskManagerFragment
import java.util.*

class MainActivity : BaseActivity() {
    private val mStack by lazy { Stack<Fragment>() }
    private val mHomeFragment by lazy { HomeFragment() }
    private val mTaskFragment by lazy { TaskManagerFragment() }
    private val mMsgFragment by lazy { MesFragment() }
    private val mMyFragment by lazy { MyFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFragment()
        initView()
    }

    private fun initFragment() {
        val manager = supportFragmentManager.beginTransaction()
        manager.add(R.id.mContaier, mHomeFragment)
        manager.add(R.id.mContaier, mTaskFragment)
        manager.add(R.id.mContaier, mMsgFragment)
        manager.add(R.id.mContaier, mMyFragment)
        manager.commit()

        mStack.add(mHomeFragment)
        mStack.add(mTaskFragment)
        mStack.add(mMsgFragment)
        mStack.add(mMyFragment)

        changeFragment(0)
    }

    private fun initView() {
        val menuView = mBottomNavBar.getChildAt(0) as BottomNavigationMenuView
        val iconView = menuView.getChildAt(2).findViewById(android.support.design.R.id.icon) as View
        val layoutParams: ViewGroup.LayoutParams = iconView.layoutParams
        val displayMetrics: DisplayMetrics = resources.displayMetrics

        layoutParams.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45f, displayMetrics).toInt()
        layoutParams.width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45f, displayMetrics).toInt()
        iconView.layoutParams = layoutParams

        mBottomNavBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> changeFragment(0)
                R.id.navigation_task -> changeFragment(1)
                R.id.navigation_release -> startActivity<TaskDetailActivity>()
                R.id.navigation_msg -> changeFragment(2)
                R.id.navigation_my -> changeFragment(3)
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun changeFragment(position: Int) {
        val manager = supportFragmentManager.beginTransaction()
        for (fragment in mStack) {
            manager.hide(fragment)
        }
        manager.show(mStack[position])
        manager.commit()
    }


    private var isExit: Boolean? = false

    /**
     * 菜单、返回键响应
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click() // 调用双击退出函数
        }
        return false
    }

    /**
     * 双击退出函数
     */
    private fun exitBy2Click() {
        var tExit: Timer? = null
        if (isExit == false) {
            isExit = true // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show()
            tExit = Timer()
            tExit.schedule(object : TimerTask() {
                override fun run() {
                    isExit = false // 取消退出
                }
            }, 2000) // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish()
            System.exit(0)
        }
    }
}
