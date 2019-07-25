package apps.user.jaksimforever.data

import android.view.View
import android.widget.Toast

class RoomListData (val room_name: String, val room_manager_name: String, val room_type: Int) {
    fun onClickListener(view: View) {
        Toast.makeText(view.context, "Click : $room_name, $room_manager_name, $room_type", Toast.LENGTH_LONG).show()
    }
}