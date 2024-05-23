package com.example.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)   val uid: Int = 0, //이 코드를 입력하면 알아서 값이 정리됨
    @ColumnInfo(name = "name")         val userName: String?,
    @ColumnInfo(name = "phone_number") val phoneNumber: String?,
    @ColumnInfo(name = "email")        val email: String?
)