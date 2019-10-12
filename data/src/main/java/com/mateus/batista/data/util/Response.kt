package com.mateus.batista.data.util

sealed class Response<out D> {
    class Success<D>(val data: D) : Response<D>()
    class Error(val exception: Throwable) : Response<Nothing>()
}