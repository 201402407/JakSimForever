package apps.user.jaksimforever.data

import com.google.gson.annotations.SerializedName

data class RoomData(@SerializedName(value = "room_name") var roomName: String?,
                    @SerializedName(value = "room_manager_name") var nickname: String?,
                    @SerializedName(value = "room_description") var roomDescription: String?,
                    @SerializedName(value = "room_duration") var duration: Int?,
                    @SerializedName(value = "room_money") var money: Int?,
                    @SerializedName(value = "room_maxpeople") var maxPeople: Int?
)