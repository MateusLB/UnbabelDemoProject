package com.mateus.batista.data_cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "post",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class PostEntity(
    @PrimaryKey(autoGenerate = false) val id: Long,
    @ColumnInfo(name = "user_id") val userId: Long,
    val title: String,
    val body: String
)