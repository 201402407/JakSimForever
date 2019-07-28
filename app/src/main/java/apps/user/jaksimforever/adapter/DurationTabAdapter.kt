package apps.user.jaksimforever.adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import apps.user.jaksimforever.fragment.RoomListFragment
import android.support.v4.view.PagerAdapter



class DurationTabAdapter(fm: FragmentManager, private var tabCount: Int) :  FragmentPagerAdapter(fm) {
    private val mFragmentList: ArrayList<Fragment> = ArrayList()
    private val mFragmentTitleList: ArrayList<String> = ArrayList()

    // 누른 탭의 인덱스 값을 받아 Fragment에 전달.
    // 받은 Fragment에서 인덱스 값에 따른 경우 처리 하면 됨.
    // 0 : 7일,  1 : 1달,  2 : 3달
    override fun getItem(position: Int): Fragment {
        Log.d("LogGoGo", "$position is this getItem.")
        return mFragmentList[position]
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    // 생성 할 Fragment 의 개수
    override fun getCount() = tabCount

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
        //Log.e("FragmentPagerAdapter", "destroyItem position : $position")
    }

    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]
    }

//    fun getFragment(position: Int): Fragment? {
//        Log.d("LogGoGo", "$position is this getFragment.")
//        return RoomListFragment.newInstance(position.toString())
//    }
}