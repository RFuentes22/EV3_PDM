package com.benjalamesta.pelidex.Fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.benjalamesta.pelidex.Adapters.RVMovieAdapter
import com.benjalamesta.pelidex.Constants.AppConstants
import com.benjalamesta.pelidex.Models.Movie
import com.benjalamesta.pelidex.R
import com.benjalamesta.pelidex.ViewModel.MovieViewModel
import kotlinx.android.synthetic.main.movies_list_fragment.view.*
import java.lang.RuntimeException

class MainListFragment : Fragment(){

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var moviesAdapter: RVMovieAdapter
    var listener : ClickedMovieListener? = null

    interface ClickedMovieListener{
        fun managePortraitItemClick(movie: Movie)
        fun managedLandscapeItemClick(movie: Movie)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ClickedMovieListener) {
            listener = context
        } else {
            throw RuntimeException("implementar la interfaz clikedMovieListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.movies_list_fragment, container, false) //TODO[poner el xml de list fragment]

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

        initRecyclerView(resources.configuration.orientation, view)

        movieViewModel.getAll().observe(this, Observer { result ->
            moviesAdapter.changeDataSet(result)
        })

        return view
    }

    fun initRecyclerView(orientation: Int, container: View) {
        val linearLayoutManager = LinearLayoutManager(this.context)
        val recyclerview  = container.rv_list

        moviesAdapter = if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            RVMovieAdapter(movies = AppConstants.emptymovies, clickListener = { movie: Movie -> listener?.managePortraitItemClick(movie)})
        }else {
            RVMovieAdapter(movies = AppConstants.emptymovies, clickListener = { movie: Movie -> listener?.managedLandscapeItemClick(movie)})
        }

        recyclerview.apply {
            adapter = moviesAdapter
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)

        val searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        searchView.isSubmitButtonEnabled = true

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                queryToDatabase(newText?: "N/A")
                return true
            }

        })
    }

    private fun queryToDatabase(query: String) = movieViewModel.getMovieByName("%$query%").observe(this,
        Observer { queryResult -> moviesAdapter.changeDataSet(queryResult)})

}