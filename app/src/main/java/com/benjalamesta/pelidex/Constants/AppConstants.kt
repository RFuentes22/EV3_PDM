package com.benjalamesta.pelidex.Constants

import android.util.Log
import com.benjalamesta.pelidex.BuildConfig
import com.benjalamesta.pelidex.Models.Movie
import com.benjalamesta.pelidex.Models.MoviePreview

object AppConstants {


    val ADD_TASK_REQUEST = 1
    val emptymoviespreview = ArrayList<MoviePreview>()
    val emptymovies = ArrayList<Movie>()

    fun debugPreviewMovies(result: List<Movie>){
        Log.d("PETROLERO", "__________________________________________________")
        Log.d("PETROLERO", "NEW MOVIES________________________________________")
        for (res in result) Log.d("PETROLERO", "${res.Title} -> id = ${res.imdbID}")
        Log.d("PETROLERO", "__________________________________________________")
    }
    fun debugPreviewMoviesPreview(result: List<MoviePreview>){
        Log.d("PETROLERO", "__________________________________________________")
        Log.d("PETROLERO", "PREVIEW___________________________________________")
        for (res in result) Log.d("PETROLERO", "${res.Title} -> selected = ${res.selected}")
        Log.d("PETROLERO", "__________________________________________________")
    }
}