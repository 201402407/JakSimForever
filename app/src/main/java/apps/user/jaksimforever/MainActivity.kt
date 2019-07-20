package apps.user.jaksimforever

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import apps.user.jaksimforever.adapter.MainAdapter
import apps.user.jaksimforever.data.MainData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FontActivity() {
    var mainAdapter: MainAdapter? = null
    var context: Context? = null
    var currentAimList = arrayListOf<MainData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = applicationContext

        // 현재 목표를 담은 RecyclerView 설정
        mainAdapter = MainAdapter(context!!, currentAimList)
        mainAimRecyclerView.adapter = mainAdapter
        mainAimRecyclerView.layoutManager = LinearLayoutManager(this)
        mainAimRecyclerView.setHasFixedSize(true)   // 테스트 필요
    }
}
