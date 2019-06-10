package com.benjalamesta.pelidex.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.benjalamesta.pelidex.Models.Movie
import com.benjalamesta.pelidex.R
import com.bumptech.glide.Glide

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

        view.collapsingtoolbarviewer.setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
        view.collapsingtoolbarviewer.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar)


        bindData(view, movie)

        return view
    }

    fun bindData(view: View, data: Movie){
        Glide.with(this)
            .load(data.Poster)
            .placeholder(R.drawable.ic_launcher_background)
            .into(view.app_bar_image_viewer)

        view.collapsingtoolbarviewer.title = data.Title
        view.plot_viewer.text = data.Plot
        view.director_viewer.text = data.Director
        view.actors_viewer.text = data.Actors
        view.genre_viewer.text = data.Genre
        view.released_viewer.text = data.Released
        view.app_bar_rating_viewer.text = data.imdbRating
    }

}
