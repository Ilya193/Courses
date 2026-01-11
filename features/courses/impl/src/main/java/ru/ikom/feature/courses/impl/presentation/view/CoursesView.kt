package ru.ikom.feature.courses.impl.presentation.view

import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.ikom.feature.courses.impl.R
import ru.ikom.feature.courses.impl.presentation.component.CoursesComponent
import ru.ikom.feature.courses.impl.presentation.component.model.CourseUi
import ru.ikom.ui.compat.getViewById
import ru.ikom.ui.diff.Value
import ru.ikom.ui.diff.onChangeReference
import ru.ikom.ui.list.BottomMarginWithLastItemDecoration

class CoursesView(
    private val root: View,
    private val component: CoursesComponent,
    private val lifecycleScope: CoroutineScope
) {

    private val binding = Binding(root)

    private val values = Values()

    private val coursesAdapter = CoursesAdapter()

    init {
        setupInsets()

        initCourses()

        binding.containerSorted.setOnClickListener { component.onClickSorted() }

        lifecycleScope.launch {
            component.state.collect {
                updateState(it)
            }
        }
    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
    }

    private fun initCourses() {
        binding.courses.adapter = coursesAdapter
        binding.courses.setHasFixedSize(true)

        val spaces = root.resources.getDimensionPixelSize(R.dimen.courses_items_space)
        val itemDecoration = BottomMarginWithLastItemDecoration(spaces)
        binding.courses.addItemDecoration(itemDecoration)
    }

    private fun updateState(state: CoursesComponent.State) {
        updateGroupContent(state)
        updateCourses(state)
        updateProgressBar(state)
    }

    private fun updateGroupContent(state: CoursesComponent.State) {
        binding.groupContent.isInvisible = state.isLoading
    }

    private fun updateCourses(state: CoursesComponent.State) {
        val courses = state.courses

        if (!values.coursesValue.onChangeReference(courses)) return

        coursesAdapter.submitList(courses)
    }

    private fun updateProgressBar(state: CoursesComponent.State) {
        binding.progressBar.isVisible = state.isLoading
    }
}

private class Values {
    val coursesValue = Value.defaultValue<List<CourseUi>>()
}

private class Binding(root: View) {

    val courses = root.getViewById<RecyclerView>(R.id.list_of_courses)
    val progressBar = root.getViewById<ProgressBar>(R.id.progress_bar)

    val containerSorted = root.getViewById<LinearLayout>(R.id.container_sorted)

    val groupContent = root.getViewById<Group>(R.id.group_content)
}