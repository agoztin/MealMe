package com.example.mealme.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.mealme.R
import com.example.mealme.ui.fragments.ResultsFragment
import com.example.mealme.ui.fragments.SearchFragment
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

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
            R.id.menu_action_fab -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, ResultsFragment.newInstance(ResultsFragment.Companion.TYPE.FAVOURITES))
                    .addToBackStack(null)
                    .commit()
            }
            R.id.menu_action_new -> {
                startActivity(Intent(this, NewMealActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
