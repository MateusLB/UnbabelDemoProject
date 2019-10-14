package com.mateus.batista.data.mapper

import com.mateus.batista.data.model.CommentModel
import com.mateus.batista.data.model.PostModel
import com.mateus.batista.data.model.UserModel
import com.mateus.batista.domain.model.Post

object PostMapper {
   fun toDomain(post: PostModel, comments: List<CommentModel> = listOf(), user: UserModel = UserModel(1,"123123","","")): Post {
        return Post(
            id = post.id,
            title = post.title,
            body = post.body,
            author = user.name,
            numberComments = comments.size

        )
    }
}