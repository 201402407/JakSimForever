package apps.user.jaksimforever.data

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Toast
import apps.user.jaksimforever.JakSimUtil.Companion.NICKNAME
import apps.user.jaksimforever.WaitingRoomActivity
import com.google.gson.annotations.SerializedName

class RoomListData (val _id: String, val room_name: String, val room_manager_name: String, val room_description: String,
                    val room_duration : Int, val room_money: Int, var room_maxpeople: Int) {
    fun onClickListener(view: View) {
        Toast.makeText(view.context, "Click : $_id, $NICKNAME", Toast.LENGTH_LONG).show()
        var intent = Intent(view.context, WaitingRoomActivity::class.java)
        intent.putExtra("_id", _id)
        ContextCompat.startActivity(view.context, intent, null)
    }
}