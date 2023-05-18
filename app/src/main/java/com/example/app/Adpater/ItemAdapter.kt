package com.example.app.Adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.database.Menu

class ItemAdapter(var items: List<Menu>):
    RecyclerView.Adapter<ItemAdapter.ViewHolder>(){

    lateinit var jumlahText: TextView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item
            , parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menu = items[position]
        holder.txtNamaMenu.text = items[position].nama_menu
        holder.txtHargaMenu.text = "Rp." + items[position].harga
    }

    override fun getItemCount(): Int {
        return items.size
    }

    public fun getItem(position: Int): Menu {
        return items.get(position)
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        var txtNamaMenu: TextView
        var txtHargaMenu: TextView


        init {
            txtNamaMenu = view.findViewById(R.id.namaItem)
            txtHargaMenu = view.findViewById(R.id.hargaItem)
        }
    }
}