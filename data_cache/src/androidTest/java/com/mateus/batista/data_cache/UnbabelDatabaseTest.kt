package com.mateus.batista.data_cache

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mateus.batista.data_cache.dao.CommentDao
import com.mateus.batista.data_cache.dao.PostDao
import com.mateus.batista.data_cache.dao.UserDao
import com.mateus.batista.data_cache.database.UnbabelDatabase
import com.mateus.batista.data_cache.entity.CommentEntity
import com.mateus.batista.data_cache.entity.PostEntity
import com.mateus.batista.data_cache.entity.UserEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class UnbabelDatabaseTest {

    private lateinit var db: UnbabelDatabase
    private lateinit var postDao: PostDao
    private lateinit var userDao: UserDao
    private lateinit var commentDao: CommentDao

    private val dummyUser = UserEntity(2, "mateus", "mateus", "mateuslb91@gmail.com")
    private val dummyPost = PostEntity(1,2,"uhusaduha", "usahdasuihduasid")
    private val dummyComment = CommentEntity(3,1,"name", "usahdasuihduasid", "sadasda")

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, UnbabelDatabase::class.java).build()
        postDao = db.postDao()
        userDao = db.userDao()
        commentDao = db.commentDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun checkInsertUserAndGetById() = runBlocking {
        val id = userDao.insert(dummyUser)
        val userFromDb = userDao.getUserById(id)
        Assert.assertEquals(id, 2)
        Assert.assertNotEquals(id, -1)
        Assert.assertEquals(userFromDb, dummyUser)
    }

    @Test
    @Throws(Exception::class)
    fun checkInsertAllPostAndGetAll() = runBlocking {
        userDao.insert(dummyUser)
        postDao.insertAll(listOf(dummyPost))
        val postFromDb = postDao.getAllPost()

        Assert.assertEquals(postFromDb, listOf(dummyPost))
    }

    @Test
    @Throws(Exception::class)
    fun checkInsertAllCommentAndGetAllByPostId() = runBlocking {
        userDao.insert(dummyUser)
        postDao.insert(dummyPost)
        commentDao.insertAll(listOf(dummyComment))
        val commentFromDb = commentDao.getAllCommentById(1)
        Assert.assertEquals(commentFromDb, listOf(dummyComment))
    }
}