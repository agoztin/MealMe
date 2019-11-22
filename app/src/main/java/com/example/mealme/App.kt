package com.example.mealme

import com.example.mealme.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<App?>? = DaggerAppComponent.factory().create(this)

}