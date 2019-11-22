package com.example.mealme.di

import android.app.Application
import com.example.mealme.App
import com.example.mealme.net.repositories.MealsRepository
import com.example.mealme.ui.activities.MainActivity
import com.example.mealme.ui.fragments.ResultsFragment
import com.example.mealme.viewmodel.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class DaggerBuilder(val app: App) {

    @Binds
    abstract fun bindApplication(app: App?): Application?

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindResultsFragment(): ResultsFragment
}