package com.example.term_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.term_project.Model.ChatLeft
import com.example.term_project.Model.ChatRight
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_chat_room.*


class ChatRoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        val adapter = GroupAdapter<GroupieViewHolder>()
        // 채팅이 들어가면 넣음
        adapter.add(ChatLeft())
        adapter.add(ChatRight())
        adapter.add(ChatLeft())
        recyclerView_chat.adapter = adapter

    }
}