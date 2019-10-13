package com.mateus.batista.data_cache.core

import com.mateus.batista.data.model.CommentModel
import com.mateus.batista.data.model.PostModel
import com.mateus.batista.data.model.UserModel
import com.mateus.batista.data.source.PostCacheDataSource
import com.mateus.batista.data_cache.dao.CommentDao
import com.mateus.batista.data_cache.dao.PostDao
import com.mateus.batista.data_cache.dao.UserDao
import com.mateus.batista.data_cache.mapper.CommentMapper
import com.mateus.batista.data_cache.mapper.PostMapper
import com.mateus.batista.data_cache.mapper.UserMapper
import org.koin.core.KoinComponent

class PostCacheDataSourceImp(
    private val userDao: UserDao,
    private val postDao: PostDao,
    private val commentDao: CommentDao
) : KoinComponent, PostCacheDataSource {

    override suspend fun insertAllPost(posts: List<PostModel>) {
        postDao.insertAll(posts.map { PostMapper.fromData(it) })
    }

    override suspend fun insertAllComments(comments: List<CommentModel>) {
        commentDao.insertAll(comments.map { CommentMapper.fromData(it) })
    }

    override suspend fun insertAllUsers(users: List<UserModel>) {
        userDao.insertAll(users.map { UserMapper.fromData(it) })
    }

    override suspend fun getUserByPostId(postId: Long): UserModel {
        return UserMapper.toData(userDao.getUserById(postId))
    }

    override suspend fun getCommentsByPostId(postId: Long): List<CommentModel> {
        return commentDao.getAllCommentById(postId).map { CommentMapper.toData(it) }
    }

    override suspend fun getPosts(): List<PostModel> {
        return postDao.getAllPost().map { PostMapper.toData(it) }
    }
}