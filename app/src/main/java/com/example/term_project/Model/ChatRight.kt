package com.example.term_project.Model

import com.example.term_project.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.chat_right.view.*

class ChatRight(val msg : String) : Item<GroupieViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.chat_right
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.right_message.text = msg
    }

}