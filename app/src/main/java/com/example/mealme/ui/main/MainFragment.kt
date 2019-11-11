package com.example.mealme.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealme.R
import com.example.mealme.model.Meal
import com.example.mealme.ui.adapters.MealAdapter
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val TAG = this.javaClass.name
    private lateinit var viewModel: MainViewModel
    private var mealsList = ArrayList<Meal>()
    private val mealAdapter = MealAdapter(mealsList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        // Set the observers
        setObservers()
        // Set buttons handlers
        setButtonsListeners()
    }

    private fun setObservers() {
        viewModel.getSearchResult().observe(this, Observer<ArrayList<Meal>> { meals ->
            mealsList = meals
            mealAdapter.notifyDataSetChanged()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fmain_recyclerview.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mealAdapter
        }
    }

    private fun setButtonsListeners() {
        // Search button
        fmain_btn_search.setOnClickListener {
            val searchedText = fmain_searched_text.text.toString()

            viewModel.searchMeals(searchedText)
        }
    }

}
