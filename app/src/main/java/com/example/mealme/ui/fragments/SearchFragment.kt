package com.example.mealme.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealme.model.Meal
import com.example.mealme.ui.adapters.MealAdapter
import kotlinx.android.synthetic.main.search_fragment.*
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.*
import android.view.inputmethod.InputMethodManager
import com.example.mealme.R
import com.example.mealme.ui.viewmodel.MainViewModel


class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private val TAG = this.javaClass.name
    private lateinit var viewModel: MainViewModel
    private var mealsList = ArrayList<Meal>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = activity?.run {
            ViewModelProviders.of(this)[MainViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        // Set the observers
//        setObservers()

        return inflater.inflate(R.layout.search_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set buttons handlers
        setButtonsListeners()
    }


    private fun setObservers() {
        viewModel.getMealsLiveData().observe(this, Observer { meals ->

        })
    }


    private fun setButtonsListeners() {
        // Search button
        fsearch_btn_search.setOnClickListener {
            // Hide keyboard
            val imm = activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.hideSoftInputFromWindow(activity?.currentFocus?.getWindowToken(), 0)
            // Launch search
            val searchedText = fsearch_searched_text.text.toString()
            viewModel.searchMeals(searchedText)
            // Change fragment
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container, ResultsFragment.newInstance(ResultsFragment.Companion.TYPE.SEARCH))
                ?.addToBackStack(null)
                ?.commit()
        }

        // On press enter
        fsearch_searched_text.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER) {
                fsearch_btn_search.callOnClick()
            }
            false
        }
    }

}
