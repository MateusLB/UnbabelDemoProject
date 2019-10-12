package com.mateus.batista.data_cache.dao

import androidx.room.Query
import com.mateus.batista.data_cache.entity.CommentEntity

abstract class CommentDao : BaseDao<CommentEntity>{

    @Query("SELECT * FROM comment WHERE post_id=:postId")
    abstract suspend fun getAllPost(postId: Long) : List<CommentEntity>
}