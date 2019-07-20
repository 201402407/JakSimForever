package apps.user.jaksimforever

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import apps.user.jaksimforever.adapter.MainAdapter
import apps.user.jaksimforever.adapter.RoomListAdapter
import apps.user.jaksimforever.data.MainData
import apps.user.jaksimforever.data.RoomListData
import apps.user.jaksimforever.databinding.ActivityRoomListBinding

class RoomListActivity : AppCompatActivity() {
    var roomListAdapter: RoomListAdapter? = null
    var context: Context? = null
    var roomDataList = arrayListOf<RoomListData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Data Binding을 위한 ContentView 설정.
        val binding: ActivityRoomListBinding = DataBindingUtil.setContentView(this, R.layout.activity_room_list)
        context = applicationContext

        roomDataList.add(RoomListData("토익 900", "해워닝"))
        roomDataList.add(RoomListData("토ㄱㄱ", "rr"))
        roomDataList.add(RoomListData("토익 토토", "asdgagd"))
        roomDataList.add(RoomListData("124 900", "zzzz"))

        // 현재 목표를 담은 RecyclerView 설정
        roomListAdapter = RoomListAdapter(context!!, roomDataList)
        binding.roomListRecyclerView.adapter = roomListAdapter
        binding.roomListRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.roomListRecyclerView.setHasFixedSize(true)   // 테스트 필요
    }
}
