package com.example.firebasedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton

class CreateNetPasswordScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_net_password_screen)

        findViewById<AppCompatButton>(R.id.saveNewPasswordBtn).setOnClickListener {
            var intent= Intent(this,LoginScreen::class.java);
            startActivity(intent);
        }
    }
}