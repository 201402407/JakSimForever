package apps.user.jaksimforever

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import apps.user.jaksimforever.data.JoinData
import apps.user.jaksimforever.data.LoginData
import apps.user.jaksimforever.utils.JoinService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_join.*
import kotlinx.android.synthetic.main.activity_login.*
import apps.user.jaksimforever.JakSimUtil.Companion.SERVER_URL
import apps.user.jaksimforever.JakSimUtil.Companion.TAG
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class JoinActivity : FontActivity() {
    var context: Context? = null
    var joinData: JoinData? = null
    var isCheckID: Boolean = false
    var isCheckNicname: Boolean = false
    var isJoinCard: Boolean = false
    var memberID: String? = null
    var memberNickname: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        context = applicationContext

        // 가입 서비스 Create
        val service: JoinService = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(SERVER_URL)
            .client(OkHttpClient())
            .build()
            .create(JoinService::class.java)

        // 아이디 중복검사 버튼 클릭 이벤트
        idCheckBtn.setOnTouchListener(View.OnTouchListener { _, event ->
            if(event?.action == MotionEvent.ACTION_DOWN) {
                editTextID.text?.let {
                    // HashMap으로 Json 변환.
                    val map = hashMapOf(
                        "member_id" to editTextID.text.toString()
                    )

                    // 아이디 중복검사 서버 통신
                    service.resultCheckRepos("checkID", map)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({    // 받은 데이터를 사용하는 함수. 받은 데이터 : it
                            Log.d(TAG, "result : ${it.result}")
                            // 서버 통신 성공
                            if (it.result == 1) {   // 중복된 아이디 존재 X
                                isCheckID = true
                                memberID = editTextID.text.toString()
                                Toast.makeText(context, "Success! Use this ID.", Toast.LENGTH_LONG).show()
                            }
                            else {  // 중복된 아이디 존재 O
                                isCheckID = false
                                memberID = null
                                Toast.makeText(context, "Failed! Don't Use this ID.", Toast.LENGTH_LONG).show()
                            }
                        }, {
                            // 서버 통신 실패
                            Log.d(TAG, "Error : ${it.message}")
                        })
                    return@OnTouchListener true
                }
                false
            }
            false
        })

        // 닉네임 중복검사 버튼 클릭 이벤트
        nicknameCheckBtn.setOnTouchListener(View.OnTouchListener { _, event ->
            if(event?.action == MotionEvent.ACTION_DOWN) {
                editTextNickname.text?.let {
                    // HashMap으로 Json 변환.
                    val map = hashMapOf(
                        "member_nickname" to editTextNickname.text.toString()
                    )

                    // 닉네임 중복검사 서버 통신
                    service.resultCheckRepos("checkNickname", map)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({    // 받은 데이터를 사용하는 함수. 받은 데이터 : it
                            Log.d(TAG, "result : ${it.result}")
                            // 서버 통신 성공
                            if (it.result == 1) {   // 중복된 닉네임 존재 X
                                isCheckNicname = true
                                memberNickname = editTextNickname.text.toString()
                                Toast.makeText(context, "Success! Use this Nickname.", Toast.LENGTH_LONG).show()
                            }
                            else {  // 중복된 닉네임 존재 O
                                isCheckNicname = false
                                memberNickname = null
                                Toast.makeText(context, "Failed! Don't Use this Nickname.", Toast.LENGTH_LONG).show()
                            }
                        }, {
                            // 서버 통신 실패
                            Log.d(TAG, "Error : ${it.message}")
                        })
                    return@OnTouchListener true
                }
                false
            }
            false
        })

        // 회원가입 버튼 클릭 이벤트
        joinSuccessBtn.setOnTouchListener(View.OnTouchListener { _, event ->
            if(event?.action == MotionEvent.ACTION_DOWN) {
                // EditText Null Check
                if(isCheckID and isCheckNicname) {  // 아이디와 닉네임 중복 체크 여부
                    memberID?.let { memberNickname?.let { editTextPwd.text?.let { editTextPwdReCheck.text?.let {    // 아이디, 비밀번호, 닉네임 null 체크
                        if (editTextPwd.text.toString() == editTextPwdReCheck.text.toString()) {    // 비밀번호와 비밀번호 확인이 같은지 체크
                            if (isJoinCard) {
                                // TODO - 카드 정보를 담아 서버에 전송하는 코드 작성
                                // TODO - 카드 EditText 가져오기 + 예외 처리.
                                // TODO - 서버로 보낼 때 Date 타입은 어떻게 처리하는지 구현 및 테스트
                                // joinData = JoinData(memberID!!, editTextPwd.text.toString(), memberNickname!!, null)
                            }
                            else {
                                joinData = JoinData(memberID!!, editTextPwd.text.toString(), memberNickname!!)
                                // 회원가입 서비스 통신
                                service.resultJoinRepos(joinData!!)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe({    // 받은 데이터를 사용하는 함수. 받은 데이터 : it
                                        Log.d(TAG, "result : ${it.result}")
                                        // 서버 통신 성공
                                        if (it.result == 1) {   // 중복된 닉네임 존재 X
                                            Toast.makeText(context, "Join Success!", Toast.LENGTH_LONG).show()
                                            finish()
                                        }
                                        else {  // 중복된 닉네임 존재 O
                                            Toast.makeText(context, "Join Failed! Try again.", Toast.LENGTH_LONG).show()
                                        }
                                    }, {
                                        // 서버 통신 실패
                                        Log.d(TAG, "Error : ${it.message}")
                                    })
                            }
                        }
                        Toast.makeText(context, "비밀번호와 확인이 같지 않습니다.", Toast.LENGTH_LONG).show()
                    } } } }
                }

                return@OnTouchListener true
            }
            false
        })
    }

}
