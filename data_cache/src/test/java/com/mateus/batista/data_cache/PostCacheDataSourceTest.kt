package com.mateus.batista.data_cache

import com.mateus.batista.data.model.CommentModel
import com.mateus.batista.data.model.PostModel
import com.mateus.batista.data.model.UserModel
import com.mateus.batista.data.source.PostCacheDataSource
import com.mateus.batista.data_cache.core.PostCacheDataSourceImp
import com.mateus.batista.data_cache.dao.CommentDao
import com.mateus.batista.data_cache.dao.PostDao
import com.mateus.batista.data_cache.dao.UserDao
import com.mateus.batista.data_cache.entity.CommentEntity
import com.mateus.batista.data_cache.entity.PostEntity
import com.mateus.batista.data_cache.entity.UserEntity
import com.mateus.batista.data_cache.mapper.CommentMapper
import com.mateus.batista.data_cache.mapper.PostMapper
import com.mateus.batista.data_cache.mapper.UserMapper
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class PostCacheDataSourceTest : KoinTest {

    private val userDao = mock<UserDao>()
    private val postDao = mock<PostDao>()
    private val commentDao = mock<CommentDao>()

    private lateinit var postCacheDataSource: PostCacheDataSource

    @Before
    fun setUp() {
        startKoin { }
        postCacheDataSource = PostCacheDataSourceImp(userDao, postDao, commentDao)
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `insertAllPost must call with mapped value`() = runBlocking {
        val dummyPostsModel = listOf<PostModel>()
        postCacheDataSource.insertAllPost(dummyPostsModel)

        verify(postDao).insertAll(dummyPostsModel.map { PostMapper.fromData(it) })
    }

    @Test
    fun `insertAllComments must call with mapped value`() = runBlocking {
        val dummyCommentsModel = listOf<CommentModel>()
        postCacheDataSource.insertAllComments(dummyCommentsModel)

        verify(commentDao).insertAll(dummyCommentsModel.map { CommentMapper.fromData(it) })
    }

    @Test
    fun `insertAllUsers must call with mapped value`() = runBlocking {
        val dummyUsersModel = listOf<UserModel>()
        postCacheDataSource.insertAllUsers(dummyUsersModel)

        verify(userDao).insertAll(dummyUsersModel.map { UserMapper.fromData(it) })
    }

    @Test
    fun `getPosts must return right mapped value`() = runBlocking {
        val dummyPosts = listOf<PostEntity>()
        stubPosts(dummyPosts)

        val result = postCacheDataSource.getPosts()
        assertEquals(result, dummyPosts.map { PostMapper.toData(it) })
    }

    @Test
    fun `getCommentsByPostId must return right mapped value`() = runBlocking {
        val dummyComments = listOf<CommentEntity>()
        stubComments(dummyComments)

        val result = postCacheDataSource.getCommentsByPostId(1)
        assertEquals(result, dummyComments.map { CommentMapper.toData(it) })
    }

    @Test
    fun `getUserByPostId must return right mapped value`() = runBlocking {
        val dummyUser = UserEntity(1, "", "", "")
        stubUser(dummyUser)

        val result = postCacheDataSource.getUserByPostId(1)
        assertEquals(result, UserMapper.toData(dummyUser))
    }

    @Test
    fun `getPostById must return right mapped value`() = runBlocking {
        val dummyPost = PostEntity(3,1,"","")
        stubPost(dummyPost)

        val result = postCacheDataSource.getPostById(3)
        assertEquals(result, PostMapper.toData(dummyPost))
    }

    private suspend fun stubPosts(posts: List<PostEntity>) {
        whenever(postDao.getAllPost()).thenReturn(posts)
    }

    private suspend fun stubPost(post : PostEntity) {
        whenever(postDao.getPostById(any())).thenReturn(post)
    }

    private suspend fun stubUser(user: UserEntity) {
        whenever(userDao.getUserById(any())).thenReturn(user)
    }

    private suspend fun stubComments(comments: List<CommentEntity>) {
        whenever(commentDao.getAllCommentById(any())).thenReturn(comments)
    }
}