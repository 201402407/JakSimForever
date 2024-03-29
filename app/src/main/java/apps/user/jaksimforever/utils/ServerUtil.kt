package apps.user.jaksimforever.utils

import android.telecom.Call
import apps.user.jaksimforever.data.JoinData
import apps.user.jaksimforever.data.LoginData
import apps.user.jaksimforever.data.RoomData
import apps.user.jaksimforever.data.RoomListData
import com.google.gson.annotations.SerializedName
import io.reactivex.Observable
import io.reactivex.Single
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

// 서비스 정의
interface LoginService {

    // member 관련 통신 시 사용
    // post방식. value는 메인 주소 뒤에 붙는 주소. {}는 임의의 값의 이름
    // @Path를 통해 임의의 값인 member에 String 변수값을 넣는다는 뜻.
    // @Body를 통해 POST 전송 시 LoginData data를 담아 전송
    // Single<ResultData>를 통해 ResultData로 데이터를 받겠다는 뜻.
    @POST("/members/login")
    fun resultRepos(@Body loginData: LoginData) : Single<LoginResultData>
}
data class LoginResultData(val result : Int, var nickname : String?, var reason : String?)

interface JoinService {
    @POST("/members/join")
    fun resultJoinRepos(@Body joinData: JoinData) : Single<ResultData>

    @POST("/members/{check}")
    fun resultCheckRepos(@Path("check") member : String, @Body params: HashMap<String, String>) : Single<ResultData>
}
data class ResultData(val result : Int)

interface RoomListService {
    @POST("/rooms/getRoomList")
    fun resultRoomListRepos(@Body params: HashMap<String, Int>) : Single<ArrayList<RoomListData>>
}

interface AddRoomService {
    @POST("/rooms/addRoom")
    fun resultAddRoomRepos(@Body roomData: RoomData) : Single<ResultData>
}