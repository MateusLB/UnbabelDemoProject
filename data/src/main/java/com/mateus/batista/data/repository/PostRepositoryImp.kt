package com.mateus.batista.data.repository

import com.mateus.batista.data.mapper.PostMapper
import com.mateus.batista.data.model.CommentModel
import com.mateus.batista.data.model.PostModel
import com.mateus.batista.data.model.UserModel
import com.mateus.batista.data.source.PostCacheDataSource
import com.mateus.batista.data.source.PostRemoteDataSource
import com.mateus.batista.data.util.NetworkStatus
import com.mateus.batista.domain.model.Post
import com.mateus.batista.domain.repository.PostRepository
import com.mateus.batista.domain.util.Response
import org.koin.core.KoinComponent
import org.koin.core.inject


class PostRepositoryImp(
    private val cache: PostCacheDataSource,
    private val remote: PostRemoteDataSource
) : KoinComponent, PostRepository {

    private val networkStatus : NetworkStatus by inject()

    override suspend fun getPosts(): Response<List<Post>> {
        return try {
            getAndSaveAllDataIfHasInternet()

            val posts = cache.getPosts()
            Response.Success(posts.map { PostMapper.toDomain(it) })
        } catch (ex: Exception) {
            Response.Error(ex)
        }
    }

    private suspend fun getAndSaveAllDataIfHasInternet() {
        if(networkStatus.isOnline()) {
            val posts = remote.getPosts()
            val comments = remote.getComments()
            val users = remote.getUsers()
            saveResults(posts, comments, users)
        }
    }

    private suspend fun saveResults(
        posts: List<PostModel>,
        comments: List<CommentModel>,
        users: List<UserModel>
    ) {
        cache.insertAllUsers(users)
        cache.insertAllPost(posts)
        cache.insertAllComments(comments)

    }

    override suspend fun getPostById(id: Long): Response<Post> {
        return try {
            val post = cache.getPostById(id)
            val comments = cache.getCommentsByPostId(id)
            val user = cache.getUserById(post.userId)

            Response.Success(PostMapper.toDomain(post, comments, user))
        }catch (ex: Exception) {
            Response.Error(ex)
        }
    }
}