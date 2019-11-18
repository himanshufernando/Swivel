package com.himanshu.project.myapplication.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserTB)

    @Query("SELECT * FROM user")
    suspend fun getUser(): UserTB

    @Query("SELECT userPreferences FROM user LIMIT 1")
    suspend fun getUserPreferences(): String

}