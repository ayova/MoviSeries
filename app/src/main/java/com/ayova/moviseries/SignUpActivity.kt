package com.ayova.moviseries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private val TAG = "miappSignUp"
    lateinit var auth: FirebaseAuth
    private var userEmail = ""
    private var userPass = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // init auth
        auth = Firebase.auth

        // button listener for signing in instead
        sign_up_btn_signin.setOnClickListener { startActivity(Intent(this, SignInActivity::class.java)) }

        // create user when sign up button clicked and credentials entered
        sign_up_btn_signup.setOnClickListener {
            userEmail = sign_up_et_email.text.toString()
            userPass = sign_up_et_pass.text.toString()
            if (!userEmail.isNullOrEmpty() && !userPass.isNullOrEmpty()) {
            createUser(userEmail, userPass)
            } else {
                sign_up_et_email.error = "Enter a valid email"
                sign_up_et_pass.error = "And a password, at least 6 characters long"
            }
        }
    }

    private fun createUser(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(userSignedUp: FirebaseUser?) {
        if (userSignedUp != null) {
            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        } else {
            sign_up_et_pass.error = "Password must be at least 6 characters long"
        }
    }
}
