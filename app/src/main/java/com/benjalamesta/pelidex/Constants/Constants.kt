package com.benjalamesta.pelidex.Constants

import com.benjalamesta.pelidex.BuildConfig
import com.benjalamesta.pelidex.Models.Movie

object Constants {

    val ombdApiKey = BuildConfig.OMBD_API_KEY
    val ADD_TASK_REQUEST = 1
    val emptymoviespreview = ArrayList<MoviePreview>()
    val emptymovies = ArrayList<Movie>()
}