package com.benjalamesta.pelidex.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjalamesta.pelidex.Models.MoviePreview
import com.benjalamesta.pelidex.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.cardview_preview.view.*
import kotlinx.android.synthetic.main.cardview_preview.view.tv_rating_on_preview
import kotlinx.android.synthetic.main.movie_viewer.view.*

class RVPreviewAdapter(var movies: List<MoviePreview>, val clickListener: (MoviePreview, View) -> Unit ) : RecyclerView.Adapter<RVPreviewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_preview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(movies[position] , clickListener)

    fun changeDataSet(newMovieList: List<MoviePreview>) {
        movies = newMovieList
        notifyDataSetChanged()
    }

    class ViewHolder(item: View): RecyclerView.ViewHolder(item){
        fun bind(movie: MoviePreview, clickListener: (MoviePreview, View) -> Unit) = with(itemView){
            Glide.with(itemView.context)
                .load(movie.Poster)
                .placeholder(R.drawable.ic_launcher_background)
                .into(im_on_preview)
            tv_rating_on_preview.text = movie.imdbRating
            tv_title_on_preview.text = movie.Title
            tv_preview_on_year.text = movie.Year
            if (movie.selected) preview_selected_cv.visibility = View.VISIBLE else preview_selected_cv.visibility = View.GONE
            this.setOnClickListener { clickListener(movie, preview_selected_cv) }
        }
    }
}