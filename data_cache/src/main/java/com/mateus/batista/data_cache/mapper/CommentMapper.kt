package com.mateus.batista.data_cache.mapper

import com.mateus.batista.data.model.CommentModel
import com.mateus.batista.data_cache.entity.CommentEntity

object CommentMapper : CacheMapper<CommentEntity, CommentModel> {
    override fun toData(entity: CommentEntity): CommentModel {
        return CommentModel(
            postId = entity.postId,
            id = entity.id,
            name = entity.name,
            email = entity.email,
            body = entity.body
        )
    }

    override fun fromData(model: CommentModel): CommentEntity {
        return CommentEntity(
            postId = model.postId,
            id = model.id,
            name = model.name,
            email = model.email,
            body = model.body
        )
    }
}