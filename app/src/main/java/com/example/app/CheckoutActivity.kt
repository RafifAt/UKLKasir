package com.example.app

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import com.example.app.database.CafeDatabase
import com.example.app.database.DetailTransaksi
import com.example.app.database.Menu
import com.example.app.database.Transaksi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CheckoutActivity : AppCompatActivity() {
    lateinit var namaPelanggan: TextView
    lateinit var spinnerMeja: Spinner
    lateinit var dibayar: CheckBox
    lateinit var checkoutButton: Button

    lateinit var db: CafeDatabase

    var id_user: Int = 0
    var listIdMenu = arrayListOf<Int>()
    var listMenu = arrayListOf<Menu>()
    var addAgain: Boolean = false
    var id_transaksi: Int = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        namaPelanggan = findViewById(R.id.namaPelanggan)
        spinnerMeja = findViewById(R.id.spinnerMeja)
        dibayar = findViewById(R.id.dibayar)
        checkoutButton = findViewById(R.id.checkOut)

        db = CafeDatabase.getInstance(applicationContext)
        id_user = intent.getIntExtra("id_user",0)
        id_transaksi = intent.getIntExtra("id_transaksi",0)
        listIdMenu = intent.getIntegerArrayListExtra("list")!!
        addAgain = intent.getBooleanExtra("addAgain",false)

        for (i in listIdMenu){
            var menu = db.cafeDao().getMenu(i)
            listMenu.add(menu)
        }

        setDataSpinner()
        var status = "Belum Bayar"

        if(addAgain == true){
            namaPelanggan.text = db.cafeDao().getTransaksi(id_transaksi).nama_pelanggan
            spinnerMeja.setSelection(db.cafeDao().getTransaksi(id_transaksi).id_meja - 1)
            if(db.cafeDao().getTransaksi(id_transaksi).status == "Dibayar"){
                dibayar.isChecked = true
            }
        }

        checkoutButton.setOnClickListener{
            if(addAgain == true){
                for (i in listMenu){
                    db.cafeDao().insertDetailTransaksi(DetailTransaksi(
                        null,
                        id_transaksi,
                        i.id_menu!!,
                        i.harga
                    ))
                }
                finish()
                finish()
                finish()
            } else {
                var formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy")
                var current = LocalDateTime.now().format(formatter)
                if(dibayar.isChecked){
                    status = "Dibayar"
                }
                var newTransaksi = Transaksi(null,
                    current,
                    id_user,
                    db.cafeDao().getIdMejaFromNama(spinnerMeja.selectedItem.toString()),
                    namaPelanggan.text.toString(),
                    status)
                db.cafeDao().insertTransaksi(newTransaksi)
                var idtransaksi = db.cafeDao().getIdTransaksiFromOther(
                    newTransaksi.tgl_transaksi,
                    newTransaksi.id_user,
                    newTransaksi.id_meja,
                    newTransaksi.nama_pelanggan,
                    newTransaksi.status)
                if(status != "Dibayar"){
                    var meja = db.cafeDao().getMeja(newTransaksi.id_meja)
                    db.cafeDao().updateMeja(meja.nomor_meja, meja.id_meja!!, true)
                }
                for (i in listMenu){
                    db.cafeDao().insertDetailTransaksi(DetailTransaksi(
                        null,
                        idtransaksi,
                        i.id_menu!!,
                        i.harga
                    ))
                }
                finish()
                finish()
            }
        }
    }

    private fun setDataSpinner(){
        val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, db.cafeDao().getAllNamaMeja())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMeja.adapter = adapter
    }
}