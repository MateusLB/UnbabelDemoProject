package com.mateus.batista.feature_post.mapper

interface PostMapper<in M, out F> {
    fun toFeature(model: M): F
}