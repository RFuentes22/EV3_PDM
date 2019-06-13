package com.benjalamesta.pelidex.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.benjalamesta.pelidex.Models.Movie
import com.benjalamesta.pelidex.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movie_viewer.view.*

class MainContentFragment: Fragment(){

    var movie = Movie()

    companion object {
        fun newInstance(data: Movie): MainContentFragment {
            return MainContentFragment().apply {
                movie = data
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.movie_viewer, container, false)

        bindData(view, movie)

        return view
    }

    fun bindData(view: View, data: Movie){
        Glide.with(this)
            .load(data.Poster)
            .placeholder(R.drawable.ic_launcher_background)
            .into(view.iv_image_viewer)

        view.tv_movie_title.text = data.Title
        view.tv_movie_synopsis.text = data.Plot
        view.tv_movie_director.text = data.Director
        view.tv_movie_actors.text = data.Actors
        view.tv_movie_genre.text = data.Genre
        view.released_viewer.text = data.Released
        view.tv_rating_on_preview.text = data.imdbRating
    }

}
