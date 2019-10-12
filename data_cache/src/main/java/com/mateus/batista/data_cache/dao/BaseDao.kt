package com.mateus.batista.data_cache.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(obj : T) : Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg obj : T)

    @Update
    suspend fun update(obj : T)

    @Delete
    suspend fun delete(obj : T)

}