package com.example.term_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.term_project.Model.UserItem
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_chat_list.*

class ChatListActivity : AppCompatActivity() {

    val db = Firebase.firestore
    private  val TAG = ChatListActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)

        val adapter = GroupAdapter<GroupieViewHolder>()


        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    adapter.add(UserItem(document.get("username").toString()))
                    Log.d(TAG, document.get("username").toString())
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
                recyclerView_list.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }
}