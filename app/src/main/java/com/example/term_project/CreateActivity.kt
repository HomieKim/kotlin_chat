package com.example.term_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.term_project.Model.ListModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CreateActivity : AppCompatActivity() {
    lateinit var creBtn : Button
    lateinit var chatTitle : EditText
    private lateinit var auth: FirebaseAuth
    lateinit var myUserName : String
    private val TAG = LoginActivity::class.java.simpleName

    lateinit var listBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        creBtn = findViewById<Button>(R.id.cre_btn)
        chatTitle = findViewById<EditText>(R.id.chat_title)
        auth = Firebase.auth
        listBtn = findViewById<Button>(R.id.list_btn)

        listBtn.setOnClickListener {
            val intent = Intent(this,ChatListActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("username", myUserName)
            startActivity(intent)
        }
        val myUid = auth.uid
        val db = Firebase.firestore
        title ="create Room!"
        // firestore의 users에서 uid 체크해서 username가져옴
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    if (myUid != null) {
                        if(myUid == document.get("uid")){
                            myUserName = document.get("username").toString()
                        }
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

        creBtn.setOnClickListener {
            // 방만들기 클릭하면 list db에 넣고 ChatListActivity로 넘어감
            db.collection("list")
                .add(ListModel(myUserName, chatTitle.text.toString(), myUid.toString()))
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    val intent = Intent(this,ChatListActivity::class.java)
                   // intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.putExtra("username", myUserName)
                    startActivity(intent)
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        }



    }
}