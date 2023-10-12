package com.example.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.app.database.CafeDatabase

class EditTransaksiActivity : AppCompatActivity() {
    lateinit var inputNamaPelanggan: EditText
    lateinit var spinnerMeja: Spinner
    lateinit var simpanButton: Button
    lateinit var dibayar: CheckBox

    lateinit var db: CafeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_transaksi)

        inputNamaPelanggan = findViewById(R.id.namaPelanggan)
        simpanButton = findViewById(R.id.simpan)
        dibayar = findViewById(R.id.dibayar)

        db = CafeDatabase.getInstance(applicationContext)

        var id_transaksi: Int? = null
        id_transaksi = intent.getIntExtra("ID", 0)

        inputNamaPelanggan.setText(db.cafeDao().getTransaksi(id_transaksi).nama_pelanggan)
        if(db.cafeDao().getTransaksi(id_transaksi).status == "Dibayar"){
            dibayar.isChecked = true
        }

        simpanButton.setOnClickListener{
            var status = "Belum Dibayar"
            if(dibayar.isChecked){
                status = "Dibayar"
            }
            if(inputNamaPelanggan.text.toString().isNotEmpty()){
                db.cafeDao().updateTransaksi(
                    inputNamaPelanggan.text.toString(),
                    db.cafeDao().getTransaksi(id_transaksi).id_meja,
                    status,
                    id_transaksi
                )
                if(dibayar.isChecked){
                    var meja = db.cafeDao().getMeja(db.cafeDao().getTransaksi(id_transaksi).id_meja);
                    db.cafeDao().updateMeja(meja.nomor_meja, meja.id_meja!!, false)
                }
                finish()
            }
        }
    }

}