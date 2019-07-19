package apps.user.jaksimforever.utils

import apps.user.jaksimforever.data.LoginData
import apps.user.jaksimforever.data.ResultData
import io.reactivex.Single
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
    @POST("/members/{member}")
    fun resultRepos(@Path("member") member : String, @Body loginData: LoginData) : Single<ResultData>
}