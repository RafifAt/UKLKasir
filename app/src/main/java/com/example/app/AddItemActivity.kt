package com.example.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.app.database.CafeDatabase
import com.example.app.database.Menu

class AddItemActivity : AppCompatActivity() {
    lateinit var nama: EditText
    lateinit var harga: EditText
    lateinit var pilihTipe: Spinner
    lateinit var simpan: Button

    lateinit var db: CafeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        init()
        setDataSpinner()

        db = CafeDatabase.getInstance(applicationContext)

        simpan.setOnClickListener{
            if(nama.text.toString().isNotEmpty() && harga.text.toString().isNotEmpty() && pilihTipe.selectedItem.toString() != "Pilih tipe item"){
                val namaProduk = nama.text.toString()
                val hargaProduk = harga.text.toString().toInt()
                val tipeProduk = pilihTipe.selectedItem.toString()
                db.cafeDao().insertMenu(Menu(null, namaProduk, tipeProduk, hargaProduk))
                Toast.makeText(applicationContext, "Item berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    fun init(){
        nama = findViewById(R.id.namaProduk)
        harga = findViewById(R.id.hargaProduk)
        pilihTipe = findViewById(R.id.pilihTipe)
        simpan = findViewById(R.id.simpan)
    }
    fun setDataSpinner(){
        val adapter = ArrayAdapter.createFromResource(applicationContext, R.array.tipe, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        pilihTipe.adapter = adapter
    }
}