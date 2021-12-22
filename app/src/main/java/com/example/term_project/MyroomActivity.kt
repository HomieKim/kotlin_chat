package com.example.term_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.term_project.Model.ChatModel
import com.example.term_project.Model.UserItem
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_chat_list.*
import kotlinx.android.synthetic.main.activity_myroom.*

class MyroomActivity : AppCompatActivity() {

    private val TAG = LoginActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myroom)
        title="나의 채팅목록"
        val auth = Firebase.auth
        val myUid = auth.uid.toString()
        val myUsername = intent.getStringExtra("user").toString()
        val adapter = GroupAdapter<GroupieViewHolder>()
        val db = Firebase.firestore
        db.collection(myUsername+"_list")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    adapter.add(UserItem(document.get("username").toString(), document.get("uid").toString(), document.get("title").toString()))
                    Log.d(TAG, document.get("username").toString())
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
                //recyclerView_list.adapter = adapter
                recyclerView_list2.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
        adapter.setOnItemClickListener { item, view ->

            val uid = (item as UserItem).uid
            val name = (item as UserItem).name
            val title_ = (item as UserItem).title
            val intent = Intent(this, ChatRoomActivity::class.java)
            intent.putExtra("setUid", uid)
            intent.putExtra("setName", name)
            intent.putExtra("user", myUsername)
            intent.putExtra("setTitle", title_)
            startActivity(intent)
        }




    }
}