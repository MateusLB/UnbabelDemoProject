package com.mateus.batista.data_cache.dao

import androidx.room.Query
import com.mateus.batista.data_cache.entity.PostEntity

abstract class PostDao : BaseDao<PostEntity> {

   @Query("SELECT * FROM post")
   abstract suspend fun getAllPost() : List<PostEntity>
}