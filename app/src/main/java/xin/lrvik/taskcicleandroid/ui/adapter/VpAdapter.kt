package xin.lrvik.taskcicleandroid.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import java.util.*

class VpAdapter(supportFragmentManager: FragmentManager,
                private val fragments:Stack<Fragment>,
                private val titles:Stack<String>) : FragmentStatePagerAdapter(supportFragmentManager) {

    override fun getItem(i: Int): Fragment {
        return fragments[i]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position%fragments.size]
    }
}
