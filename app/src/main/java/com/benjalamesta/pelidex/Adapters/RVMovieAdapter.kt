package com.benjalamesta.pelidex.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjalamesta.pelidex.Models.Movie
import com.benjalamesta.pelidex.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.cardview_movie.view.*

class RVMovieAdapter (var movies: List<Movie>, val clickListener: (Movie) -> Unit) : RecyclerView.Adapter<RVMovieAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_movie, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(movies[position], clickListener)

    fun changeDataSet(newMovieList: List<Movie>) {
        movies = newMovieList
        notifyDataSetChanged()
    }

    class ViewHolder(item: View): RecyclerView.ViewHolder(item){
        fun bind(movie: Movie, clickListener: (Movie) -> Unit) = with(itemView){
            Glide.with(itemView.context)
                .load(movie.Poster)
                .placeholder(R.drawable.ic_launcher_background)
                .into(iv_movie_logo)
            tv_movie_title.text = movie.Title
            tv_movie_synopsis.text = movie.Plot
            tv_rating_rv.text = movie.imdbRating
            tv_movie_runtime.text = movie.Runtime
            this.setOnClickListener { clickListener(movie) }
        }
    }

}