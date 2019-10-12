package com.mateus.batista.data_remote

import com.mateus.batista.data.repository.PostRemoteDataSource
import com.mateus.batista.data_remote.core.PostRemoteDataSourceImp
import com.mateus.batista.data_remote.mapper.CommentMapper
import com.mateus.batista.data_remote.mapper.PostMapper
import com.mateus.batista.data_remote.mapper.UserMapper
import com.mateus.batista.data_remote.model.CommentApi
import com.mateus.batista.data_remote.model.PostApi
import com.mateus.batista.data_remote.model.UserApi
import com.mateus.batista.data_remote.service.UnbabelService
import com.nhaarman.mockitokotlin2.mock
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
class PostRemoteDataSourceTest : KoinTest {

    private val service = mock<UnbabelService>()
    private lateinit var postRemoteDataSource : PostRemoteDataSource

    @Before
    fun setUp(){
        startKoin {  }
        postRemoteDataSource = PostRemoteDataSourceImp(service)
    }

    @After
    fun after(){
        stopKoin()
    }

    @Test
    fun `getUsers must return right mapped value`() = runBlocking {
        val dummyUsers = listOf<UserApi>()
        stubUsers(dummyUsers)

        val response = postRemoteDataSource.getUsers()
        assertEquals(response, UserMapper.toData(dummyUsers))
    }

    @Test
    fun `getComments must return right mapped value`() = runBlocking {
        val dummyComments = listOf<CommentApi>()
        stubComments(dummyComments)

        val response = postRemoteDataSource.getComments()
        assertEquals(response, CommentMapper.toData(dummyComments))
    }

    @Test
    fun `getPosts must return right mapped value`() = runBlocking {
        val dummyPosts = listOf<PostApi>()
        stubPosts(dummyPosts)

        val response = postRemoteDataSource.getPosts()
        assertEquals(response, PostMapper.toData(dummyPosts))
    }

    private suspend fun stubUsers(users: List<UserApi>) {
        whenever(service.getUsers()).thenReturn(users)
    }

    private suspend fun stubComments(comments: List<CommentApi>){
        whenever(service.getComments()).thenReturn(comments)
    }

    private suspend fun stubPosts(posts: List<PostApi>){
        whenever(service.getPosts()).thenReturn(posts)
    }
}