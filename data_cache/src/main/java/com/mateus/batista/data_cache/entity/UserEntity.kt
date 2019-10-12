package com.mateus.batista.data_cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val username: String,
    val email: String
)