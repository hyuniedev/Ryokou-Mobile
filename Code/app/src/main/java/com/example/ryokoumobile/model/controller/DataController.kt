package com.example.ryokoumobile.model.controller

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.example.ryokoumobile.model.entity.Tour
import com.example.ryokoumobile.model.entity.User

object DataController {
    var user: User? = null
    var lsTour = mutableStateListOf<Tour>()

    fun LoadDataTours() {
        FirebaseController.firestore.collection("tours")
            .get()
            .addOnSuccessListener { result ->
                for (doc in result) {
                    val tour = doc.toObject(Tour::class.java)
                    tour.id = doc.id
                    lsTour.add(tour)
                }
            }
            .addOnFailureListener { e -> Log.e("HyuNie", "Error on load tour: " + e.message) }
    }
}