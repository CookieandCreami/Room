package com.example.room

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.example.room.ui.theme.RoomTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomTheme {
                Contects()
            }
        }
    }
}

@Composable
fun Contects() {
    val context = LocalContext.current
    val db = remember {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "contact.db"
        ).build()
    }
//    val list = db.userDao().getAll() 컴포즈가 아닌곳에서 가져오려면 여기다가.

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var name by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }

        TextField(value = name, onValueChange = { name = it }, label = { Text("이름") })
        Divider(color = Color.Black, thickness = 5.dp)

        TextField(value = phone, onValueChange = { phone = it }, label = { Text("전화번호") })
        Divider(color = Color.Black, thickness = 5.dp)

        TextField(value = email, onValueChange = { email = it }, label = { Text("email") })
        Divider(color = Color.Black, thickness = 5.dp)

        val coroutineScope = rememberCoroutineScope()
        Button(onClick = {
            coroutineScope.launch(Dispatchers.IO) {
                db.userDao().insertAll(
                    User(
                        userName = name,
                        phoneNumber = phone,
                        email = email
                    )
                )
            }
        }) {
            Text(text = "등록")
        }
        Column {
            val userList = db.userDao().getAll()
            Text(text = "이름 : $name")
            Text(text = "전화번호 : $phone")
            Text(text = "이메일 : $email")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RoomTheme {
        Contects()
    }
}