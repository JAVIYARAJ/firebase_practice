package com.example.firebasedemo

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeScreen : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseDatabase: FirebaseDatabase;

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseDatabase = FirebaseDatabase.getInstance();

        firebaseAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_home_screen)

        val username = findViewById<TextView>(R.id.username)
        val email = findViewById<TextView>(R.id.userEmail)
        val logout = findViewById<Button>(R.id.logout);

        email.text = firebaseAuth.currentUser!!.email;
        username.text = firebaseAuth.currentUser!!.displayName;

        val reference=firebaseDatabase.getReference("users")
        reference.child(firebaseAuth.currentUser!!.uid).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        logout.setOnClickListener {
            firebaseAuth.signOut();
            finish()
            startActivity(Intent(this, LoginScreen::class.java));
        }

    }
}