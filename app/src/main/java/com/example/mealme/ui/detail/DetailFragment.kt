package com.example.mealme.ui.detail

import android.content.ContextWrapper
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.mealme.R
import com.example.mealme.model.Meal
import android.view.*
import android.widget.TableRow
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.widget.TextViewCompat
import com.example.mealme.ui.main.MainViewModel
import kotlinx.android.synthetic.main.detail_fragment.*


class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private val TAG = this.javaClass.name
    private lateinit var viewModel: MainViewModel
    private var mealInstance: Meal? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = activity?.run {
            ViewModelProviders.of(this)[MainViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        // Set the observers
        setObservers()

        return inflater.inflate(R.layout.detail_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set buttons handlers
        setButtonsListeners()
    }


    private fun setObservers() {
        viewModel.selectedMeal.observe(this, Observer<Meal> { meal ->
            fdetail_title.text = meal.name
            fdetail_instructions.text = getString(R.string.instructions)
                .plus("\n")
                .plus(meal.instructions)
                .plus("\n".repeat(6))

            meal.ingredients.forEach { mealIngredient ->
                if (!mealIngredient.name.isEmpty()) {
                    val cw = ContextThemeWrapper(context, R.style.FragmentDetail_Ingredients)
                    val measure = TextView(cw).apply { text = mealIngredient.measure }
                    val ingredient = TextView(cw).apply { text = mealIngredient.name }

                    fdetail_ingredients.addView(TableRow(context).apply {
                        addView(measure)
                        addView(ingredient)
                    })
                }
            }
            mealInstance = meal
        })
    }


    private fun setButtonsListeners() {
        fdetail_add_favorite.setOnClickListener {
            if (mealInstance != null) {
                viewModel.saveMeal(mealInstance!!)
            }
        }
    }

}
