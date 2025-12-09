package com.example.coursesapp.presentation.courses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.coursesapp.R
import com.example.coursesapp.presentation.courseAdapterDelegate
import com.example.coursesapp.data.LoadingState
import com.example.coursesapp.databinding.FragmentCoursesBinding
import com.example.coursesapp.domain.courses.Course
import com.example.coursesapp.presentation.course.CourseFragment
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class CoursesFragment : Fragment() {
    private var _binding: FragmentCoursesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CoursesViewmodel by viewModel()
    private val adapter = ListDelegationAdapter(
        courseAdapterDelegate(
            onToggleFavoriteClick = { course: Course ->
                viewModel.toggleFavorite(course)
            },
            onItemClick = {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, CourseFragment())
                    .addToBackStack("CourseFragment").commit()
            }
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoursesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter

        binding.sortButton.setOnClickListener {
            viewModel.sortByDate()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect {
                    if (it.loadingState is LoadingState.Loaded<List<Course>>) {
                        adapter.items = it.loadingState.value
                        adapter.notifyDataSetChanged()
                    } else if (it.loadingState is LoadingState.Error) {
                        requireActivity().runOnUiThread {
                            Toast.makeText(requireActivity(), "Ошибка сети", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}