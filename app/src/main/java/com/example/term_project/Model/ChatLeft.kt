package com.example.term_project.Model

import com.example.term_project.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.chat_left.view.*

class ChatLeft(val msg :String, val username : String) : Item<GroupieViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.chat_left
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.left_message.text = msg
        viewHolder.itemView.other_name.text = username
    }

}