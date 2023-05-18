package com.example.app.Adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.database.Meja

class MejaAdapter(var items: List<Meja>): RecyclerView.Adapter<MejaAdapter.ViewHolder>(){
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var namaMeja: TextView

        init{
            namaMeja = view.findViewById(R.id.namaMeja)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.meja, parent, false)
        return MejaAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.namaMeja.text = items[position].nomor_meja
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItem(position: Int): Meja {
        return items.get(position)
    }
}