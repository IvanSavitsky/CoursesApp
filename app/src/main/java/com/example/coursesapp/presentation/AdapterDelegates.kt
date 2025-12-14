package com.example.coursesapp.presentation

import androidx.core.content.ContextCompat
import com.example.coursesapp.R
import com.example.coursesapp.databinding.CourseItemBinding
import com.example.coursesapp.domain.courses.Course
import com.example.coursesapp.domain.courses.ListItem
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun courseAdapterDelegate(
    onItemClick: () -> Unit,
    onToggleFavoriteClick: (Course) -> Unit,
) = adapterDelegateViewBinding<Course, ListItem, CourseItemBinding>(
    { layoutInflater, root ->
        CourseItemBinding.inflate(
            layoutInflater, root, false
        )
    }
) {
    binding.hasLike.setOnClickListener {
        onToggleFavoriteClick(item)
    }
    binding.root.setOnClickListener {
        onItemClick()
    }
    bind {
        binding.title.text = item.title
        binding.text.text = item.text
        binding.price.text = item.price + "₽"
        binding.rate.text = item.rate
        binding.publishDate.text = item.publishDate
        if (item.hasLike) {
            binding.hasLike.setColorFilter(ContextCompat.getColor(context, R.color.green))
        } else {
            binding.hasLike.setColorFilter(ContextCompat.getColor(context, R.color.white))
        }
    }
}