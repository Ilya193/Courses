package ru.ikom.courses.di.platform

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.ikom.core.common.platform.AppDispatchers

class AndroidAppDispatchers : AppDispatchers {
    override val main: CoroutineDispatcher = Dispatchers.Main

    override val io: CoroutineDispatcher = Dispatchers.IO

    override val default: CoroutineDispatcher = Dispatchers.Default

    override val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
}