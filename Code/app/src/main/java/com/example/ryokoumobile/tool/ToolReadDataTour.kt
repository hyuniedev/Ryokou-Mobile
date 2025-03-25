package com.example.ryokoumobile.tool

import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import com.example.ryokoumobile.model.controller.FirebaseController
import com.example.ryokoumobile.model.entity.Schedule
import com.example.ryokoumobile.model.entity.TicketLimit
import com.example.ryokoumobile.model.entity.ToDoOnDay
import com.example.ryokoumobile.model.entity.Tour
import com.example.ryokoumobile.viewmodel.MyTourViewModel
import com.example.ryokoumobile.viewmodel.TourViewModel
import kotlin.random.Random

fun TooleReadDataTour(context: Context) {
    val inputStream = context.assets.open("data.csv")
    var i = 0
    inputStream.bufferedReader().useLines { lines ->
        lines.forEach {
            i++
            val tour = Tour()
            val txt = it.split(",")
            tour.lsFile = listOf(txt[0])
            tour.company = txt[1]
            tour.name = txt[2]
            tour.city = txt[3].split(" ")
            tour.gatheringPlace = txt[4]
            tour.pointo = txt.subList(5, txt.size - 1).joinToString(",")
            tour.durations = Random.nextInt(1, 7)
            tour.maintainTime = Random.nextInt(20, 50)
            tour.cost = "${Random.nextInt(10, 50)}.000.000"
            tour.sale = Random.nextInt(10, 50)
            tour.freeService = Random.nextInt(0, 1) == 0
            tour.kisoku =
                "Tour cho phép miễn phí hủy tour trước ngày khởi hành và bao gồm dịch vụ ăn uống theo lịch trình đã được sắp xếp của đoàn."
            tour.ticketLimit = TicketLimit(numLimitTicket = Random.nextInt(10, 100))
            var lsSchedule = listOf<Schedule>()
            repeat(tour.durations) { day ->
                val schedule: Schedule = Schedule(day = "Ngày ${day + 1}")
                val lsToDoOnDay = mutableListOf<ToDoOnDay>()
                if (day == 0) {
                    val todo = listOf(
                        ToDoOnDay(
                            hour = "6",
                            minute = "00",
                            content = "Điểm danh tại địa điểm tập hợp",
                            location = tour.gatheringPlace
                        ),
                        ToDoOnDay(
                            hour = "6",
                            minute = "30",
                            content = "Phổ biến lịch trình, nội quy chuyến đi",
                            location = tour.gatheringPlace
                        ),
                        ToDoOnDay(
                            hour = "7",
                            minute = "00",
                            content = "Khởi hành đến điểm du lịch",
                            location = tour.city[0]
                        ),
                        ToDoOnDay(
                            hour = "8",
                            minute = "30",
                            content = "Dừng chân nghỉ ngơi, ăn sáng",
                            location = tour.city[0]
                        ),
                        ToDoOnDay(
                            hour = "10",
                            minute = "00",
                            content = "Tham quan điểm du lịch đầu tiên",
                            location = tour.city[0]
                        ),
                        ToDoOnDay(
                            hour = "12",
                            minute = "00",
                            content = "Ăn trưa tại nhà hàng địa phương",
                            location = tour.city[0]
                        ),
                        ToDoOnDay(
                            hour = "13",
                            minute = "30",
                            content = "Di chuyển đến khách sạn nghỉ trưa",
                            location = tour.city[0]
                        ),
                        ToDoOnDay(
                            hour = "15",
                            minute = "00",
                            content = "Tiếp tục tham quan tour",
                            location = tour.city[Random.nextInt(0, tour.city.size)]
                        ),
                        ToDoOnDay(
                            hour = "18",
                            minute = "00",
                            content = "Dùng bữa tối, khám phá ẩm thực địa phương",
                            location = tour.city[Random.nextInt(0, tour.city.size)]
                        ),
                        ToDoOnDay(
                            hour = "20",
                            minute = "00",
                            content = "Tham gia hoạt động giao lưu, vui chơi buổi tối",
                            location = tour.city[Random.nextInt(0, tour.city.size)]
                        )
                    )
                    lsToDoOnDay.addAll(todo)
                } else if (day == tour.durations - 1) {
                    val todo = mutableListOf<ToDoOnDay>(
                        ToDoOnDay(
                            hour = "6",
                            minute = "30",
                            content = "Thức dậy, vệ sinh cá nhân, chuẩn bị hành lý",
                            location = tour.city[Random.nextInt(0, tour.city.size)]
                        ),
                        ToDoOnDay(
                            hour = "7",
                            minute = "00",
                            content = "Ăn sáng tại khách sạn",
                            location = tour.city[Random.nextInt(0, tour.city.size)]
                        ),
                        ToDoOnDay(
                            hour = "8",
                            minute = "00",
                            content = "Kiểm tra lại hành lý, làm thủ tục trả phòng",
                            location = tour.city[Random.nextInt(0, tour.city.size)]
                        ),
                        ToDoOnDay(
                            hour = "9",
                            minute = "00",
                            content = "Tham quan điểm du lịch cuối cùng",
                            location = tour.city[tour.city.size - 1]
                        ),
                        ToDoOnDay(
                            hour = "11",
                            minute = "30",
                            content = "Dùng bữa trưa, nghỉ ngơi trước khi về",
                            location = tour.city[Random.nextInt(0, tour.city.size)]
                        ),
                        ToDoOnDay(
                            hour = "13",
                            minute = "00",
                            content = "Mua sắm quà lưu niệm, đặc sản địa phương",
                            location = tour.city[Random.nextInt(0, tour.city.size)]
                        ),
                        ToDoOnDay(
                            hour = "14",
                            minute = "30",
                            content = "Lên xe/bay về điểm tập kết ban đầu",
                            location = tour.city[Random.nextInt(0, tour.city.size)]
                        ),
                        ToDoOnDay(
                            hour = "17",
                            minute = "00",
                            content = "Dừng chân nghỉ ngơi trên đường về",
                            location = tour.city[Random.nextInt(0, tour.city.size)]
                        ),
                        ToDoOnDay(
                            hour = "19",
                            minute = "00",
                            content = "Về đến điểm tập kết, kiểm tra lại hành lý",
                            location = tour.gatheringPlace
                        ),
                        ToDoOnDay(
                            hour = "19",
                            minute = "30",
                            content = "Tổng kết chuyến đi, gửi lời cảm ơn đến khách",
                            location = tour.gatheringPlace
                        )
                    )
                    lsToDoOnDay.addAll(todo)
                } else {
                    val todo = mutableListOf<ToDoOnDay>(
                        ToDoOnDay(
                            hour = "6",
                            minute = "30",
                            content = "Thức dậy, vệ sinh cá nhân, chuẩn bị đồ đạc",
                            location = tour.city[Random.nextInt(0, tour.city.size)]
                        ),
                        ToDoOnDay(
                            hour = "7",
                            minute = "00",
                            content = "Ăn sáng tại khách sạn",
                            location = tour.city[Random.nextInt(0, tour.city.size)]
                        ),
                        ToDoOnDay(
                            hour = "8",
                            minute = "00",
                            content = "Di chuyển đến điểm tham quan buổi sáng",
                            location = tour.city[Random.nextInt(0, tour.city.size)]
                        ),
                        ToDoOnDay(
                            hour = "9",
                            minute = "00",
                            content = "Khám phá điểm du lịch nổi bật trong khu vực",
                            location = tour.city[Random.nextInt(0, tour.city.size)]
                        ),
                        ToDoOnDay(
                            hour = "11",
                            minute = "30",
                            content = "Dùng bữa trưa tại nhà hàng địa phương",
                            location = tour.city[Random.nextInt(0, tour.city.size)]
                        ),
                        ToDoOnDay(
                            hour = "13",
                            minute = "30",
                            content = "Tham gia hoạt động vui chơi/giải trí ngoài trời",
                            location = tour.city[Random.nextInt(0, tour.city.size)]
                        ),
                        ToDoOnDay(
                            hour = "16",
                            minute = "00",
                            content = "Tham quan điểm du lịch tiếp theo hoặc tự do khám phá",
                            location = tour.city[Random.nextInt(0, tour.city.size)]
                        ),
                        ToDoOnDay(
                            hour = "18",
                            minute = "30",
                            content = "Dùng bữa tối và thưởng thức ẩm thực đặc sản",
                            location = tour.city[Random.nextInt(0, tour.city.size)]
                        ),
                        ToDoOnDay(
                            hour = "20",
                            minute = "00",
                            content = "Tham gia các hoạt động giải trí buổi tối (giao lưu, dạo phố, chợ đêm, v.v.)",
                            location = tour.city[Random.nextInt(0, tour.city.size)]
                        ),
                        ToDoOnDay(
                            hour = "22",
                            minute = "00",
                            content = "Về khách sạn, nghỉ ngơi chuẩn bị cho ngày tiếp theo",
                            location = tour.city[Random.nextInt(0, tour.city.size)]
                        ),
                    )
                    lsToDoOnDay.addAll(todo)
                }
                schedule.lsTodo = lsToDoOnDay
                lsSchedule = lsSchedule + schedule
            }
            tour.schedule = lsSchedule
            val doc = FirebaseController.firestore.collection("tours").document()
            tour.id = doc.id
            FirebaseController.firestore.collection("tours").document(tour.id).set(tour)
            Log.d("HyuNie", "Add tour: $i")
        }
    }
}