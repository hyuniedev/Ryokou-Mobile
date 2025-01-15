package com.example.ryokoumobile.view.scenes

import android.graphics.drawable.Icon
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ryokoumobile.R
import com.example.ryokoumobile.model.controller.DataController
import com.example.ryokoumobile.model.controller.FirebaseController
import com.example.ryokoumobile.model.entity.ItemAccount
import com.example.ryokoumobile.model.repository.Scenes
import com.example.ryokoumobile.view.components.BoxWelcome
import com.example.ryokoumobile.view.components.MyElevatedButton
import com.example.ryokoumobile.view.components.MyShowToast
import com.example.ryokoumobile.view.items.ItemSetting

@Composable
fun AccountScene(modifier: Modifier = Modifier, navController: NavController) {
    val context = LocalContext.current
    var lsItem = mutableListOf(
        ItemAccount(
            icon = {
                Icon(
                    painter = painterResource(R.drawable.language),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(30.dp)
                )
            },
            title = "Ngôn ngữ",
            descriptor = "Tiếng việt",
            onClick = { MyShowToast(context, "Tính năng đang được phát triển") }
        ),
        ItemAccount(
            icon = {
                Icon(
                    painter = painterResource(R.drawable.contact_support),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(30.dp)
                )
            },
            title = "Trung tâm hỗ trợ",
            descriptor = "Nơi giải đáp mọi thắc mắc của bạn",
            onClick = {}
        ),
        ItemAccount(
            icon = {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(30.dp)
                )
            },
            title = "Liên hệ với chúng tôi",
            descriptor = "Yêu cầu hỗ trợ từ dịch vụ chăm sóc khách hàng",
            onClick = {}
        ),
        ItemAccount(
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(30.dp)
                )
            },
            title = "Cài đặt",
            descriptor = "Xem và tùy chỉnh cài đặt cho tài khoản",
            onClick = {}
        ),
    )

    val user = DataController.user.collectAsState()
    Column(modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        when (user.value) {
            null -> BoxWelcome { navController.navigate(Scenes.AuthGroup.Login.route) }
            else -> {
                lsItem.add(index = 0, element = ItemAccount(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(30.dp)
                        )
                    },
                    title = "Thông tin tài khoản",
                    descriptor = "Nhấn để thay đổi",
                    onClick = {}
                ))
                lsItem = lsItem.toMutableSet().toMutableList()
            }
        }
        Icon(
            painter = painterResource(R.drawable.manage_accounts),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(top = 30.dp)
                .size(80.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 20.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier.border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(15.dp)
                )
            ) {
                lsItem.forEachIndexed { index, _ ->
                    ItemSetting(
                        icon = lsItem[index].icon,
                        title = lsItem[index].title,
                        descriptor = lsItem[index].descriptor,
                        onClick = lsItem[index].onClick
                    )
                    if (index != lsItem.size - 1) {
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

