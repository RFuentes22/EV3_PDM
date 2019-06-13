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
import com.benjalamesta.pelidex.ViewModel.MovieViewModel
import kotlinx.android.synthetic.main.preview_add_movie.*
import android.net.ConnectivityManager
import android.content.Context


class NewMovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.benjalamesta.pelidex.R.layout.preview_add_movie)

        val MovieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val recyclerView = rv_preview
        val moviesPreviewAdapter = RVPreviewAdapter(movies = AppConstants.emptymoviespreview as List<MoviePreview>,
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

        bt_search_movie_preview.setOnClickListener {
            val movieNameQuery = et_search_movie_preview.text.toString()
            if (movieNameQuery.isNotEmpty() && movieNameQuery.isNotBlank() && isNetworkAvailable()) {
                MovieViewModel.fetchMovie(movieNameQuery)
                MovieViewModel.getMovieListVM().observe(this, Observer { result ->
                    moviesPreviewAdapter.changeDataSet(result)
                })
            }else{
                Toast.makeText(this, "you cant search without internet connection" , Toast.LENGTH_LONG).show()
            }
        }

        bt_cancel_preview.setOnClickListener {clearView(et_search_movie_preview, moviesPreviewAdapter)}

        bt_add_preview.setOnClickListener {
            val thenownow = MovieViewModel.getMovieListVM().value
            val selectedMovies = thenownow?.filter { it.selected } ?: AppConstants.emptymoviespreview

            for (movie in selectedMovies) {
                MovieViewModel.fetchMovieByTitle(movie.Title)
                MovieViewModel.getMovieResult().observe(this, Observer {resultMovie ->
                    MovieViewModel.insert(resultMovie)
                })
            }
            clearView(et_search_movie_preview, moviesPreviewAdapter)

            setResult(Activity.RESULT_OK)
            finish()
        }

    }

    fun clearView(et: EditText, adapter: RVPreviewAdapter){
        et.text.clear()
        adapter.changeDataSet(AppConstants.emptymoviespreview)
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }


}