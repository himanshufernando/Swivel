package com.himanshu.project.myapplication.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class UserTB (
    @PrimaryKey val userID: Long?,
    var userName: String?,
    var userEmail: String?,
    var userPreferences: String?
){
}