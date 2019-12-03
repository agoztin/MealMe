package com.example.mealme.di

import android.app.Application
import com.example.mealme.App
import dagger.Binds
import dagger.Module

@Module(includes = [
    DbModule::class,
    NetworkModule::class,
    ViewModelModule::class
])
abstract class AppModule {

    @Binds
    abstract fun bindApplication(app: App?): Application?

}