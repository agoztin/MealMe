package com.example.mealme.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import com.example.mealme.R
import com.example.mealme.ui.fragments.ResultsFragment
import com.example.mealme.ui.fragments.SearchFragment
import com.example.mealme.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.results_fragment.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SearchFragment.newInstance())
                .commitNow()
        }

        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_fab -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, ResultsFragment.newInstance(ResultsFragment.Companion.TYPE.FAVOURITES))
                    .addToBackStack(null)
                    .commit()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
