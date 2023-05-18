package com.example.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.app.database.CafeDatabase
import com.example.app.database.User

class MainActivity : AppCompatActivity() {
    lateinit var editEmail: EditText
    lateinit var editPassword: EditText
    lateinit var loginButton: Button
    lateinit var register: TextView

    lateinit var db: CafeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        loginButton.setOnClickListener{
            if(editEmail.text.toString().isNotEmpty() && editPassword.text.toString().isNotEmpty()){
                var list: List<User> = db.cafeDao().login(editEmail.text.toString(), editPassword.text.toString())
                if(list.size > 0){
                    val moveIntent = Intent(this@MainActivity, HomeActivity::class.java)
                    val name = list[0].nama
                    val role = list[0].role
                    moveIntent.putExtra("name", name)
                    moveIntent.putExtra("role", role)
                    startActivity(moveIntent)
                }
                else{
                    Toast.makeText(applicationContext, "User not found", Toast.LENGTH_SHORT).show()
                }
            }
        }
        register.setOnClickListener{
            val moveIntent = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(moveIntent)
        }
    }

    fun init(){
        editEmail = findViewById(R.id.textEmail)
        editPassword = findViewById(R.id.textPassword)
        loginButton = findViewById(R.id.btnSubmit)
        register = findViewById(R.id.textregister)

        db = CafeDatabase.getInstance(applicationContext)
    }
}