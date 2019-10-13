package com.mateus.batista.feature_post.mapper

import com.mateus.batista.domain.model.Post
import com.mateus.batista.feature_post.model.ListPost

object ListPostMapper : PostMapper<List<Post>, List<ListPost>> {
    override fun toFeature(model: List<Post>): List<ListPost> {
        return model.map { parsePost(it) }
    }

    private fun parsePost(post: Post): ListPost {
        return ListPost(
            id = post.id,
            title = post.title
        )
    }
}