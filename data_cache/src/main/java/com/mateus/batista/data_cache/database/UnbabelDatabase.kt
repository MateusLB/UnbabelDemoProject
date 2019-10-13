package com.mateus.batista.data_cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mateus.batista.data_cache.dao.CommentDao
import com.mateus.batista.data_cache.dao.PostDao
import com.mateus.batista.data_cache.dao.UserDao
import com.mateus.batista.data_cache.entity.CommentEntity
import com.mateus.batista.data_cache.entity.PostEntity
import com.mateus.batista.data_cache.entity.UserEntity

@Database(entities = [CommentEntity::class, PostEntity::class, UserEntity::class], version = 1)
abstract class UnbabelDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao
    abstract fun commentDao(): CommentDao
    abstract fun userDao(): UserDao
}