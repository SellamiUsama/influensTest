package com.example.influensapp.Views.Authentification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.influensapp.R
import com.example.influensapp.Views.PopularShows.PopularShowsActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        tv_redirect_login.setOnClickListener {
            startActivity(Intent(this,SigninActivity::class.java))
        }
        btn_confirm_login.setOnClickListener {
            if (validate()){
                progressbar_login.visibility = View.VISIBLE
                auth.signInWithEmailAndPassword(
                    et_mail_login.text.toString(),
                    et_pwd_login.text.toString()
                )
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e("login", "signInWithEmail:success")
                            progressbar_login.visibility = View.GONE
                            val intent = Intent(this,PopularShowsActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e("login", "signInWithEmail:failure", task.exception)
                            progressbar_login.visibility = View.GONE
                            Toast.makeText(
                                baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }

        }
    }

    private fun validate() : Boolean{
        val email = et_mail_login.text.toString().trim()
        val pwd = et_pwd_login.text.toString().trim()

        if (email.isEmpty()){
            et_mail_login.error = "Email required"
            et_mail_login.requestFocus()
            return false
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_mail_login.error = "Email not valid"
            et_mail_login.requestFocus()
            return false
        }
        if (pwd.isEmpty()){
            et_pwd_login.error = "Password required"
            et_pwd_login.requestFocus()
            return false
        }
    return true
    }
}
