package com.benjalamesta.pelidex.Network

import com.benjalamesta.pelidex.Models.Movie
import com.benjalamesta.pelidex.Models.OmbdMovieResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface OmbdApi {

    @GET("/")
    fun getMoviesByName(@Query("s") query: String): Deferred<Response<OmbdMovieResponse>>

    @GET("/")
    fun getMovieByTitle(@Query("t") query: String): Deferred<Response<Movie>>
}
