package com.mateus.batista.data_cache.database

import android.content.Context
import androidx.room.Room
import com.mateus.batista.data_cache.dao.CommentDao
import com.mateus.batista.data_cache.dao.PostDao
import com.mateus.batista.data_cache.dao.UserDao

object DatabaseFactory {

    fun createPostDao(db: UnbabelDatabase): PostDao = db.postDao()

    fun createCommentDao(db: UnbabelDatabase): CommentDao = db.commentDao()

    fun createUserDao(db: UnbabelDatabase): UserDao = db.userDao()

    fun createDatabase(context: Context): UnbabelDatabase {
        return Room.databaseBuilder(context, UnbabelDatabase::class.java, "database-unbabel")
            .fallbackToDestructiveMigration()
            .build()
    }
}