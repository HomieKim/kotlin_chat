package com.example.term_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.term_project.Model.ChatLeft
import com.example.term_project.Model.ChatModel
import com.example.term_project.Model.ChatRight
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_chat_room.*


class ChatRoomActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var edit : EditText
    lateinit var button : Button
    private  val TAG = ChatRoomActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        edit = findViewById<EditText>(R.id.editMessage)
        button = findViewById<Button>(R.id.button)
        auth = Firebase.auth


        val myUid = auth.uid
        val getUid = intent.getStringExtra("setUid")
        val getName = intent.getStringExtra("setName")

        val adapter = GroupAdapter<GroupieViewHolder>()
        // 채팅이 들어가면 넣음
        adapter.add(ChatLeft())
        adapter.add(ChatRight())
        adapter.add(ChatLeft())
        recyclerView_chat.adapter = adapter

        val db = Firebase.firestore

        button.setOnClickListener {
            val message = edit.text.toString()
            edit.setText("")
            val chat = ChatModel(myUid.toString(), getUid.toString(), message)

            db.collection("message")
                .add(chat)
                .addOnSuccessListener {
                    Log.d(TAG,"메세지 db 성공")
                }
                .addOnFailureListener {
                    Log.e(TAG, "메세지 db 실패")
                }
        }

    }
}