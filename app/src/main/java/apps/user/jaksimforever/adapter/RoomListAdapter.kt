package apps.user.jaksimforever.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import apps.user.jaksimforever.JakSimUtil.Companion.TAG
import apps.user.jaksimforever.R
import apps.user.jaksimforever.data.MainData
import apps.user.jaksimforever.data.RoomListData
import apps.user.jaksimforever.databinding.RoomListItemBinding
import kotlinx.android.synthetic.main.main_list_item.view.*

class RoomListAdapter(val context: Context, private val roomItemList: ArrayList<RoomListData>) : RecyclerView.Adapter<RoomListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.room_list_item, parent, false
            )
        )
    }

    override fun getItemCount(): Int = roomItemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val roomListData = roomItemList[position]
        holder.apply {
            bind(roomListData)
            itemView.tag = roomListData
        }
    }

    class ViewHolder (private val binding: RoomListItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(roomListData: RoomListData) {
            binding.apply { roomListItem = roomListData }
        }
    }
}