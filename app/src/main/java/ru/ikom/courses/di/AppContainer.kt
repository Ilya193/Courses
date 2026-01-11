package ru.ikom.courses.di

import ru.ikom.feature_root.RootFeatureScreen

interface AppContainer {
    fun provideRootFeatureScreen(): RootFeatureScreen
}