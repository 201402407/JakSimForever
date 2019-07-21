package apps.user.jaksimforever

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.widget.ImageView
import apps.user.jaksimforever.adapter.DurationTapAdapter
import apps.user.jaksimforever.adapter.RoomListAdapter
import apps.user.jaksimforever.data.RoomListData
import apps.user.jaksimforever.databinding.ActivityRoomListBinding
import kotlinx.android.synthetic.main.activity_room_list.*
import android.support.design.widget.TabLayout
import android.util.Log
import kotlinx.android.synthetic.main.room_list_duration_tab.view.*
import apps.user.jaksimforever.JakSimUtil.Companion.SERVER_URL
import apps.user.jaksimforever.JakSimUtil.Companion.TAG


class RoomListActivity : FontActivity() {
    var roomListAdapter: RoomListAdapter? = null
    var context: Context? = null
    var roomDataList = arrayListOf<RoomListData>()
    // private val durationTapAdapter by lazy{ DurationTapAdapter(supportFragmentManager) }
    private var durationTapAdapter: DurationTapAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_list)
        // Data Binding을 위한 ContentView 설정.
//        val binding: ActivityRoomListBinding = DataBindingUtil.setContentView(this, R.layout.activity_room_list)
        context = applicationContext

        // 상단 기간 탭 레이아웃 설정
        // 탭 레이아웃 초기화
        durationTabLayout.addTab(durationTabLayout.newTab().setText("1"))
        durationTabLayout.addTab(durationTabLayout.newTab().setText("2"))
        durationTabLayout.addTab(durationTabLayout.newTab().setText("3"))
        // 뷰페이저 어댑터 연결
        durationTapAdapter = DurationTapAdapter(supportFragmentManager, durationTabLayout.tabCount)
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


//        roomDataList.add(RoomListData("토익 900", "해워닝"))
//        roomDataList.add(RoomListData("토ㄱㄱ", "rr"))
//        roomDataList.add(RoomListData("토익 토토", "asdgagd"))
//        roomDataList.add(RoomListData("124 900", "zzzz"))
//
//        // 중간 결과 리스트 레이아웃 설정
//        // 현재 목표를 담은 RecyclerView 설정
//        roomListAdapter = RoomListAdapter(context!!, roomDataList)
//        binding.roomListRecyclerView.adapter = roomListAdapter
//        binding.roomListRecyclerView.layoutManager = LinearLayoutManager(this)
//        binding.roomListRecyclerView.setHasFixedSize(true)   // 테스트 필요
    }

    /*
    탭 클릭에 따른 이미지 변화 함수
     */
    fun setDurationTabImage() {
        durationTabLayout.getTabAt(0)?.setIcon(R.drawable.list_sevendayfilterclick)
        durationTabLayout.getTabAt(1)?.setIcon(R.drawable.list_onemonthfilter)
        durationTabLayout.getTabAt(2)?.setIcon(R.drawable.list_threemonthfilter)

        for (i in 0 until durationTabLayout.tabCount) {
            val tab = durationTabLayout.getTabAt(i)
            Log.d(TAG, "$i 는 아이이다")
            tab.let { tab!!.setCustomView(R.layout.room_list_duration_tab) }
        }
    }
}
