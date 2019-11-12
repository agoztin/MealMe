package com.example.mealme.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealme.R
import com.example.mealme.model.Meal
import com.example.mealme.ui.adapters.MealAdapter
import kotlinx.android.synthetic.main.main_fragment.*
import android.content.Context.INPUT_METHOD_SERVICE
import android.opengl.Visibility
import android.view.*
import android.view.inputmethod.InputMethodManager


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val TAG = this.javaClass.name
    private lateinit var viewModel: MainViewModel
    private var mealsList = ArrayList<Meal>()
    private var mealAdapter = MealAdapter(mealsList)

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fmain_recyclerview.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mealAdapter
        }

    }

    private fun setObservers() {
        viewModel.getSearchResult().observe(this, Observer<ArrayList<Meal>> { meals ->
            fmain_progress.visibility = View.INVISIBLE
            fmain_recyclerview.visibility = View.VISIBLE

            mealsList.clear()
            mealsList.addAll(meals ?: ArrayList())
            mealAdapter.notifyDataSetChanged()
        })
    }


    private fun setButtonsListeners() {
        // Search button
        fmain_btn_search.setOnClickListener {
            fmain_progress.visibility = View.VISIBLE
            fmain_recyclerview.visibility = View.INVISIBLE

            val searchedText = fmain_searched_text.text.toString()
            viewModel.searchMeals(searchedText)

            // Hide keyboard
            try {
                val imm = activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
                imm!!.hideSoftInputFromWindow(activity?.currentFocus?.getWindowToken(), 0)
            } catch (e: Exception) { }
        }

        // On press enter
        fmain_searched_text.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                fmain_btn_search.callOnClick()
            }
            false
        }
    }

}
