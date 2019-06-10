package com.benjalamesta.pelidex.Activities

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.benjalamesta.pelidex.Fragments.MainContentFragment
import com.benjalamesta.pelidex.Fragments.MainListFragment
import com.benjalamesta.pelidex.Models.Movie
import com.benjalamesta.pelidex.R

class MainActivity : AppCompatActivity(), MainListFragment.ClickedMovieListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFragments()
    }

    fun initFragments(){
        mainFragment = MainListFragment()

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            resource = R.id.portrait_main_place_holder
        }
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            showContent(R.id.land_main_movieviewer_ph, Movie())
            resource =R.id.land_main_place_holder
        }

        val intent = Intent(this, NewMovieActivity::class.java)
        main_add_button.setOnClickListener { startActivityForResult(intent , AppConstants.ADD_TASK_REQUEST) }

        changeFragment(resource, mainFragment)
    }

    private fun changeFragment(id: Int, frag: Fragment){ supportFragmentManager.beginTransaction().replace(id, frag).commit() }

    private fun showContent(id_placeholder: Int, movie: Movie) {
        mainContentFragment = MainContentFragment.newInstance(movie)
        changeFragment(id_placeholder, mainContentFragment)
    }

    override fun managePortraitItemClick(movie: Movie) = showContent(R.id.portrait_main_place_holder, movie)

    override fun managedLandscapeItemClick(movie: Movie) = showContent(R.id.land_main_movieviewer_ph, movie)
}
