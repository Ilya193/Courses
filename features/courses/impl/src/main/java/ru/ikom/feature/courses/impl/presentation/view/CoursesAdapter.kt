package ru.ikom.feature.courses.impl.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.ikom.feature.courses.impl.R
import ru.ikom.feature.courses.impl.presentation.component.model.CourseUi
import ru.ikom.ui.compat.getViewById

class CoursesAdapter : ListAdapter<CourseUi, CoursesAdapter.ViewHolder>(DiffCourses()) {

    class ViewHolder(
        private val rootView: View
    ) : RecyclerView.ViewHolder(rootView) {

        private val courseName = rootView.getViewById<TextView>(R.id.course_name)
        private val courseDescription = rootView.getViewById<TextView>(R.id.course_description)
        private val price = rootView.getViewById<TextView>(R.id.course_price)

        private val containerRate = rootView.getViewById<LinearLayout>(R.id.container_rate)
        private val iconRate = containerRate.getViewById<ImageView>(R.id.icon_rate)
        private val rate = containerRate.getViewById<TextView>(R.id.rate)

        private val courseStartDate = rootView.getViewById<TextView>(R.id.course_start_date)

        fun bind(item: CourseUi) {
            courseName.text = item.title
            courseDescription.text = item.text
            price.text = item.price
            rate.text = item.rate
            courseStartDate.text = item.startDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_course_content, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

private class DiffCourses : DiffUtil.ItemCallback<CourseUi>() {

    override fun areItemsTheSame(oldItem: CourseUi, newItem: CourseUi): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: CourseUi, newItem: CourseUi): Boolean =
        oldItem == newItem
}