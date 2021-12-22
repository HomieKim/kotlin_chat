package com.example.term_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.term_project.Model.ChatLeft
import com.example.term_project.Model.ChatModel
import com.example.term_project.Model.ChatRight
import com.example.term_project.Model.ListModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
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

        val database = Firebase.database
        val room_title = intent.getStringExtra("setTitle").toString() // 채팅방 제목
        val myUid = auth.uid.toString()    // 로그인한 사람 uid
        val getUid = intent.getStringExtra("setUid").toString() // 현재 채팅방 uid
        val getName = intent.getStringExtra("setName").toString() // 현재 채팅방 username
        val myUserName = intent.getStringExtra("user").toString() // 로그인한 사람 username
        val db = Firebase.firestore

        title = room_title

        val adapter = GroupAdapter<GroupieViewHolder>()


        // 채팅이 들어가면 넣음, db에서 불러옴
//        val db = Firebase.firestore
//        db.collection("message")
//            .orderBy("time")
//            .get()
//            .addOnSuccessListener { result->
//                for(document in result){
//                    Log.d(TAG, document.toString())
//                    val chkUid = document.get("myUid")
//                    val msg = document.get("message").toString()
//
//                    if(chkUid == myUid){
//                        adapter.add(ChatRight(msg))
//                    } else{
//                        adapter.add(ChatLeft(msg))
//                    }
//                }
//                recyclerView_chat.adapter = adapter
//            }

//        adapter.add(ChatLeft())
//        adapter.add(ChatRight())
//        adapter.add(ChatLeft())



        val myRef = database.getReference("message")
        val readRef = database.getReference("message").child(getUid)
        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val model = snapshot.getValue(ChatModel::class.java)
                val msg = model?.message.toString()
                val chkUid = model?.myUid.toString()
                val name = model?.who.toString()

                if(myUid == chkUid){
                    adapter.add(ChatRight(msg, name))
                }else{
                    adapter.add(ChatLeft(msg, name))
                }
//                val who = model?.who
//
//                if(who.equals("me")){
//                    adapter.add(ChatRight(msg, myUserName))
//                }else{
//                    adapter.add(ChatLeft(msg, getName))
//                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }


        }
        recyclerView_chat.adapter = adapter
        readRef.addChildEventListener(childEventListener)



        button.setOnClickListener {
            val message = edit.text.toString()
            edit.setText("")
            val chat = ChatModel(myUid, getUid, message, System.currentTimeMillis(), myUserName)
            myRef.child(getUid).push().setValue(chat)
            db.collection(myUserName+"_list").document(getUid).set(ListModel(getName,room_title,getUid))

//            val chat = ChatModel(myUid.toString(), getUid.toString(), message, System.currentTimeMillis(), "me")
//            myRef.child(myUid.toString()).child(getUid.toString()).push().setValue(chat)
//            // 받은 메세지
//            val chat_get = ChatModel(getUid.toString(), myUid.toString(), message, System.currentTimeMillis(), "other")
//            myRef.child(getUid.toString()).child(myUid.toString()).push().setValue(chat_get)

//            val message = edit.text.toString()
//            edit.setText("")
//            val chat = ChatModel(myUid.toString(), getUid.toString(), message, System.currentTimeMillis())
//
//            db.collection("message")
//                .add(chat)
//                .addOnSuccessListener {
//                    Log.d(TAG,"메세지 db 성공")
//                }
//                .addOnFailureListener {
//                    Log.e(TAG, "메세지 db 실패")
//                }
        }

    }
}