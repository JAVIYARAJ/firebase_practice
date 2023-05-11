package com.example.firebasedemo

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class ForgotPasswordScreen : AppCompatActivity() {

    private lateinit var mPhoneCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks;
    lateinit var mAuth: FirebaseAuth;
    lateinit var verificationId: String;
    lateinit var userPhoneNumber: EditText
    lateinit var forgotScreenProgressBar: ProgressBar;

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password_screen)

        mAuth = FirebaseAuth.getInstance();
        userPhoneNumber = findViewById(R.id.userPhoneNumber);
        forgotScreenProgressBar = findViewById(R.id.forgotScreenProgressBar);


        //send otp
        findViewById<AppCompatButton>(R.id.sendOtpBtn).setOnClickListener {
            sendOtp()
        }

    }

    private fun sendOtp() {
        forgotScreenProgressBar.visibility = View.VISIBLE;
        //define all required callback for phone auth
        mPhoneCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d("Practice", "verification completed");
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Log.d("Practice", p0.message.toString());
            }

            override fun onCodeSent(
                verificationToken: String,
                resendToken: PhoneAuthProvider.ForceResendingToken
            ) {
                verificationId = verificationToken;
                val intent =
                    Intent(this@ForgotPasswordScreen, OtpVerificationScreen::class.java);
                intent.putExtra("verificationId", verificationToken);
                intent.putExtra("phone", userPhoneNumber.text.trim().toString());
                forgotScreenProgressBar.visibility = View.GONE;
                startActivity(intent);
            }

        }

        //create phone auth option that contain all required info about phone auth provider.

        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(
                "+91${
                    userPhoneNumber.text.trim().toString()
                }"
            ) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(mPhoneCallback) // OnVerificationStateChangedCallbacks
            .build()

        //verify phone number(by phone provider reCAPTCHA verification) which you entered
        PhoneAuthProvider.verifyPhoneNumber(options)

    }
}