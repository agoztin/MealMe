package com.example.mealme.ui.activities

import com.example.mealme.ui.fragments.DetailFragment
import com.example.mealme.ui.fragments.ResultsFragment
import com.example.mealme.ui.fragments.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector
    abstract fun bindSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun bindResultsFragment(): ResultsFragment

    @ContributesAndroidInjector
    abstract fun bindDetailFragment(): DetailFragment
}
