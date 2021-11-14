package com.example.mydictionaryapp.view.historyScreen.RV

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mydictionaryapp.R
import com.example.mydictionaryapp.domain.database.HistoryEntity

class HistoryAdapter(
    private var data: List<HistoryEntity>
) : RecyclerView.Adapter<HistoryAdapter.RecyclerItemViewHolder>() {

    fun setData(data: List<HistoryEntity>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.activity_history_recycler_view_item,
                parent,
                false
            ) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: HistoryEntity) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.apply {
                    findViewById<TextView>(R.id.header_history_textview_recycler_item).text = data.word
                }
            }
        }
    }


}
