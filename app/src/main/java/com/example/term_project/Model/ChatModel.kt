package com.example.term_project.Model

class ChatModel(val myUid : String, val otherUid : String, val message : String, val time : Long, val who : String) {
    constructor() : this("","","",0,"")
}