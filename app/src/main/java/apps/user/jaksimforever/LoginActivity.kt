package apps.user.jaksimforever

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.View
import android.widget.Toast
import apps.user.jaksimforever.JakSimUtil.Companion.SERVER_URL
import apps.user.jaksimforever.JakSimUtil.Companion.TAG
import apps.user.jaksimforever.data.LoginData
import apps.user.jaksimforever.utils.LoginService
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {
    var context: Context? = null
    var logindata: LoginData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 변수 초기화
        context = applicationContext

        Log.d(TAG, SERVER_URL)
        // 로그인 서비스 Create
        val service:LoginService = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(SERVER_URL)
            .client(OkHttpClient())
            .build()
            .create(LoginService::class.java)

        // 로그인 버튼 클릭 이벤트
        loginBtn.setOnTouchListener(View.OnTouchListener { _, event ->
            if(event?.action == ACTION_DOWN) {
                // EditText Null Check
                loginID.text?.let { loginPwd.text?.let {
                    logindata = LoginData(loginID.text.toString(), loginPwd.text.toString())
                } }

                // 로그인을 위한 네트워킹 시작
                service.resultRepos("login", logindata!!)
                    .subscribeOn(Schedulers.io())   // 데이터를 보내는 쓰레드.
                    .observeOn(AndroidSchedulers.mainThread())  // 데이터를 받아서 사용하는 쓰레드.
                    .subscribe({    // 받은 데이터를 사용하는 함수. 받은 데이터 : it
                        // 서버 통신 성공
                        if (it.result == 1) {   // 로그인 성공
                            Log.d(TAG, "nickname : ${it.nickname}")
                            Toast.makeText(context, "Success! your nickname : ${it.nickname}", Toast.LENGTH_LONG).show()
                        }
                        else {
                            Log.d(TAG, "Failed! failed reason : ${it.reason}")
                            Toast.makeText(context, "Failed! failed reason : ${it.reason}", Toast.LENGTH_LONG).show()
                        }
                    }, {
                        // 서버 통신 실패
                        Log.d(TAG, "Error : ${it.message}")
                    })

                return@OnTouchListener true
            }
            false
        })
    }

}
