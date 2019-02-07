package xin.lrvik.taskcicleandroid.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter.POSITION_NONE
import android.view.View
import android.view.ViewGroup
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

    /*override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(fragments[position] as View)
        return fragments[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }*/

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position%fragments.size]
    }

}
