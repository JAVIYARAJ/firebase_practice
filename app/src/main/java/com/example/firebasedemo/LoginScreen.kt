package com.example.firebasedemo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.MediaSync.Callback
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginScreen : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth;

    @SuppressLint("WrongViewCast", "MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        firebaseAuth = FirebaseAuth.getInstance();

        val userLoginBtn = findViewById<AppCompatButton>(R.id.userLoginBtn);
        val email = findViewById<EditText>(R.id.userEmail);
        val password = findViewById<EditText>(R.id.userPassword);
        val loginProgressBar = findViewById<ProgressBar>(R.id.loginProgressBar);
        val loginForgotBtn=findViewById<TextView>(R.id.loginForgotBtn);

        loginForgotBtn.setOnClickListener {
            startActivity(Intent(this,ForgotPasswordScreen::class.java))
        }

        val googleRegister = findViewById<ImageView>(R.id.googleRegister);
        val facebookRegister = findViewById<ImageView>(R.id.facebookRegister);

        if (firebaseAuth.currentUser != null) {
            navigateToHomeScreen()
        }

        val loginSignupBtn = findViewById<TextView>(R.id.loginSignupBtn);
        loginSignupBtn.setOnClickListener {
            startActivity(Intent(this, SignUpScreen::class.java));
        }

        //simple email and password sign in
        userLoginBtn.setOnClickListener {
            loginProgressBar.visibility = View.VISIBLE;
            val userEmail = email.text.toString();
            val userPassword = password.text.toString();

            if (userEmail.isNotEmpty() && userPassword.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            loginProgressBar.visibility = View.GONE;
                            navigateToHomeScreen();
                        } else {
                            loginProgressBar.visibility = View.GONE;
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
                    "please enter email or password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        //google firebase auth
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_client_id))
                .requestEmail()
                .build();

        //create google sign client
        val gsc = GoogleSignIn.getClient(this, gso);

        //create contract
        val contract = object : ActivityResultContract<Intent, Intent>() {
            override fun createIntent(context: Context, input: Intent): Intent {
                return input;
            }

            override fun parseResult(resultCode: Int, intent: Intent?): Intent {
                Log.d("Practice", intent?.data.toString())
                return intent!!;
            }
        }

        //create launcher
        val launcher =
            registerForActivityResult(
                contract
            ) { intent ->
                val task = GoogleSignIn.getSignedInAccountFromIntent(intent);

                if (task.isSuccessful) {
                    //get google account info google sign in intent
                    val account = task.getResult(ApiException::class.java);

                    //get credential from google in account info
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null);

                    firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful) {
                            loginProgressBar.visibility = View.GONE;
                            navigateToHomeScreen();
                        } else {
                            loginProgressBar.visibility = View.GONE;
                            Toast.makeText(
                                applicationContext,
                                "Please try again",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    loginProgressBar.visibility = View.GONE;
                    Toast.makeText(applicationContext, "please try again", Toast.LENGTH_SHORT)
                        .show()
                }


            }

        googleRegister.setOnClickListener {

            loginProgressBar.visibility = View.VISIBLE;
            //create google sign in option

            //launch launcher
            launcher.launch(gsc.signInIntent);
        }

        //facebook sign in
        facebookRegister.setOnClickListener {

        }
    }

    private fun navigateToHomeScreen() {
        finish()
        startActivity(Intent(this, HomeScreen::class.java));
    }
}