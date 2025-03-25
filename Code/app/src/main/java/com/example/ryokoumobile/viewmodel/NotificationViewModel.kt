package com.example.ryokoumobile.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.controller.FirebaseController
import com.example.ryokoumobile.model.entity.Company
import com.example.ryokoumobile.model.entity.Notification
import com.example.ryokoumobile.model.entity.Tour
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

class NotificationViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(listOf<Notification>())
    var uiState = _uiState.asStateFlow()

    fun loadInitData() {
        val ls = mutableListOf<Notification>()
        try {
            FirebaseController.firestore
                .collection("notification")
                .whereEqualTo("toId", DataController.user.value!!.id)
                .get()
                .addOnSuccessListener { task ->
                    try {
                        for (i in task) {
                            val data = i.toObject(Notification::class.java)
                            for (ntf in ls)
                                if (ntf.content == data.content)
                                    ls.remove(ntf)
                            ls.add(data)
                        }
                        _uiState.update { ls }
                    } catch (e: Exception) {
                        Log.e("HyuNie", "${e.message}")
                    }
                }
                .addOnFailureListener {
                    Log.e("HyuNie", "${it.message}")
                }
        } catch (e: Exception) {
            Log.e("HyuNie", "${e.message}")
        }
    }

    fun updateIsRead() {
        val ls = uiState.value
        for (i in ls) {
            i.seen = true
            FirebaseController.firestore.collection("notification")
                .document(i.id).set(i)
        }
    }

    fun revertToLocalDate(date: Date): LocalDate {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
        return LocalDate.parse(dateFormatter.format(date))
    }

    fun notifyStartTour(tour: Tour) {
        var notification = Notification(
            fromId = "admin",
            toId = DataController.user.value!!.id!!,
            content = "Xin chào ${DataController.user.value!!.fullName}. Tour ``${tour.name}`` của bạn bắt đầu sau 1 ngày nữa, bạn đã sẵn sàng chưa? Nhớ đến điểm tập hợp của tour tại '${tour.gatheringPlace}' đúng giờ nhé! Nếu có vấn đề vui lòng liên hệ qua hotline: 0778421699"
        )
        addNewNotification(notification)
    }

    fun notifyEndTour(tour: Tour) {
        var notification = Notification(
            fromId = "admin",
            toId = DataController.user.value!!.id!!,
            content = "Xin chào ${DataController.user.value!!.fullName}. Tour ``${tour.name}`` của bạn đã kết thúc, bạn có cảm nghĩ như nào về tour? Hãy để lại đánh giá của bạn trong mục comment của tour nhé! Cảm ơn bạn đã lựa chọn chúng tôi. Nếu có vấn đề về tour cần phản ánh vui lòng liên hệ qua hotline: 0778421699"
        )
        addNewNotification(notification)
    }

    fun notifyDeletedTour(id: String) {
        FirebaseController.firestore.collection("tours")
            .document(id).get().addOnSuccessListener { result ->
                val tour = result.toObject(Tour::class.java)
                var notification = Notification(
                    fromId = "admin",
                    toId = DataController.user.value!!.id!!,
                    content = "Xin chào ${DataController.user.value!!.fullName}. Tour ``${tour!!.name}`` đã bị xóa. Xin lỗi vì sự bất tiện này."
                )
                addNewNotification(notification)
            }
    }

    private fun addNewNotification(notification: Notification) {
        FirebaseController.firestore.collection("notification")
            .whereEqualTo("content", notification.content)
            .get()
            .addOnSuccessListener { doc ->
                if (doc.isEmpty) {
                    var newNoti = FirebaseController.firestore.collection("notification")
                        .document()
                    notification.id = newNoti.id
                    FirebaseController.firestore.collection("notification")
                        .document(notification.id).set(notification)
                    _uiState.update { it + notification }
                }
            }
    }

    suspend fun getCompanyOfNotification(notification: Notification): String {
        var result = "admin"
        val data =
            FirebaseController.firestore.collection("companys").document(notification.fromId).get()
                .await()
        if (data.exists()) {
            val kq = data.toObject(Company::class.java)
            result = kq?.name ?: "admin"
        }
        return result
    }
}