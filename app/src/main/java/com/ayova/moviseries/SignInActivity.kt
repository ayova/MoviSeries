package com.ayova.moviseries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignInActivity : AppCompatActivity() {

    private val TAG = "miappSignIn"
    lateinit var auth: FirebaseAuth
    private var userEmail = ""
    private var userPass = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // init auth
        auth = Firebase.auth

        // button listener for sign up instead
        sign_in_btn_signup.setOnClickListener { startActivity(Intent(this, SignUpActivity::class.java)) }

        // create user when sign up button clicked and credentials entered
        sign_in_btn_signin.setOnClickListener {
            userEmail = sign_in_et_email.text.toString()
            userPass = sign_in_et_pass.text.toString()
            if (!userEmail.isNullOrEmpty() && !userPass.isNullOrEmpty()){
                loginUser(userEmail, userPass) // check credential
            } else {
                sign_in_tv_error.visibility = LinearLayout.VISIBLE
                sign_in_tv_error.error = "Please enter both email and password!"
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(userSignIn: FirebaseUser?){
        if (userSignIn != null) {
            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        } else {
            sign_in_tv_error.visibility = LinearLayout.VISIBLE
            sign_in_tv_error.error = "Credentials didn't match, please try again!"
        }
    }
}
