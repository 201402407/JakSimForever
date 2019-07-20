package apps.user.jaksimforever.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import apps.user.jaksimforever.JakSimUtil.Companion.TAG
import apps.user.jaksimforever.R
import apps.user.jaksimforever.data.MainData
import kotlinx.android.synthetic.main.main_list_item.view.*

class MainAdapter(val context: Context, val mainItemList: ArrayList<MainData>) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_list_item, parent, false)
        return MainViewHolder(view)
    }


    override fun getItemCount(): Int = mainItemList.size

    override fun onBindViewHolder(p0: MainViewHolder, p1: Int) {
       if (itemCount != 0)
           p0.bind(mainItemList[p1], context)
    }

    inner class MainViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val tvTitle = itemView?.tvGoalTitle
        val tvPercent = itemView?.tvPercent
        val percentProgressbar = itemView?.goalProgress
        var percent: Int? = 0

        fun bind(mainData: MainData, context: Context) {
            mainData?.goal_title?.let {
                tvTitle!!.text = mainData.goal_title // 목표 이름
                mainData.goal_success?.let { mainData.goal_all_count?.let {
                    percent = mainData.goal_success!! / (mainData.goal_all_count!! * 100)
                    tvPercent!!.text = percent.toString()   // 목표 퍼센트
                    // ProgressBar 수정
                    percentProgressbar!!.progress = percent!!

                } } ?: run {
                    Log.d(TAG, "이 ${mainData.goal_title}는 아직 진행이 되지 않은 목표입니다.")
                }
            } ?: run {
                Log.d(TAG, "현재 진행중인 목표가 없습니다.")
            }
        }
    }
}