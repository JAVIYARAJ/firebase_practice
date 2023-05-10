package com.example.firebasedemo

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUpScreen : AppCompatActivity() {

    lateinit var firebaseDatabase: FirebaseDatabase;
    private lateinit var firebaseAuth: FirebaseAuth;

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_screen)


        val name = findViewById<EditText>(R.id.registerName);
        val email = findViewById<EditText>(R.id.registerEmail);
        val phoneNumber = findViewById<EditText>(R.id.registerNumber);
        val pwd = findViewById<EditText>(R.id.registerPassword);
        val cpwd = findViewById<EditText>(R.id.registerCPassword);
        val registerBtn = findViewById<AppCompatButton>(R.id.signUpBtn);


        registerBtn.setOnClickListener {
            val username = name.text.toString();
            val userEmail = email.text.toString();
            val userPwd = pwd.text.toString();
            val phone = phoneNumber.text.toString()
            val userCPwd = cpwd.text.toString();

            if (username.isNotEmpty() && userEmail.isNotEmpty() && userPwd.isNotEmpty() && userCPwd.isNotEmpty() && phone.isNotEmpty()) {
                if (userPwd == userCPwd) {

                    //create user account in firebase
                    firebaseAuth.createUserWithEmailAndPassword(userEmail, userPwd)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val id = it.result.user?.uid;
                                //save all info into firebase real time database
                                val databaseRef = firebaseDatabase.getReference("users");
                                databaseRef.child(id!!).setValue(
                                    User(
                                        id, username, userEmail, phone, userPwd
                                    )
                                );
                                navigateToHomeScreen();
                            } else {
                                Toast.makeText(
                                    applicationContext,
                                    "please try again",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(
                        applicationContext,
                        "password and confirm password not match!!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    applicationContext, "please enter all value!!", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun navigateToHomeScreen() {
        finish()
        startActivity(Intent(this, HomeScreen::class.java));
    }
}