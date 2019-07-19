package apps.user.jaksimforever

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.activity_splash.*

/*
 Splash 화면 액티비티.
 GlideModule 사용
 - Kotlin
 - 이해원
 */
class SplashActivity : AppCompatActivity() {
    private var mHandler: Handler? = null
    private val SPLASH_TIME: Long = 4000
    // 다음 화면으로 넘어가기 위한 Runnable 변수.
    val mRunnable: Runnable = Runnable {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Glide에 스플래쉬 넣기
        Glide.with(this)
            .load(R.drawable.splash)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(splashView)

        // Handler 초기화
        mHandler = Handler()
        mHandler!!.postDelayed(mRunnable, SPLASH_TIME)  // !!이면 Non-Null

    }
}
