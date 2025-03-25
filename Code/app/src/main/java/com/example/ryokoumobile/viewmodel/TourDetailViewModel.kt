package com.example.ryokoumobile.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.ryokoumobile.R
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.controller.FirebaseController
import com.example.ryokoumobile.model.entity.Company
import com.example.ryokoumobile.model.entity.Rate
import com.example.ryokoumobile.model.entity.Schedule
import com.example.ryokoumobile.model.entity.Tour
import com.example.ryokoumobile.model.entity.TourBooked
import com.example.ryokoumobile.model.entity.User
import com.example.ryokoumobile.model.repository.Scenes
import com.example.ryokoumobile.model.uistate.TourDetailUiState
import com.example.ryokoumobile.view.components.MyShowToast
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TourDetailViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TourDetailUiState())
    val uiState = _uiState.asStateFlow()

    fun loadCompanyWithId(idCompany: String) {
        viewModelScope.launch {
            FirebaseController.firestore.collection("companys").document(idCompany).get()
                .addOnSuccessListener {
                    if (it != null && it.exists()) {
                        val company = it.toObject(Company::class.java)
                        if (company != null) {
                            _uiState.update { it.copy(companyName = company.name) }
                        }
                    }
                }
                .addOnFailureListener {
                    Log.e("HyuNie", "Bug on load company: ${it.message}")
                }
        }
    }

    fun sendComment(context: Context, tour: Tour, user: User) {
        viewModelScope.launch {
            if (_uiState.value.numRateOfUser == 0) {
                MyShowToast(context, "Please rate the number of stars")
                return@launch
            } else if (_uiState.value.userComment.isEmpty()) {
                MyShowToast(context, "Please enter your comment")
                return@launch
            } else {
                val rate = Rate(
                    tourId = tour.id,
                    comment = _uiState.value.userComment,
                    userId = user.id!!,
                    star = _uiState.value.numRateOfUser
                )
                val newRate = FirebaseController.firestore.collection("rates").document()
                rate.id = newRate.id
                DataController.tourVM.addRate(tour, rate)
                _uiState.update {
                    it.copy(
                        lsRate = listOf<Rate>() + rate + it.lsRate,
                        numRateOfUser = 0,
                        userComment = ""
                    )
                }
            }
        }
    }

    fun updateNumShowRate() {
        var newValue = _uiState.value.numShowRate + 2
        newValue =
            if (newValue < _uiState.value.lsRate.size) newValue else _uiState.value.lsRate.size
        _uiState.update { it.copy(numShowRate = newValue) }
    }

    fun updateUserComment(value: String) {
        _uiState.update { it.copy(userComment = value) }
    }

    fun updateNumRateOfUser(num: Int) {
        _uiState.update { it.copy(numRateOfUser = num) }
    }

    fun loadRateOfTour(tour: Tour) {
        viewModelScope.launch {
            _uiState.update { it.copy(lsRate = DataController.tourVM.getRate(tour)) }
        }
    }

    fun updateShowSchedule() {
        _uiState.update { it.copy(isShowSchedule = !it.isShowSchedule) }
    }

    fun updateSelectedDayOnSchedule(schedule: Schedule) {
        _uiState.update { it.copy(selectedDayOnSchedule = schedule) }
    }

    fun updateExtend() {
        _uiState.update { it.copy(isExtend = !it.isExtend) }
    }

    fun updateIsSelectTour() {
        _uiState.update { it.copy(isSelectTour = !it.isSelectTour) }
    }

    fun updateIsShowDatePicker() {
        _uiState.update { it.copy(isShowDatePicker = !it.isShowDatePicker) }
    }

    fun updateSelectedDate(time: Timestamp?) {
        _uiState.update { it.copy(dateSelected = time) }
    }

    fun updateNumTicket(context: Context, value: Int, maxValue: Int) {
        if (value < 1) {
            MyShowToast(context, "Số vé tối thiểu: 1")
            return
        }
        if (value > maxValue) {
            MyShowToast(context, "Số vé khả dụng: $maxValue")
            return
        }
        _uiState.update { it.copy(numTicket = value) }
    }

    fun getEndDay(duration: Int): Timestamp {
        val startDay = uiState.value.dateSelected?.toDate()
        val calendar = Calendar.getInstance()
        calendar.time = startDay!!
        calendar.add(Calendar.DAY_OF_MONTH, duration - 1)
        return Timestamp(calendar.time)
    }

    fun formatDate(date: Timestamp): String {
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return format.format(date.toDate())
    }

    fun thanhToan(context: Context, tour: Tour, navController: NavController) {
        if (uiState.value.dateSelected != null) {
            if (uiState.value.numTicket > 0) {
                if (tour.ticketLimit.numCurrentTicket + uiState.value.numTicket > tour.ticketLimit.numLimitTicket) {
                    MyShowToast(
                        context,
                        "Rất tiếc! Số vé khả dụng: ${tour.ticketLimit.numLimitTicket - tour.ticketLimit.numCurrentTicket}"
                    )
                    return
                }
                val bookedTour = TourBooked(
                    numPerson = uiState.value.numTicket,
                    startDay = uiState.value.dateSelected!!,
                    tourId = tour.id
                )
                if (checkValidBookedTour(bookedTour)) {
                    DataController.tourVM.navigationToTourPay(
                        navController,
                        tourBooked = bookedTour
                    )
                } else {
                    MyShowToast(context, "Thời gian trùng với 1 tour khác.")
                }
            } else {
                MyShowToast(context, "Vui lòng chọn số vé!")
            }
        } else {
            MyShowToast(context, "Vui lòng chọn ngày đi!")
        }
    }

    private fun checkValidBookedTour(booked: TourBooked): Boolean {
        for (bt in DataController.lsBookedTour) {
            if (!(booked.startDay.toDate().after(bt.getEndDay()) || booked.getEndDay()
                    .before(bt.startDay.toDate())) || booked.startDay.compareTo(bt.startDay) == 0 || booked.startDay.toDate()
                    .compareTo(bt.getEndDay()) == 0
            ) {
                return false
            }
        }
        return true
    }
}