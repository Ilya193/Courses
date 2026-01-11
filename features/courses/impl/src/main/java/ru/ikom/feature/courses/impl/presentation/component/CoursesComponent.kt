package ru.ikom.feature.courses.impl.presentation.component

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.ikom.core.courses_logic.courses_domain.CoursesRepository
import ru.ikom.feature.courses.api.CoursesFeature
import ru.ikom.feature.courses.impl.di.CoursesContainer
import ru.ikom.feature.courses.impl.presentation.component.mappers.CourseMapper
import ru.ikom.feature.courses.impl.presentation.component.model.CourseUi
import ru.ikom.ui.components.BaseRootComponent

class CoursesComponent(
    private val feature: CoursesFeature,
    private val deps: CoursesDependencies,
    private val coursesContainer: CoursesContainer = CoursesContainer(),
    private val coursesRepository: CoursesRepository = deps.coursesRepository,
    private val courseMapper: CourseMapper = coursesContainer.courseMapper
) : BaseRootComponent<CoursesComponent.State, CoursesComponent.Msg, Unit>(initialState()) {

    init {
        viewModelScope.launch {
            dispatch(Msg.UpdateLoading(isLoading = true))

            coursesRepository.getCourses()
                .onSuccess {
                    val finalCourses = it.map(courseMapper::mapToUi)

                    dispatch {
                        reduce(Msg.UpdateCourses(finalCourses))
                            .reduce(Msg.UpdateLoading(isLoading = false))
                    }
                }
        }
    }

    fun onClickSorted() {
        val state = uiState.value
        val courses = state.courses

        val finalCourses = courses.sortedByDescending { it.publishDate }

        dispatch(Msg.UpdateCourses(finalCourses))
    }

    override fun State.reduce(msg: Msg): State =
        when (msg) {
            is Msg.UpdateCourses -> copy(courses = msg.courses)
            is Msg.UpdateLoading -> copy(isLoading = msg.isLoading)
        }

    data class State(
        val courses: List<CourseUi>,
        val isLoading: Boolean,
    )

    sealed interface Msg {
        class UpdateCourses(val courses: List<CourseUi>) : Msg
        class UpdateLoading(val isLoading: Boolean) : Msg
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val feature: () -> CoursesFeature,
        private val deps: CoursesDependencies
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CoursesComponent(feature(), deps) as T
        }
    }
}

private fun initialState() =
    CoursesComponent.State(
        courses = emptyList(),
        isLoading = false,
    )