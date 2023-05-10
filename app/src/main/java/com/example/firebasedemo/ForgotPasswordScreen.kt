package com.example.firebasedemo

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton

class ForgotPasswordScreen : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password_screen)

        findViewById<AppCompatButton>(R.id.sendOtpBtn).setOnClickListener {
            var intent= Intent(this, OtpVerificationScreen::class.java);
            startActivity(intent);
        }

    }
}