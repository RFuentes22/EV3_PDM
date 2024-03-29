package com.benjalamesta.pelidex.Repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.benjalamesta.pelidex.Database.daos.MovieDao
import com.benjalamesta.pelidex.Models.Movie
import com.benjalamesta.pelidex.Models.OmbdMovieResponse
import com.benjalamesta.pelidex.Network.OmbdApi
import kotlinx.coroutines.Deferred
import retrofit2.Response

class MovieRepository(private val movieDao: MovieDao, private val api: OmbdApi){

    fun retrieveMoviesByNameAsync(name:String): Deferred<Response<OmbdMovieResponse>> = api.getMoviesByName(name)

    fun retrieveMoviesByTitleAsync(name:String): Deferred<Response<Movie>> = api.getMovieByTitle(name)

    @WorkerThread
    suspend fun insert(movie: Movie) = movieDao.insertMovie(movie )

    fun getAllfromRoomDB(): LiveData<List<com.benjalamesta.pelidex.Models.Movie>> = movieDao.getMovies()

    fun getMovieByName(name: String) = movieDao.getMovieByName(name)
}