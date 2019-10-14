package com.mateus.batista.data

import com.mateus.batista.data.mapper.PostMapper
import com.mateus.batista.data.model.CommentModel
import com.mateus.batista.data.model.PostModel
import com.mateus.batista.data.model.UserModel
import com.mateus.batista.data.repository.PostRepositoryImp
import com.mateus.batista.data.source.PostCacheDataSource
import com.mateus.batista.data.source.PostRemoteDataSource
import com.mateus.batista.data.util.NetworkStatus
import com.mateus.batista.domain.repository.PostRepository
import com.mateus.batista.domain.util.Response
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

@RunWith(JUnit4::class)
class PostRepositoryTest : KoinTest {

    private val cache = mock<PostCacheDataSource>()
    private val remote = mock<PostRemoteDataSource>()

    private lateinit var repository: PostRepository

    private val networkStatus = mock<NetworkStatus>()
    private val testModule = module { single { networkStatus } }

    @Before
    fun setup() {
        startKoin { testModule }
        repository = PostRepositoryImp(cache, remote)
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `getPosts when is online and getPost return error must return Error`() =
        runBlocking {
            stubNetworkStatus(true)
            stubErrorPosts()

            val response = repository.getPosts()
            assert(response is Response.Error)
        }

    @Test
    fun `getPosts when is online and getComments return error must return Error`() =
        runBlocking {
            stubNetworkStatus(true)
            stubErrorComments()

            val response = repository.getPosts()
            assert(response is Response.Error)
        }

    @Test
    fun `getPosts when is online and getUsers return error must return Error`() =
        runBlocking {
            stubNetworkStatus(true)
            stubErrorUsers()

            val response = repository.getPosts()
            assert(response is Response.Error)
        }

    @Test
    fun `getPosts when is online and insertAllUsers return error must return Error`() =
        runBlocking {
            stubNetworkStatus(true)
            stubErrorSavePosts()

            val response = repository.getPosts()
            assert(response is Response.Error)
        }

    @Test
    fun `getPosts when is online and insertAllPost return error must return Error`() =
        runBlocking {
            stubNetworkStatus(true)
            stubErrorSaveComments()

            val response = repository.getPosts()
            assert(response is Response.Error)
        }

    @Test
    fun `getPosts when is online and cache getPosts return error must return Error`() =
        runBlocking {
            stubNetworkStatus(true)
            stubErrorPostsCache()

            val response = repository.getPosts()
            assert(response is Response.Error)
        }

    @Test
    fun `getPosts when is online and insertAllComments return error must return Error`() =
        runBlocking {
            stubNetworkStatus(true)
            stubErrorSaveUsers()

            val response = repository.getPosts()
            assert(response is Response.Error)
        }

    @Test
    fun `getAndSaveAllDataIfHasInternet is online must call gets`() = runBlocking<Unit> {
        stubNetworkStatus(true)

        repository.getPosts()
        remote.getPosts()
        remote.getComments()
        remote.getUsers()

        verify(remote, times(1)).getPosts()
        verify(remote, times(1)).getComments()
        verify(remote, times(1)).getUsers()
    }

    @Test
    fun `getAndSaveAllDataIfHasInternet is offline must do not call gets`() = runBlocking<Unit> {
        stubNetworkStatus(false)

        repository.getPosts()

        verify(remote, times(0)).getPosts()
        verify(remote, times(0)).getComments()
        verify(remote, times(0)).getUsers()
    }

    @Test
    fun `getPosts return a Success response`() = runBlocking {
        val dummyPost = listOf<PostModel>()
        stubGetPosts(dummyPost)

        stubNetworkStatus(false)

        val response = repository.getPosts()
        assert(response is Response.Success && response.data == dummyPost.map {
            PostMapper.toDomain(
                it
            )
        })

    }

    @Test
    fun `getPostById when is online and getPostById return error must return Error`() =
        runBlocking {
            stubErrorPostById()

            val response = repository.getPostById(1)
            assert(response is Response.Error)
        }

    @Test
    fun `getPosts when is online and getCommentsByPostId return error must return Error`() =
        runBlocking {
            stubErrorCommentsByPostId()

            val response = repository.getPostById(1)
            assert(response is Response.Error)
        }

    @Test
    fun `getPosts when is online and getUserById return error must return Error`() =
        runBlocking {
            stubErrorUserById()

            val response = repository.getPostById(1)
            assert(response is Response.Error)
        }

    @Test
    fun `getPostById return a Success response`() = runBlocking {
        val dummyPost = PostModel(2, 1, "", "")
        stubGetPost(dummyPost)

        val dummyComments = listOf<CommentModel>()
        stubGetComments(dummyComments)

        val dummyUser = UserModel(2, "", "", "")
        stubGetUser(dummyUser)

        val response = repository.getPostById(1)
        assert(
            response is Response.Success && response.data == PostMapper.toDomain(
                dummyPost,
                dummyComments,
                dummyUser
            )
        )

    }

    private fun stubNetworkStatus(isOnline: Boolean) {
        whenever(networkStatus.isOnline()).thenReturn(isOnline)
    }

    private suspend fun stubGetPosts(posts: List<PostModel>) {
        whenever(cache.getPosts()).thenReturn(posts)
    }

    private suspend fun stubErrorPostsCache() {
        whenever(cache.getPosts()).doThrow(RuntimeException())
    }

    private suspend fun stubErrorSavePosts() {
        whenever(cache.insertAllPost(any())).doThrow(RuntimeException())
    }

    private suspend fun stubErrorSaveComments() {
        whenever(cache.insertAllComments(any())).doThrow(RuntimeException())
    }

    private suspend fun stubErrorSaveUsers() {
        whenever(cache.insertAllPost(any())).doThrow(RuntimeException())
    }

    private suspend fun stubErrorPosts() {
        whenever(remote.getPosts()).doThrow(RuntimeException())
    }

    private suspend fun stubErrorComments() {
        whenever(remote.getComments()).doThrow(RuntimeException())
    }

    private suspend fun stubErrorUsers() {
        whenever(remote.getUsers()).doThrow(RuntimeException())
    }

    private suspend fun stubErrorPostById() {
        whenever(cache.getPostById(any())).doThrow(RuntimeException())
    }

    private suspend fun stubErrorCommentsByPostId() {
        whenever(cache.getCommentsByPostId(any())).doThrow(RuntimeException())
    }

    private suspend fun stubErrorUserById() {
        whenever(cache.getUserById(any())).doThrow(RuntimeException())
    }

    private suspend fun stubGetPost(post: PostModel) {
        whenever(cache.getPostById(any())).thenReturn(post)
    }

    private suspend fun stubGetComments(comments: List<CommentModel>) {
        whenever(cache.getCommentsByPostId(any())).thenReturn(comments)
    }

    private suspend fun stubGetUser(user: UserModel) {
        whenever(cache.getUserById(any())).thenReturn(user)
    }

}