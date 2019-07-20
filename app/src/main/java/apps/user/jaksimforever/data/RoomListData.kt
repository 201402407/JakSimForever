package apps.user.jaksimforever.data

import android.view.View
import android.widget.Toast

class RoomListData (val room_name: String, val room_manager_nickname: String) {
    fun onClickListener(view: View) {
        Toast.makeText(view.context, "Click : $room_name, $room_manager_nickname", Toast.LENGTH_LONG).show()
    }
}