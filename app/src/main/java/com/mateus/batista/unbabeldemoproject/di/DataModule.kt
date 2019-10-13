package com.mateus.batista.unbabeldemoproject.di

import com.mateus.batista.data.repository.PostRepositoryImp
import com.mateus.batista.data.source.PostCacheDataSource
import com.mateus.batista.data.source.PostRemoteDataSource
import com.mateus.batista.data_cache.core.PostCacheDataSourceImp
import com.mateus.batista.data_cache.database.DatabaseFactory
import com.mateus.batista.data_remote.core.PostRemoteDataSourceImp
import com.mateus.batista.data_remote.service.RequestInterceptor
import com.mateus.batista.data_remote.service.ServiceFactory
import com.mateus.batista.domain.repository.PostRepository
import com.mateus.batista.unbabeldemoproject.di.Constants.BASE_RUL
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


object Constants {
    const val BASE_RUL = "http://jsonplaceholder.typicode.com"
}

val dataModule = module {
    factory { PostRepositoryImp(get(), get()) as PostRepository }
}

val remoteModule = module {
    factory { PostRemoteDataSourceImp(get()) as PostRemoteDataSource }
}

val serviceModule = module {
    single { ServiceFactory.createService(BASE_RUL, get()) }
    single { ServiceFactory.createOkHttpClient(get(), get()) }
    single { ServiceFactory.createHttpLoggingInterceptor(BuildConfig.DEBUG) }
    single { RequestInterceptor() }
}

val cacheModule = module {
    factory { PostCacheDataSourceImp(get(), get(), get()) as PostCacheDataSource }
}

val databaseModule = module {
    single { DatabaseFactory.createPostDao(get()) }
    single { DatabaseFactory.createCommentDao(get()) }
    single { DatabaseFactory.createUserDao(get()) }
    single { DatabaseFactory.createDatabase(androidApplication()) }
}