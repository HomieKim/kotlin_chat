package com.example.term_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var loginBtn : Button
    lateinit var joinBtn : Button
    lateinit var editEmail : EditText
    lateinit var editUsername : EditText
    lateinit var editPw : EditText
    private lateinit var auth: FirebaseAuth

    private val TAG :String =MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginBtn = findViewById<Button>(R.id.login)
        joinBtn = findViewById<Button>(R.id.joinBtn)
        editEmail = findViewById<EditText>(R.id.editEmail)
        editUsername = findViewById<EditText>(R.id.editUsername)
        editPw = findViewById<EditText>(R.id.editPw)
        auth = Firebase.auth

        joinBtn.setOnClickListener {
            val email =  editEmail.text.toString()
            val pw = editPw.text.toString()
            auth.createUserWithEmailAndPassword(email, pw)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG,"성공")
                        val intent = Intent(this,LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    } else {
                        Log.d(TAG,"실패")
                    }
                }
        }



        loginBtn.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }
}