package com.example.mealme.ui.fragments

import android.os.Bundle
import kotlinx.android.synthetic.main.search_fragment.*
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.*
import android.view.inputmethod.InputMethodManager
import com.example.mealme.R
import com.example.mealme.viewmodel.MainViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class SearchFragment : DaggerFragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private val TAG = this.javaClass.name

    @Inject lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(R.layout.search_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set buttons handlers
        setButtonsListeners()
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
        fsearch_searched_text.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER) {
                fsearch_btn_search.callOnClick()
            }
            false
        }
    }

}
