package com.example.mealme.ui.fragments

import android.app.AlertDialog
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
import com.example.mealme.util.ListOrder
import com.example.mealme.viewmodel.MainViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.dialog_sort.*
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


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        viewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        // Set the observers
        setObservers()

        // Initialize RecyclerView adapter
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
                fresults.setBackgroundColor(Color.DKGRAY)
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

                sortMealsList()
                showResults(true)
            }
        })
    }


    private fun setButtonsListeners() {
        fresults_sort.setOnClickListener { showSortDialog() }
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


    fun showSortDialog() {
        val dialog = AlertDialog.Builder(ContextThemeWrapper(context, R.style.AppTheme))
            .setView(R.layout.dialog_sort)
            .create()

        with(dialog) {
            show()

            // Restore state
            with(viewModel.listOrder) {
                when(field) {
                    ListOrder.FIELD.NAME -> dsort_gf_name.isChecked = true
                    ListOrder.FIELD.CATEGORY -> dsort_gf_category.isChecked = true
                    else -> dsort_gf_shuffle.isChecked = true
                }

                if (field != ListOrder.FIELD.SHUFFLE) {
                    when (order) {
                        ListOrder.ORDER.ASC -> dsort_gd_asc.isChecked = true
                        ListOrder.ORDER.DESC -> dsort_gd_desc.isChecked = true
                    }
                }
            }

            // Set listeners
            dsort_gf_shuffle.setOnCheckedChangeListener { _, isChecked ->
                with(dsort_gd_asc) {
                    isEnabled = !isChecked
                    setChecked(!isChecked)
                }

                with(dsort_gd_desc) {
                    isEnabled = !isChecked
                    if (isChecked) {
                        setChecked(false)
                    }
                }

                viewModel.listOrder.field = ListOrder.FIELD.SHUFFLE
            }

            dsort_btn_sort.setOnClickListener {
                viewModel.listOrder.field = when(dsort_group_field.checkedRadioButtonId) {
                    R.id.dsort_gf_name -> ListOrder.FIELD.NAME
                    R.id.dsort_gf_category -> ListOrder.FIELD.CATEGORY
                    else -> ListOrder.FIELD.SHUFFLE
                }

                viewModel.listOrder.order = when(dsort_group_direction.checkedRadioButtonId) {
                    R.id.dsort_gd_desc -> ListOrder.ORDER.DESC
                    else -> ListOrder.ORDER.ASC
                }

                sortMealsList()

                dismiss()
            }
        }
    }


    private fun sortMealsList() {
        with(viewModel.listOrder) {
            when(field) {
                ListOrder.FIELD.NAME -> mealsList.sortBy { it.name }
                ListOrder.FIELD.CATEGORY -> mealsList.sortBy { it.category }
                else -> mealsList.shuffle()
            }

            if (order == ListOrder.ORDER.DESC)
                mealsList.reverse()
        }
        mealAdapter.notifyDataSetChanged()
    }
}
