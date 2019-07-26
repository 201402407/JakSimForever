package apps.user.jaksimforever

import android.text.TextUtils
import android.widget.EditText

/*
 전체적으로 사용하는 변수나 연산 함수 등 유틸리티 클래스
 */
class JakSimUtil {
    // 전역변수로 변경. 다른 클래스에서 직접 참조 가능
    companion object {
        const val SERVER_URL: String = "http://192.168.1.6:8080"
        const val TAG: String = "LogGoGo"

        /*
        EditText의 값 중 공백 확인
        @return : boolean(true : 앞 뒤 공백 또는 전체 공백이 아님. false : 둘 중 하나라도 해당하는 경우)
         */
        fun checkEditText(editText: EditText): Boolean {
            val editStr = editText.text.toString()    // 검색어 임시 변수에 저장.
            if (TextUtils.isEmpty(editStr.trim()))  // 공백처리
                return false
            return editStr == editStr.trim()
        }
    }
}