package com.example.firebasedemo

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.get

class OtpVerificationScreen : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verfication_screen)

        findViewById<AppCompatButton>(R.id.verifyOtpBtn).setOnClickListener {
            var intent = Intent(this, CreateNetPasswordScreen::class.java);
            startActivity(intent);
        }

        var otpContainer = findViewById<LinearLayoutCompat>(R.id.otp_verification_container);
        var childCount = otpContainer.childCount;

        for (i in 0 until childCount) {
            var editText: EditText = findViewById<EditText>(otpContainer[i].id);
        }

        val otp1 = findViewById<EditText>(R.id.otp1);
        val otp2 = findViewById<EditText>(R.id.otp2);
        val otp3 = findViewById<EditText>(R.id.otp3);
        val otp4 = findViewById<EditText>(R.id.otp4);
        val otp5 = findViewById<EditText>(R.id.otp5);
        val otp6 = findViewById<EditText>(R.id.otp6);

        otp1.requestFocus();

        val otpEditTextBoxList = listOf<EditText>(
            otp1, otp2, otp3, otp4, otp5, otp6
        );

        otp1.addTextChangedListener(CustomTextWatcher(otpEditTextBoxList, otp1));
        otp2.addTextChangedListener(CustomTextWatcher(otpEditTextBoxList, otp2));
        otp3.addTextChangedListener(CustomTextWatcher(otpEditTextBoxList, otp3));
        otp4.addTextChangedListener(CustomTextWatcher(otpEditTextBoxList, otp4));
        otp5.addTextChangedListener(CustomTextWatcher(otpEditTextBoxList, otp5));
        otp6.addTextChangedListener(CustomTextWatcher(otpEditTextBoxList, otp6));

    }
}