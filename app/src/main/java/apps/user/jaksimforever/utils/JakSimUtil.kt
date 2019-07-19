package apps.user.jaksimforever

/*
 전체적으로 사용하는 변수나 연산 함수 등 유틸리티 클래스
 */
class JakSimUtil {

    // 전역변수로 변경. 다른 클래스에서 직접 참조 가능
    companion object {
        val SERVER_URL: String = "http://192.168.1.4:8080"
        val TAG: String = "LogGoGo"
    }
}