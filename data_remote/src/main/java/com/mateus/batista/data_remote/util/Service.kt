package com.mateus.batista.data_remote.util

import retrofit2.HttpException
import java.io.IOException

open class DataSourceException(message: String? = null, code: Int? = null) : Exception(message)
class RemoteDataNotFoundException(message: String?, code: Int? = null) :
    DataSourceException(message, code)

class ServerException(message: String?) : Exception(message)

suspend fun <T : Any> callApi(
    call: suspend () -> T
): Response<T> {

    return try {
        val dataFromRemote = call()
        Response.Success(dataFromRemote)

    } catch (httpException: HttpException) {
        Response.Error(RemoteDataNotFoundException(httpException.message(), httpException.code()))
    } catch (ioException: IOException) {
        Response.Error(ServerException(ioException.message))
    }
}