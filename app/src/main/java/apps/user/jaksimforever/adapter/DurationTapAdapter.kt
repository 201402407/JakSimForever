package apps.user.jaksimforever.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.ViewGroup
import android.widget.Toast
import apps.user.jaksimforever.fragment.RoomListFragment

class DurationTapAdapter(fm: FragmentManager, private var tabCount: Int) : FragmentStatePagerAdapter(fm) {
    // 누른 탭의 인덱스 값을 받아 Fragment에 전달.
    // 받은 Fragment에서 인덱스 값에 따른 경우 처리 하면 됨.
    override fun getItem(position: Int): Fragment? {
        return when(position) {
            0, 1, 2 -> RoomListFragment(position)
            else -> null
        }
    }

    // 생성 할 Fragment 의 개수
    override fun getCount() = tabCount

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
        //Log.e("FragmentPagerAdapter", "destroyItem position : $position")
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return null
    }

}