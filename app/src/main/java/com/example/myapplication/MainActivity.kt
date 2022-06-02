package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    val text = "Вход выполнен"
    val text_if_no = "Введены неправильные данные"
    val duration =Toast.LENGTH_SHORT
    val login = "Tamara"
    val password = "Tamara"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val editLogin: EditText = findViewById(R.id.login)
        val editPassword: EditText = findViewById(R.id.password)
        val logInButton: Button = findViewById(R.id.button_logIn)

        logInButton.setOnClickListener {
            if(!editLogin.text.equals(login) && editPassword.text.equals(password)){
                Toast.makeText(applicationContext,text_if_no,duration).show()
            }
            else{
                Toast.makeText(applicationContext,text,duration).show()
                val intent = Intent(this, Note::class.java)
                startActivity(intent)
            }

        }
    }
}