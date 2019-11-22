package com.example.mealme.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealme.model.Meal
import com.example.mealme.ui.adapters.MealAdapter
import android.graphics.Color
import android.view.*
import androidx.lifecycle.ViewModelProvider
import com.example.mealme.R
import com.example.mealme.viewmodel.MainViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.results_fragment.*
import javax.inject.Inject


class ResultsFragment(private val type: TYPE) : DaggerFragment() {

    companion object {
        enum class TYPE {
            FAVOURITES, SEARCH
        }

        fun newInstance(type: TYPE): ResultsFragment = ResultsFragment(type)
    }

    private val TAG = this.javaClass.name
    private var mealsList = ArrayList<Meal>()
    private lateinit var mealAdapter: MealAdapter

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        // Set the observers
        setObservers()

        mealAdapter = MealAdapter(mealsList, object: MealAdapter.OnItemClickListener {
            override fun OnItemClick(item: Meal) {
                viewModel.selectMeal(item)

                activity?.supportFragmentManager
                    ?.beginTransaction()
                    ?.replace(R.id.container, DetailFragment.newInstance())
                    ?.addToBackStack(null)
                    ?.commit()
            }
        })

        return inflater.inflate(R.layout.results_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initially hides everything
        showResults(false)
        // Set type of result
        when(type) {
            TYPE.FAVOURITES -> {
                fresults.setBackgroundColor(Color.GRAY)
                fresults.setPadding(16, 16, 16, 16)
                viewModel.loadFavouritesMeals()
                fresults_title.text = "FAVOURITES"
            }
            TYPE.SEARCH -> {
                viewModel.loadSearchResult()
                fresults_title.text = "SEARCH RESULT"
            }
        }
        // Set buttons handlers
        setButtonsListeners()
        // RecyclerView adapter
        fresults_recyclerview.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mealAdapter
        }
    }


    private fun setObservers() {
        viewModel.getMealsLiveData().observe(this, Observer { meals ->
            if (meals == null) {
                showResults(false)
            } else {
                mealsList.clear()
                mealsList.addAll(meals)
                mealAdapter.notifyDataSetChanged()

                showResults(true)
            }
        })
    }


    private fun setButtonsListeners() {

    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelSearch()
    }

    private fun showResults(show: Boolean) {
        if(show) {
            fresults_progress.visibility = View.INVISIBLE
            fresults_title.visibility = View.VISIBLE
            fresults_recyclerview.visibility = View.VISIBLE
        } else {
            fresults_progress.visibility = View.VISIBLE
            fresults_title.visibility = View.INVISIBLE
            fresults_recyclerview.visibility = View.INVISIBLE
        }
    }
}
