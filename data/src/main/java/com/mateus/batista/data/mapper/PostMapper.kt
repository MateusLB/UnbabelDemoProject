package com.mateus.batista.data.mapper

import com.mateus.batista.data.model.PostModel
import com.mateus.batista.domain.model.Post

object PostMapper : DataMapper<PostModel, Post> {
    override fun toDomain(data: PostModel): Post {
        return Post(
            userId = data.userId,
            id = data.id,
            title = data.title,
            body = data.body
        )
    }

    override fun fromData(data: Post): PostModel {
        return PostModel(
            userId = data.userId,
            id = data.id,
            title = data.title,
            body = data.body
        )
    }
}