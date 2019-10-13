package com.mateus.batista.data.repository

import com.mateus.batista.data.mapper.PostMapper
import com.mateus.batista.data.source.PostCacheDataSource
import com.mateus.batista.data.source.PostRemoteDataSource
import com.mateus.batista.domain.util.Response
import com.mateus.batista.domain.model.Post
import com.mateus.batista.domain.repository.PostRepository
import org.koin.core.KoinComponent


class PostRepositoryImp(
    private val cache: PostCacheDataSource,
    private val remote: PostRemoteDataSource
) : KoinComponent, PostRepository {
    override suspend fun getPosts(): Response<List<Post>> {
       return try {
            val posts = remote.getPosts()
            Response.Success(posts.map { PostMapper.toDomain(it) })
        }catch (ex : Exception){
            Response.Error(ex)
        }
    }

    override suspend fun getPostById(id: Long): Response<Post> {
        TODO("not implemented")
    }
}