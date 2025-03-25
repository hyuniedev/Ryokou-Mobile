package com.example.ryokoumobile.model.controller

import com.example.ryokoumobile.model.entity.Notification
import com.example.ryokoumobile.model.entity.TourBooked

enum class ETypeNotification {
    SEND_TO_ADMIN,
    SEND_TO_COMPANY
}

object NotificationController {
    fun SendNotification(type: ETypeNotification, bookedTour: TourBooked, text: String) {
        val doc = FirebaseController.firestore.collection("notification")
            .document()
        val noti = Notification(
            id = doc.id,
            fromId = DataController.user.value!!.id!!,
            toId = if (type == ETypeNotification.SEND_TO_ADMIN) "admin" else DataController.tourVM.getTourFromID(
                bookedTour.tourId
            )!!.company,
            content = "Người dùng (${DataController.user.value!!.fullName}) \n - ID: (${DataController.user.value!!.id}) \n Đã gửi ${if (type == ETypeNotification.SEND_TO_ADMIN) "báo cáo" else "yêu cầu hỗ trợ"} tour (${
                DataController.tourVM.getTourFromID(
                    bookedTour.tourId
                )!!.name
            } \n - ID: (${bookedTour.tourId}) \n - Nội dung: $text \n ${if (type == ETypeNotification.SEND_TO_COMPANY) " || - Vui lòng liên lạc sớm nhất đến khách hàng để hỗ trợ!" else " || - Vui lòng xem xét!"}"
        )
        FirebaseController.firestore.collection("notification")
            .document(doc.id)
            .set(noti)
    }
}