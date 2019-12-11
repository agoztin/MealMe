package com.example.mealme.ui.activities

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewParent
import android.webkit.URLUtil
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.forEach
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.mealme.R
import com.example.mealme.model.Ingredient
import com.example.mealme.model.Meal
import com.example.mealme.viewmodel.MainViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_new_meal.*
import javax.inject.Inject

class NewMealActivity : DaggerAppCompatActivity() {

    val TAG = this.javaClass.name

    @Inject
    lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_meal)

        // Set buttons handlers
        setButtonsListeners()

    }


    private fun setButtonsListeners() {

        new_meal_ingredient_1_amount.addTextChangedListener(IngredientTextWatcher(new_meal_ingredient_1_container))
        new_meal_ingredient_1.addTextChangedListener(IngredientTextWatcher(new_meal_ingredient_1_container))

        new_meal_url.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                getImage((v as EditText).text.toString())
            }
        }

        new_meal_btn_add.setOnClickListener { addNewMeal() }
    }


    private fun getImage(url: String) {
        if (URLUtil.isValidUrl(url)) {
            Glide.with(this)
                .load(url)
                .placeholder(R.drawable.ic_image)
                .into(new_meal_image)
        }
    }


    private fun addNewMeal() {
        // Check input data
        if (new_meal_name.text.isEmpty()) {
            new_meal_name.setError("Name can not be empty")
            new_meal_name.requestFocus()
            return
        }

        if (new_meal_category.text.isEmpty()) {
            new_meal_category.setError("Category can not be empty")
            new_meal_category.requestFocus()
            return
        }

        if (new_meal_tags.text.isEmpty()) {
            new_meal_tags.setError("Tags can not be empty")
            new_meal_tags.requestFocus()
            return
        }

        if (new_meal_ingredients_layout.childCount == 1) {
            val ingredient = (new_meal_ingredients_layout.getChildAt(0) as LinearLayout).getChildAt(0) as EditText
            ingredient.setError("Complete at least one ingredient")
            ingredient.requestFocus()
            return
        }

        new_meal_ingredients_layout.forEach { v: View ->
            val layout = v as LinearLayout
            val amount = layout.getChildAt(0) as TextView
            val name = layout.getChildAt(1) as TextView

            if (amount.text.isEmpty() && name.text.isNotEmpty()) {
                amount.setError("Name is not empty")
                amount.requestFocus()
                return
            }
            if (amount.text.isNotEmpty() && name.text.isEmpty()) {
                name.setError("Amount is not empty")
                name.requestFocus()
                return
            }
        }

        if (new_meal_instructions.text.isEmpty()) {
            new_meal_instructions.setError("Instructions can not be empty")
            new_meal_instructions.requestFocus()
            return
        }

        val ingredients = ArrayList<Ingredient>()

        new_meal_ingredients_layout.forEach { v ->
            val ingredientAmount = ((v as LinearLayout).getChildAt(0) as EditText).text.toString()
            val ingredientName = (v.getChildAt(1) as EditText).text.toString()
            ingredients.add(Ingredient(0, ingredientName, ingredientAmount))
        }

        for (i in ingredients.size until 20)
            ingredients.add(Ingredient())

        viewModel.postMeal(
            Meal(
                0,
                new_meal_name.text.toString(),
                new_meal_category.text.toString(),
                new_meal_tags.text.toString(),
                new_meal_instructions.text.toString(),
                new_meal_url.text.toString(),
                ingredients
            )
        ).observe(this, Observer { result ->
            AlertDialog.Builder(this)
                .setMessage(result.getOrElse { it.message })
                .setPositiveButton("OK") { dialog, _ ->
                    if (result.isSuccess) {
                        finish()
                    }
                    dialog.dismiss()
                }
                .create()
                .show()
        })
    }


    class IngredientTextWatcher(val ingredientLayout: ViewParent) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val layout = ingredientLayout.parent as LinearLayout
            val index = layout.indexOfChild(ingredientLayout as View)
            val nextIngredient = layout.getChildAt(index + 1)

            if (s!!.isNotEmpty() && nextIngredient == null) {
                // Add new ingredient
                if (layout.childCount < 20) {
                    val inflater = layout.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                    val newIngredient = inflater.inflate(R.layout.ingredient_view, null) as LinearLayout
                    newIngredient.forEach { v ->
                        (v as EditText).addTextChangedListener(IngredientTextWatcher(v.parent))
                    }
                    layout.addView(newIngredient)
                }
            }
            else if (s.isEmpty() && nextIngredient != null) {
                // Removes ingredient if it's empty
                layout.removeView(ingredientLayout)
            }

        }

    }


}
