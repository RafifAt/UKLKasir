package com.example.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.app.database.CafeDatabase
import com.example.app.database.User

class RegisterActivity : AppCompatActivity() {
    lateinit var editName: EditText
    lateinit var editEmail: EditText
    lateinit var editPassword: EditText
    lateinit var buttonSave: Button
    lateinit var pilihRole: Spinner

    lateinit var db: CafeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        init()
        setDataSpinner()
        buttonSave.setOnClickListener{
            if(editName.text.toString().isNotEmpty() && editEmail.text.toString().isNotEmpty() && editPassword.text.toString().isNotEmpty()){
                db.cafeDao().insertUser(
                    User(
                    null,
                    editName.text.toString(),
                    editEmail.text.toString(),
                    editPassword.text.toString(),
                    pilihRole.selectedItem.toString())
                )
                Toast.makeText(applicationContext, "Register successful", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    fun init(){
        editName = findViewById(R.id.edtName)
        editEmail = findViewById(R.id.edtEmail)
        editPassword = findViewById(R.id.edtPassword)
        buttonSave = findViewById(R.id.btnSave)
        pilihRole = findViewById(R.id.spinnerRole)

        db = CafeDatabase.getInstance(applicationContext)
    }


    private fun setDataSpinner(){
        val adapter = ArrayAdapter.createFromResource(applicationContext, R.array.roles, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        pilihRole.adapter = adapter
    }
}