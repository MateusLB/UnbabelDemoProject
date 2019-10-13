package com.mateus.batista.data_cache.mapper

import com.mateus.batista.data.model.PostModel
import com.mateus.batista.data_cache.entity.PostEntity


object PostMapper: CacheMapper<PostEntity, PostModel> {

    override fun toData(entity: PostEntity): PostModel {
       return PostModel(
               userId = entity.userId,
               id = entity.id,
               title = entity.title,
               body = entity.body
           )
    }

    override fun fromData(model: PostModel): PostEntity {
        return PostEntity(
            userId = model.userId,
            id = model.id,
            title = model.title,
            body = model.body
        )
    }
}