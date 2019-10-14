package com.mateus.batista.feature_post.mapper

import com.mateus.batista.domain.model.Post
import com.mateus.batista.feature_post.model.DetailPost
import com.mateus.batista.feature_post.model.ListPost

object DetailPostMapper : PostMapper<Post, DetailPost> {
    override fun toFeature(model: Post): DetailPost {
        return DetailPost(
            author = model.author,
            description = model.body,
            numberComments = model.numberComments.toString()
        )
    }


}