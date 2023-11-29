package com.example.mygithubuser.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_user")
data class FavoriteUser (
    val login: String,
    @PrimaryKey
    val avatarUrl : String,
    val id: Int
): Serializable