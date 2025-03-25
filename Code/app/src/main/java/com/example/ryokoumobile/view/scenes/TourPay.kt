package com.example.ryokoumobile.view.scenes

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ryokoumobile.R
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.entity.Tour
import com.example.ryokoumobile.model.entity.TourBooked
import com.example.ryokoumobile.model.repository.Scenes
import com.example.ryokoumobile.view.components.MyElevatedButton
import com.example.ryokoumobile.view.components.MyShowToast
import com.example.ryokoumobile.view.components.MyTopBar

@Composable
fun TourPay(tourBooked: TourBooked, navController: NavController) {
    val tour = DataController.tourVM.getTourFromID(tourBooked.tourId)
    val styleTitle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
    val styleMedium = TextStyle(fontSize = 18.sp)
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    Scaffold(
        topBar = { MyTopBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 15.dp, vertical = 15.dp)
                .verticalScroll(state = scrollState)
        ) {
            ThongTinTour(tour!!, tourBooked, styleTitle, styleMedium)
            Spacer(Modifier.height(10.dp))
            PhuongThucThanhToan(styleTitle, styleMedium)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                MyElevatedButton(
                    title = "Thanh toán",
                    modifier = Modifier.fillMaxWidth(0.5f)
                ) {
                    DataController.updateBookedTour(tourBooked)
                    MyShowToast(context, "Đặt thành công")
                    navController.navigate(Scenes.MainGroup.route)
                }
            }
        }
    }
}

@Composable
private fun PhuongThucThanhToan(styleTitle: TextStyle, styleMedium: TextStyle) {
    Text(stringResource(R.string.phuongThucThanhToan), style = styleTitle)
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .height(450.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text("Thẻ và tài khoản ngân hàng", style = styleMedium)
        RowIcon(ls.subList(0, 2), styleMedium)
        RowIcon(ls.subList(2, 4), styleMedium)
        Text("Ví điện tử", style = styleMedium)
        RowIcon(ls.subList(4, 6), styleMedium)
    }
}

@Composable
private fun RowIcon(listItem: List<Pay>, styleMedium: TextStyle) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        ItemPay(
            icon = listItem[0].icon,
            title = listItem[0].title,
            isDefault = listItem[0].title == "Tiền mặt"
        )
        ItemPay(
            icon = listItem[1].icon,
            title = listItem[1].title,
            isDefault = listItem[1].title == "Tiền mặt"
        )
    }
}

private data class Pay(@DrawableRes val icon: Int, val title: String)

private val ls =
    listOf(
        Pay(R.drawable.tienmat, "Tiền mặt"),
        Pay(R.drawable.visa, "Thẻ tín dụng, thẻ ghi nợ quốc tế"),
        Pay(R.drawable.vietqr, "Mobile Banking VietQR"),
        Pay(R.drawable.vnpay, "Quét mã QR VNPay"),
        Pay(R.drawable.momo, "Momo"),
        Pay(R.drawable.zalo, "Zalo pay")
    )

@Composable
private fun ThongTinTour(
    tour: Tour,
    tourBooked: TourBooked,
    styleTitle: TextStyle,
    styleMedium: TextStyle
) {
    Text(stringResource(R.string.tourInformation), style = styleTitle)
    Column(
        modifier = Modifier.padding(start = 15.dp, top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(tour.name, style = styleMedium)
        Text(
            stringResource(R.string.thoiGian) + ": ${tourBooked.formatDate(tourBooked.startDay.toDate())}",
            style = styleMedium
        )
        Text(
            stringResource(R.string.soLuongKhach) + ": ${tourBooked.numPerson}",
            style = styleMedium
        )
        Text(
            stringResource(R.string.tongTien) + ": ${tourBooked.getTotalPay()}",
            style = styleMedium
        )
    }
}

@Composable
private fun ItemPay(
    @DrawableRes icon: Int,
    title: String,
    isDefault: Boolean = false
) {
    val context = LocalContext.current
    Card(
        onClick = {
            if (isDefault) return@Card else MyShowToast(
                context = context,
                "Tính năng đang phát triển"
            )
        },
        modifier = if (isDefault) {
            Modifier
                .width(150.dp)
                .height(120.dp)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(15.dp)
                )
        } else {
            Modifier
                .width(150.dp)
                .height(120.dp)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painterResource(icon), contentDescription = null, Modifier.size(50.dp))
            Spacer(Modifier.height(5.dp))
            Text(title, style = TextStyle(fontSize = 18.sp), textAlign = TextAlign.Center)
        }
    }
}