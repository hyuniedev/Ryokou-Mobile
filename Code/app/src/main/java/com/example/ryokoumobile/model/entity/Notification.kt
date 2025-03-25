package com.example.ryokoumobile.model.entity

import com.google.firebase.Timestamp

data class Notification(
    var id: String = "",
    var fromId: String = "",
    var toId: String = "",
    var content: String = "",
    var seen: Boolean = false,
    var timeSend: Timestamp = Timestamp.now()
)
