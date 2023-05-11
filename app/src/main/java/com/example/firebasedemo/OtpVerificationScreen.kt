package com.example.firebasedemo

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.get
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class OtpVerificationScreen : AppCompatActivity() {

    lateinit var verificationId: String;
    lateinit var mAuth: FirebaseAuth;
    lateinit var verifyScreenProgressBar: ProgressBar;

    @SuppressLint("MissingInflatedId", "SetTextI18n")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verfication_screen)

        mAuth = FirebaseAuth.getInstance();

        val otp1 = findViewById<EditText>(R.id.otp1);
        val otp2 = findViewById<EditText>(R.id.otp2);
        val otp3 = findViewById<EditText>(R.id.otp3);
        val otp4 = findViewById<EditText>(R.id.otp4);
        val otp5 = findViewById<EditText>(R.id.otp5);
        val otp6 = findViewById<EditText>(R.id.otp6);
        verifyScreenProgressBar = findViewById(R.id.verifyScreenProgressBar);

        val phoneNumber = findViewById<TextView>(R.id.phoneNumber);
        phoneNumber.text = "+91 ${intent.getStringExtra("phone")}"
        verificationId = intent.getStringExtra("verificationId").toString()

        findViewById<AppCompatButton>(R.id.verifyOtpBtn).setOnClickListener {
            verifyScreenProgressBar.visibility = View.VISIBLE
            if (otp1.text.toString().isNotEmpty() && otp2.text.toString()
                    .isNotEmpty() && otp3.text.toString().isNotEmpty() && otp4.text.toString()
                    .isNotEmpty() && otp5.text.toString().isNotEmpty() && otp6.text.toString()
                    .isNotEmpty()
            ) {
                val code = "${otp1.text.trim().toString()}${otp2.text.trim().toString()}${
                    otp3.text.trim().toString()
                }${otp4.text.trim().toString()}${otp5.text.trim().toString()}${
                    otp6.text.trim().toString()
                }"

                val phoneAuthCredential = PhoneAuthProvider.getCredential(verificationId, code);

                mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener {
                    if (it.isSuccessful) {
                        verifyScreenProgressBar.visibility = View.GONE
                        navigateToHome();
                    } else {
                        Toast.makeText(applicationContext, "Please try again", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            } else {
                Toast.makeText(applicationContext, "Please enter otp", Toast.LENGTH_SHORT).show();
            }

        }




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

    private fun navigateToHome() {
        startActivity(Intent(this, HomeScreen::class.java))
    }
}