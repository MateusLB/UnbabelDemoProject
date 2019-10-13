package com.mateus.batista.data_cache.dao

import androidx.room.Dao
import androidx.room.Query
import com.mateus.batista.data_cache.entity.UserEntity

@Dao
abstract class UserDao : BaseDao<UserEntity> {

    @Query("SELECT * FROM user WHERE id=:id")
    abstract suspend fun getUserById(id: Long) : UserEntity
}