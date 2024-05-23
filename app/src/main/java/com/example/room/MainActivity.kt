//https://developer.android.com/training/data-storage/room?hl=ko
//https://developer.android.com/build/migrate-to-ksp?hl=ko  - ksp에 대한 build.gradle에 추가
//참고하여 복붙 넣을 것

package com.example.room

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Button

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        AppDatabase.getDatabase(context)
    }
//    val list = db.userDao().getAll() 컴포즈가 아닌곳에서 가져오려면 여기다가.

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var name by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("이름") },
            modifier = Modifier.fillMaxWidth().padding(all = 20.dp)
        )

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("전화번호") },
            modifier = Modifier.fillMaxWidth().padding(all = 20.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("email") },
            modifier = Modifier.fillMaxWidth().padding(all = 20.dp)
        )


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
            val userList by db.userDao().getAll().collectAsState(initial = emptyList())
            for (user in userList) {
                Text(text = "이름 : ${user.userName}")
                Text(text = "전화번호 : ${user.phoneNumber}")
                Text(text = "email : ${user.email}")
            }
            userList.forEach {
                Text(text = "이름 : ${it.userName}")
                Text(text = "전화번호 : ${it.phoneNumber}")
                Text(text = "email : ${it.email}")
            }
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