package apps.user.jaksimforever

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
import apps.user.jaksimforever.JakSimUtil.Companion.TAG
import apps.user.jaksimforever.JakSimUtil.Companion.SERVER_URL
import apps.user.jaksimforever.JakSimUtil.Companion.checkEditText
import apps.user.jaksimforever.data.RoomData
import apps.user.jaksimforever.utils.AddRoomService
import apps.user.jaksimforever.utils.LoginService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add_room.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class AddRoomActivity : AppCompatActivity() {
    var nickname: String? = null
    var duration: Int = -1   // -1: default  0: 7day  1: 1month  2: 3month
    var money: Int = 0
    var userNum: Int = -1    // -1 : default  3 : 3people  4 : 4people  5 : 5people  6 : 6people
    var goalName: String? = null
    var goalDescription: String? = null
    private var service: AddRoomService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_room)
        nickname = intent?.getStringExtra("nickname")!!

        // 로그인 서비스 Create
        service = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .client(OkHttpClient())
                .build()
                .create(AddRoomService::class.java)

        // seekBar의 상태를 변경할 때
        moneySeekBar.setOnSeekBarChangeListener (object : SeekBar.OnSeekBarChangeListener {
            // Seek의 상태가 변경되었을 때 실행될 사항
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // SeekBar의 값을 따라다니면서 화면에 표시
                val padding = seekBar.paddingLeft + seekBar.paddingRight
                val sPos = seekBar.left + seekBar.paddingLeft
                val xPos = (seekBar.width - padding) * seekBar.progress / seekBar.max + sPos - textViewMoney.width / 2
                money = seekBar.progress * 1000  // 값 설정
                textViewMoney.x = xPos.toFloat()
                if (money == 0)
                    textViewMoney.text = "free"
                else
                    textViewMoney.text = money.toString() + "원"
            }

            // SeekBar의 움직임이 시작되었을 때 실행
            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            // SeekBar의 움직임이 멈춘다면 실행될 사항
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                if (money == 0) // 초기 값 설정하기 위해
                    textViewMoney.text = "free"
            }
        })

        sevenDayBtn.setOnTouchListener(touchListener)
        oneMonthBtn.setOnTouchListener(touchListener)
        threeMonthBtn.setOnTouchListener(touchListener)
        threePeopleBtn.setOnTouchListener(touchListener)
        fourPeopleBtn.setOnTouchListener(touchListener)
        fivePeopleBtn.setOnTouchListener(touchListener)
        sixPeopleBtn.setOnTouchListener(touchListener)
        addRoomBtn.setOnTouchListener(touchListener)
    }

    /*
    방 생성을 위한 정보 제대로 입력했는지 확인
    우선 null check만
     */
    private fun checkRoomInfo(): Boolean {
        if (checkEditText(editTextGoalName) && checkEditText(editTextGoalDescription)) {
            goalName = editTextGoalName.text.toString()
            goalDescription = editTextGoalDescription.text.toString()
            if (duration != 0 && userNum != 0)
                return true
        }
        return false
    }

    // 각 ImageView 클릭에 따른 리스너 묶음 변수 선언
    private val touchListener = View.OnTouchListener { v, event ->
        val image = v as ImageView
        when (v.id) {
            // 기간 버튼
            R.id.sevenDayBtn -> if (event.action == MotionEvent.ACTION_DOWN) { // 클릭 O
                duration = 0
                image.setImageResource(R.drawable.addroom_sevendaybtnclick)
                oneMonthBtn.setImageResource(R.drawable.addroom_onemonthbtn)
                threeMonthBtn.setImageResource(R.drawable.addroom_threemonthbtn)
                //if()
            } else if (event.action == MotionEvent.ACTION_UP) {    // 클릭 X
                image.setImageResource(R.drawable.addroom_sevendaybtn)
            }

            R.id.oneMonthBtn -> if (event.action == MotionEvent.ACTION_DOWN) { // 클릭 O
                duration = 1
                image.setImageResource(R.drawable.addroom_onemonthbtnclick)
                sevenDayBtn.setImageResource(R.drawable.addroom_sevendaybtn)
                threeMonthBtn.setImageResource(R.drawable.addroom_threemonthbtn)
            } else if (event.action == MotionEvent.ACTION_UP) {    // 클릭 X
                image.setImageResource(R.drawable.addroom_onemonthbtn)
            }

            R.id.threeMonthBtn -> if (event.action == MotionEvent.ACTION_DOWN) { // 클릭 O
                duration = 2
                image.setImageResource(R.drawable.addroom_threemonthbtnclick)
                oneMonthBtn.setImageResource(R.drawable.addroom_onemonthbtn)
                sevenDayBtn.setImageResource(R.drawable.addroom_sevendaybtn)
            } else if (event.action == MotionEvent.ACTION_UP) {    // 클릭 X
                image.setImageResource(R.drawable.addroom_threemonthbtn)
            }

            // 인원 수 버튼
            R.id.threePeopleBtn -> if (event.action == MotionEvent.ACTION_DOWN) { // 클릭 O
                userNum = 3
                image.setImageResource(R.drawable.addroom_threebtnclick)
                fourPeopleBtn.setImageResource(R.drawable.addroom_fourbtn)
                fivePeopleBtn.setImageResource(R.drawable.addroom_fivebtn)
                sixPeopleBtn.setImageResource(R.drawable.addroom_sixbtn)
            } else if (event.action == MotionEvent.ACTION_UP) {    // 클릭 X
                image.setImageResource(R.drawable.addroom_threebtn)
            }

            R.id.fourPeopleBtn -> if (event.action == MotionEvent.ACTION_DOWN) { // 클릭 O
                userNum = 4
                image.setImageResource(R.drawable.addroom_fourbtnclick)
                threePeopleBtn.setImageResource(R.drawable.addroom_threebtn)
                fivePeopleBtn.setImageResource(R.drawable.addroom_fivebtn)
                sixPeopleBtn.setImageResource(R.drawable.addroom_sixbtn)
            } else if (event.action == MotionEvent.ACTION_UP) {    // 클릭 X
                image.setImageResource(R.drawable.addroom_fourbtn)
            }

            R.id.fivePeopleBtn -> if (event.action == MotionEvent.ACTION_DOWN) { // 클릭 O
                userNum = 5
                image.setImageResource(R.drawable.addroom_fivebtnclick)
                threePeopleBtn.setImageResource(R.drawable.addroom_threebtn)
                fourPeopleBtn.setImageResource(R.drawable.addroom_fourbtn)
                sixPeopleBtn.setImageResource(R.drawable.addroom_sixbtn)
            } else if (event.action == MotionEvent.ACTION_UP) {    // 클릭 X
                image.setImageResource(R.drawable.addroom_fivebtn)
            }

            R.id.sixPeopleBtn -> if (event.action == MotionEvent.ACTION_DOWN) { // 클릭 O
                userNum = 6
                image.setImageResource(R.drawable.addroom_sixbtnclick)
                threePeopleBtn.setImageResource(R.drawable.addroom_threebtn)
                fourPeopleBtn.setImageResource(R.drawable.addroom_fourbtn)
                fivePeopleBtn.setImageResource(R.drawable.addroom_fivebtn)
            } else if (event.action == MotionEvent.ACTION_UP) {    // 클릭 X
                image.setImageResource(R.drawable.addroom_sixbtn)
            }

            // 방 추가 버튼
            R.id.addRoomBtn -> if (event.action == MotionEvent.ACTION_DOWN) { // 클릭 O
                if (checkRoomInfo()) {
                    // 방 추가 데이터
                    val roomData = RoomData(goalName, nickname, goalDescription, duration, money, userNum)
                    // 방 추가를 위한 서버 통신
                    service!!.resultAddRoomRepos(roomData)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({    // 받은 데이터를 사용하는 함수. 받은 데이터 : it
                            Log.d(TAG, "result : ${it.result}")
                            // 서버 통신 성공
                            if (it.result != 0) {   // 생성 성공
                                val intent = Intent(this, WaitingRoomActivity::class.java)
                                intent.putExtra("nickname", nickname)   // 현재 유저의 닉네임
                                intent.putExtra("room_id", it.result)   // 방의 ID값
                                startActivity(intent)
                                finish()
                            }
                            else {  // 생성 실패
                            }
                        }, {
                            // 서버 통신 실패
                            Log.d(TAG, "Error : ${it.message}")
                        })
                }
            }
        }
        false
    }
}
