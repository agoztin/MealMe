package com.example.mealme.di

import com.example.mealme.App
import com.example.mealme.di.viewmodel.ViewModelBuilder
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        DaggerBuilder::class,
        AppModule::class,
        ViewModelBuilder::class,
        DbModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {
    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<App?>

//    @Component.Factory
//    interface Factory {
//        fun create(@BindsInstance applicationContext: Context): AppComponent
//    }
}