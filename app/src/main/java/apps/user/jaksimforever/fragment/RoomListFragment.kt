package apps.user.jaksimforever.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import apps.user.jaksimforever.JakSimUtil
import apps.user.jaksimforever.JakSimUtil.Companion.TAG
import apps.user.jaksimforever.R
import apps.user.jaksimforever.R.id.listTabLayout
import apps.user.jaksimforever.RoomListActivity
import apps.user.jaksimforever.adapter.RoomListAdapter
import apps.user.jaksimforever.data.RoomListData
import apps.user.jaksimforever.databinding.FragmentRoomListBinding
import apps.user.jaksimforever.utils.RoomListService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_room_list.*
import kotlinx.android.synthetic.main.fragment_room_list.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

@SuppressLint("ValidFragment")
class RoomListFragment() : Fragment() {
    var roomListAdapter: RoomListAdapter? = null
    var roomDataList = arrayListOf<RoomListData>()
    var roomFilterDataList = arrayListOf<RoomListData>()
    var indexStartNum: Int = 1
    private var isPageRefresh: Boolean = false
    private var isSearch: Boolean = false
    private var searchWord: String? = null
    private var searchResultCount: Int = 0
    lateinit var binding: FragmentRoomListBinding
    private var durationIndex: String = "default"

    companion object {
        fun newInstance(s: String): RoomListFragment {
//            val f = RoomListFragment()
//            val bdl = Bundle(1)
//            bdl.putString("index", s)
//            f.arguments = bdl
//            return f
            return RoomListFragment().apply {
                arguments = Bundle().apply { putString("index", s) }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        durationIndex = arguments?.getString("index", "default")!!
        Log.d(TAG, "durationIndex is $durationIndex")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Data Binding을 위한 ContentView 설정.
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_room_list, container, false)
//        durationIndex = arguments!!.getString("index", "default")
//        Log.d(TAG, "durationIndex is $durationIndex")
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        searchResultCount = resources.getInteger(R.integer.searchResultCount)
        Log.d(TAG, "onActivityCreated, type is $durationIndex")

        // 방 리스트 서비스 Create
        val service: RoomListService = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(JakSimUtil.SERVER_URL)
            .client(OkHttpClient())
            .build()
            .create(RoomListService::class.java)

        val map = hashMapOf("room_duration" to Integer.parseInt(durationIndex))   // 전송 타입에 맞게 변환
        // 방 리스트 정보를 얻기 위한 네트워킹 시작
        service.resultRoomListRepos(map)
            .subscribeOn(Schedulers.io())   // 데이터를 보내는 쓰레드.
            .observeOn(AndroidSchedulers.mainThread())  // 데이터를 받아서 사용하는 쓰레드.
            .subscribe({    // 받은 데이터를 사용하는 함수. 받은 데이터 : it
                // 서버 통신 성공
                // TODO : 클릭 시 _id값과 닉네임 보내는 방법
                it.let {
                    isSearch = false
                    roomDataList.clear()
                    roomDataList.addAll(it.filterNotNull())
                    setTabIndex(roomDataList)
                }
            }, {
                // 서버 통신 실패
                Log.d(TAG, "Error : ${it.message}")
            })

        // 현재 목표를 담은 RecyclerView 설정
        // 초기 생성 함수 안에 있기 때문에, when을 지나오면 우선 roomDataList에 값을 저장하기 때문.
        setRecyclerView(roomDataList, indexStartNum)

        // 탭 레이아웃 클릭 리스너
        listTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (isPageRefresh)
                    isPageRefresh = false
                else
                    changeTabView(tab!!.position)
            }

        })

        // 검색 이미지 버튼 클릭 리스너
        searchBtn.setOnTouchListener(View.OnTouchListener { _, event ->
            if(event?.action == MotionEvent.ACTION_DOWN) {
                searchEditText.text.let {
                    searchWord = searchEditText.text.toString()
                    isSearch = true
                    if (setSearchList()) {
                        setTabIndex(getArrayListFromFilter())
                        setRecyclerView(getArrayListFromFilter(), indexStartNum)
                    }
                }
                return@OnTouchListener true
            }
            false
        })
    }
//
//    fun sevenDaysFilter() {
//    }
//
//    fun oneMonthFilter() {
//    }
//
//    fun threeMonthFilter() {
//    }

    /*
    검색 필터 사용 시 검색어에 맞는 방 이름 또는 방장 이름만 가져다 저장하기
     */
    private fun setSearchList(): Boolean {
        if (isSearch) {
            searchWord.let { roomDataList.let {
                roomFilterDataList.clear()
                roomFilterDataList = roomDataList.filter {
                        s -> s.room_name.contains(searchWord!!) ||  s.room_manager_name.contains(searchWord!!)
                } as ArrayList<RoomListData>
                return true
//                val itemDataIterator = roomDataList.iterator()
//                while(itemDataIterator.hasNext()) {
//                    var roomListData: RoomListData = itemDataIterator.next()
//                    if ()
//                }
            }}
        }
        return false
    }

    /*
    현재 필터와 조건에 맞는 arraylist 반환
     */
    private fun getArrayListFromFilter(): ArrayList<RoomListData> {
        return if (isSearch) roomFilterDataList else roomDataList
    }

    /*
    사용자가 탭을 눌렀을 때 호출
     */
    private fun changeTabView(index: Int) {
        var tabIndexNum = 0
        var page: String? = null
        when (index) {
            0 -> tabIndexNum = -2 // ◀ 버튼 클릭 시
            listTabLayout.tabCount - 1 -> tabIndexNum = -1 // ▶ 버튼 클릭 시
            else -> {    // Tab Page Index로 불러오기
                page = listTabLayout.getTabAt(index)?.text?.toString()
                tabIndexNum = Integer.parseInt(page!!) // 해당 페이지 번호 가져오기
            }
        }
        val arrayList = getArrayListFromFilter()
        when (tabIndexNum) {
            -2 // ◀ 버튼 클릭 시
            -> showNextPage(arrayList, false)
            -1 // ▶ 버튼 클릭 시
            -> showNextPage(arrayList, true)
            else -> {
                setRecyclerView(arrayList, tabIndexNum)
            }
        }
    }

    /*
    현재 페이지에 해당하는 값 선정
     */
    private fun getCurrenPageList(arrayList: ArrayList<RoomListData>, index: Int) : ArrayList<RoomListData>{
        val start = (index - 1) * searchResultCount
        val end: Int
        // 만약 리스트로 출력하는 데이터가 dimens.xml에서 지정한 갯수보다 못채우는 경우
        end = if (start + searchResultCount >= arrayList.size)  arrayList.size
                else    start + searchResultCount
        val result = ArrayList<RoomListData>()   // adapter에 넣기 위한 임시 arrayList 생성
        for (i in start until end) {
            result.add(arrayList[i])
        }
        return result
    }

    private fun setRecyclerView(arrayList: ArrayList<RoomListData>, index: Int) {
        // 중간 결과 리스트 레이아웃 설정
        // 현재 목표를 담은 RecyclerView 설정
        if (arrayList.isEmpty())
            Toast.makeText(context, "리스트가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()

        var result: ArrayList<RoomListData> = getCurrenPageList(arrayList, index)
        roomListAdapter = RoomListAdapter(context!!, result)
        binding.roomListRecyclerView.adapter = roomListAdapter
        binding.roomListRecyclerView.adapter!!.notifyDataSetChanged()
        binding.roomListRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.roomListRecyclerView.setHasFixedSize(true)   // 아이템 추가 / 삭제 시 RecyclerView의 크기가 변경되면서 오류가 발생할 수 있기 때문에 고정 크기 설정 True로
    }
    /*
    해당 arraylist의 크기를 보고 전체 페이지 크기를 반환.
     */
    private fun getMaxTabSize(arrayList: ArrayList<RoomListData>): Int {
        val tabSize = arrayList.size
        var maxTabSize = 0
        if (tabSize % searchResultCount == 0)
        // ex. 40개면 5페이지.
            maxTabSize = tabSize / searchResultCount
        else
        // 39개여도 5페이지.
            maxTabSize = tabSize / searchResultCount + 1
        return maxTabSize
    }

    /*
    이전 또는 다음 페이지를 선택하여 새로운 페이지를 출력해야 하는 경우
     */
    private fun showNextPage(arrayList: ArrayList<RoomListData>, isUpPage: Boolean) {
        val maxSize = getMaxTabSize(arrayList)
        if (isUpPage) {  // 다음 5개의 페이지를 넘기라고 한다면
            if (indexStartNum + 5 > maxSize) { // 전체가 12페이지인데 11페이지에서 다음 페이지를 선택한 경우.
                Log.d(TAG, "Tab 페이지 설정하는데 다음 페이지가 없는 경우. ex) 최대 14페이지인데 11페이지에서 다음 페이지를 클릭하는 경우.")
                Toast.makeText(context, "해당 탭 내 최대 페이지입니다.", Toast.LENGTH_LONG).show()
                listTabLayout.getTabAt(listTabLayout.getTabCount() - 2)!!.select()
                return
            } else {
                indexStartNum += 5
                setTabIndex(arrayList) // Tab Page 변경
            }
        } else {  // 이전 5개의 페이지를 넘기라고 한다면
            if (indexStartNum - 5 < 0) { // 전체가 12페이지인데 1페이지에서 이전 페이지를 선택한 경우.
                Log.d(TAG, "이전 페이지가 존재하지 않습니다. 최소 페이지입니다.")
                Toast.makeText(context, "해당 탭 내 최소 페이지입니다.", Toast.LENGTH_LONG).show()
                listTabLayout.getTabAt(1)!!.select()
                return
            } else {
                indexStartNum -= 5
                setTabIndex(arrayList) // Tab Page 변경
            }
        }
    }

    /*
    Tab Layout에 Tab 생성하는 함수
     */
    private fun setTabIndex(arrayList: ArrayList<RoomListData>) {
        val maxTabSize = getMaxTabSize(arrayList)
        if (listTabLayout.tabCount != 0)
            listTabLayout.removeAllTabs() // tab item 제거

        isPageRefresh = true   // 페이지 갱신
        if (maxTabSize == 0) {
            listTabLayout.addTab(listTabLayout.newTab().setIcon(R.drawable.list_lefttab))
            listTabLayout.addTab(listTabLayout.newTab().setText("1"))
            listTabLayout.addTab(listTabLayout.newTab().setIcon(R.drawable.list_righttab))
            listTabLayout.getTabAt(1)!!.select()
            return
        }

        listTabLayout.addTab(listTabLayout.newTab().setIcon(R.drawable.list_lefttab))
        if (indexStartNum + 5 > maxTabSize) { // 전체가 12페이지인데 11페이지부터 시작하는 경우.
            for (i in indexStartNum..maxTabSize)
                listTabLayout.addTab(listTabLayout.newTab().setText(i.toString()))
        } else {
            for(i in 0..5)
                listTabLayout.addTab(listTabLayout.newTab().setText((indexStartNum + i).toString()))
        }
        listTabLayout.addTab(listTabLayout.newTab().setIcon(R.drawable.list_righttab))
        // 첫 번째 선택하기
        listTabLayout.getTabAt(1)?.select()
    }
}