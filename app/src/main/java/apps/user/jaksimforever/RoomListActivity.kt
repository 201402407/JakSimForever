package apps.user.jaksimforever

import android.content.Context
import android.os.Bundle
import android.support.v4.view.ViewPager
import apps.user.jaksimforever.adapter.DurationTabAdapter
import apps.user.jaksimforever.adapter.RoomListAdapter
import apps.user.jaksimforever.data.RoomListData
import kotlinx.android.synthetic.main.activity_room_list.*


class RoomListActivity : FontActivity() {
    var roomListAdapter: RoomListAdapter? = null
    var context: Context? = null
    var roomDataList = arrayListOf<RoomListData>()
    // private val durationTapAdapter by lazy{ DurationTabAdapter(supportFragmentManager) }
    private var durationTapAdapter: DurationTabAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_list)
        context = applicationContext

        // 상단 기간 탭 레이아웃 설정
        // 탭 레이아웃 초기화
        durationTabLayout.addTab(durationTabLayout.newTab().setText("1"))
        durationTabLayout.addTab(durationTabLayout.newTab().setText("2"))
        durationTabLayout.addTab(durationTabLayout.newTab().setText("3"))
        // 뷰페이저 어댑터 연결
        durationTapAdapter = DurationTabAdapter(supportFragmentManager, durationTabLayout.tabCount)
        roomListViewPager.adapter = durationTapAdapter
        // 탭 레이아웃에 뷰페이저 연결
        durationTabLayout.setupWithViewPager(roomListViewPager)
        // 탭에 이미지 넣기 (첫 화면)
        setDurationTabImage()
        // 상단 기간 탭 레이아웃 클릭 이벤트
        roomListViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                // 전체 초기화
                durationTabLayout.getTabAt(0)?.setIcon(R.drawable.list_sevendayfilter)
                durationTabLayout.getTabAt(1)?.setIcon(R.drawable.list_onemonthfilter)
                durationTabLayout.getTabAt(2)?.setIcon(R.drawable.list_threemonthfilter)
                when(position) {
                    0 -> durationTabLayout.getTabAt(position)?.setIcon(R.drawable.list_sevendayfilterclick)
                    1 -> durationTabLayout.getTabAt(position)?.setIcon(R.drawable.list_onemonthfilterclick)
                    2 -> durationTabLayout.getTabAt(position)?.setIcon(R.drawable.list_threemonthfilterclick)
                }
            }
        })

        // 초기 첫 번째 탭의 Fragment 설정(일부러 가운데부터 시작)
        roomListViewPager.currentItem = 1
    }

    /*
    탭 클릭에 따른 이미지 변화 함수
     */
    private fun setDurationTabImage() {
        durationTabLayout.getTabAt(0)?.setIcon(R.drawable.list_sevendayfilterclick)
        durationTabLayout.getTabAt(1)?.setIcon(R.drawable.list_onemonthfilter)
        durationTabLayout.getTabAt(2)?.setIcon(R.drawable.list_threemonthfilter)

        for (i in 0 until durationTabLayout.tabCount) {
            val tab = durationTabLayout.getTabAt(i)
            tab.let {
                tab!!.setCustomView(R.layout.room_list_duration_tab)
            }
        }
    }
}
