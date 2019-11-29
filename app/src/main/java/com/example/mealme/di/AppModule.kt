package com.example.mealme.di

import android.app.Application
import com.example.mealme.App
import com.example.mealme.di.viewmodel.ViewModelBuilder
import dagger.Binds
import dagger.Module

@Module(includes = [
    ViewModelBuilder::class,
    DbModule::class,
    NetworkModule::class
])
abstract class AppModule {

    @Binds
    abstract fun bindApplication(app: App?): Application?

}