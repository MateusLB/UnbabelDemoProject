package com.mateus.batista.data_cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "comment",
    foreignKeys = [ForeignKey(
        entity = PostEntity::class,
        parentColumns = ["id"],
        childColumns = ["post_id"],
        onDelete = CASCADE
    )]
)
data class CommentEntity(
    @PrimaryKey(autoGenerate = false) val id: Long,
    @ColumnInfo(name = "post_id") val postId: Long,
    val name: String,
    val email: String,
    val body: String
)