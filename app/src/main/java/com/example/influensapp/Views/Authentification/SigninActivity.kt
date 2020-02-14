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
import kotlinx.android.synthetic.main.activity_signin.*

class SigninActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        auth = FirebaseAuth.getInstance()

        btn_confirm_signin.setOnClickListener {
            if (validate()){
                progressbar_signin.visibility = View.VISIBLE
                auth.createUserWithEmailAndPassword(et_mail_signin.text.toString(), et_pwd_signin.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e("signin", "createUserWithEmail:success")
                            progressbar_signin.visibility = View.GONE
                            val intent = Intent(this,PopularShowsActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e("signin", "createUserWithEmail:failure", task.exception)
                            progressbar_signin.visibility = View.GONE
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_LONG).show()
                        }
            }
        }

    }
        }

    fun validate() : Boolean{
        val email = et_mail_signin.text.toString().trim()
        val pwd = et_pwd_signin.text.toString().trim()
        val pwd2 = et_pwd2_signin.text.toString().trim()
        if (email.isEmpty()){
            et_mail_signin.error = "Email required"
            et_mail_signin.requestFocus()
            return false
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_mail_signin.error = "Email not valid"
            et_mail_signin.requestFocus()
            return false
        }
        if (pwd.isEmpty()){
            et_pwd_signin.error = "Password required"
            et_pwd_signin.requestFocus()
            return false
        }
        if (pwd.isEmpty()){
            et_pwd2_signin.error = "Password confirmation required"
            et_pwd2_signin.requestFocus()
            return false
        }
        if (pwd != pwd2){
            et_pwd2_signin.error = "Passwords mismatch"
            et_pwd2_signin.requestFocus()
            return false
        }
        return true
    }
}
