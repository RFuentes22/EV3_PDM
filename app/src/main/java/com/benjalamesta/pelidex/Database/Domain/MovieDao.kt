package com.benjalamesta.pelidex.Database.Domain

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.benjalamesta.pelidex.Models.Movie


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Query("select * from Movie")
    fun allMovies(): LiveData<List<Movie>>

    @Query("select * from Movie where Title like :name")
    fun searchMovieByName(name: String): LiveData<List<Movie>>
}