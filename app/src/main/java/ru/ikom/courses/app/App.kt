package ru.ikom.courses.app

import android.app.Application
import ru.ikom.courses.di.AppContainer
import ru.ikom.courses.di.DefaultAppContainer

class App : Application() {

    val appContainer: AppContainer by lazy {
        DefaultAppContainer(this)
    }
}