package com.benjalamesta.pelidex.Activities

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.benjalamesta.pelidex.Adapters.RVPreviewAdapter
import com.benjalamesta.pelidex.Constants.AppConstants
import com.benjalamesta.pelidex.Models.MoviePreview
import com.benjalamesta.pelidex.R
import com.benjalamesta.pelidex.ViewModel.MovieViewModel
import kotlinx.android.synthetic.main.preview_add_movie.*

class NewMovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.preview_add_movie)

        val MovieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val recyclerView = rv_preview
        val moviesPreviewAdapter = RVPreviewAdapter(movies = AppConstants.emptymoviespreview,
            clickListener = { movie: MoviePreview, checky: View ->
                movie.selected = !movie.selected
                Toast.makeText(this, if (movie.selected) "Selected ${movie.Title}" else "Unselected ${movie.Title}", Toast.LENGTH_SHORT).show()
                //Snackbar.make(checky.rootView, if (movie.selected) "Selected ${movie.Title}" else "Unselected ${movie.Title}", Snackbar.LENGTH_SHORT)
                if (movie.selected) checky.visibility = View.VISIBLE else checky.visibility = View.GONE
            })

        recyclerView.apply {
            adapter = moviesPreviewAdapter
            setHasFixedSize(true)
            this.layoutManager = layoutManager
        }

        MovieViewModel.getMovieListVM().observe(this, Observer { result ->
            moviesPreviewAdapter.changeDataSet(result)
        })

        bt_search.setOnClickListener {
            val movieNameQuery = et_search.text.toString()
            if (movieNameQuery.isNotEmpty() && movieNameQuery.isNotBlank()) {
                MovieViewModel.fetchMovie(movieNameQuery)
                MovieViewModel.getMovieListVM().observe(this, Observer { result ->
                    moviesPreviewAdapter.changeDataSet(result)
                })
            }
        }

        bt_cancel.setOnClickListener {clearView(et_search, moviesPreviewAdapter)}

        bt_add_preview.setOnClickListener {
            val thenownow = MovieViewModel.getMovieListVM().value
            val selectedMovies = thenownow?.filter { it.selected } ?: AppConstants.emptymoviespreview

            for (movie in selectedMovies) {
                MovieViewModel.fetchMovieByTitle(movie.Title)
                MovieViewModel.getMovieResult().observe(this, Observer {resultMovie ->
                    MovieViewModel.insert(resultMovie)
                })
            }
            clearView(et_search, moviesPreviewAdapter)

            setResult(Activity.RESULT_OK)
            finish()
        }

    }

    fun clearView(et: EditText, adapter: RVPreviewAdapter){
        et.text.clear()
        adapter.changeDataSet(AppConstants.emptymoviespreview)
    }
}