package com.example.term_project.Model

import com.example.term_project.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.message_list.view.*


class UserItem(val name:String, val uid: String, val title : String) : Item<GroupieViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.message_list
    }
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.name.text= name
        viewHolder.itemView.title.text = title

    }
}
