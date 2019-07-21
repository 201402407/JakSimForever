package apps.user.jaksimforever.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import apps.user.jaksimforever.R
import apps.user.jaksimforever.adapter.RoomListAdapter
import apps.user.jaksimforever.data.RoomListData
import apps.user.jaksimforever.databinding.FragmentRoomListBinding

@SuppressLint("ValidFragment")
class RoomListFragment(val durationIndex: Int) : Fragment() {
    var roomListAdapter: RoomListAdapter? = null
    var roomDataList = arrayListOf<RoomListData>()
    lateinit var binding: FragmentRoomListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Data Binding을 위한 ContentView 설정.
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_room_list, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//
        when(durationIndex) {
            0 -> sevenDaysFilter()
            1 -> oneMonthFilter()
            2 -> threeMonthFilter()
            else -> null
        }

        // 중간 결과 리스트 레이아웃 설정
        // 현재 목표를 담은 RecyclerView 설정
        roomListAdapter = RoomListAdapter(context!!, roomDataList)
        binding.roomListRecyclerView.adapter = roomListAdapter
        binding.roomListRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.roomListRecyclerView.setHasFixedSize(true)   // 테스트 필요
    }

    fun sevenDaysFilter() {
        roomDataList.add(RoomListData("토익 900", "해워닝"))
    }

    fun oneMonthFilter() {
        roomDataList.add(RoomListData("토ㄱㄱ", "rr"))
        roomDataList.add(RoomListData("토익 토토", "asdgagd"))
    }

    fun threeMonthFilter() {
        roomDataList.add(RoomListData("124 900", "zzzz"))
        roomDataList.add(RoomListData("124 900", "zzzz"))
        roomDataList.add(RoomListData("124 900", "zzzz"))
        roomDataList.add(RoomListData("124 900", "zzzz"))
    }
}