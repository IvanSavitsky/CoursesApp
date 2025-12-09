package com.example.coursesapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.coursesapp.R
import com.example.coursesapp.databinding.MainFragmentBinding
import com.example.coursesapp.presentation.account.AccountFragment
import com.example.coursesapp.presentation.courses.CoursesFragment
import com.example.coursesapp.presentation.favourites.FavouritesFragment

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        childFragmentManager
            .beginTransaction()
            .add(binding.mainFragment.id, CoursesFragment())
            .commit()

        binding.bottomNavigationMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    replaceFragment(CoursesFragment())
                    true
                }

                R.id.favourites -> {
                    replaceFragment(FavouritesFragment())
                    true
                }

                R.id.account -> {
                    replaceFragment(AccountFragment())
                    true
                }

                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(binding.mainFragment.id, fragment)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}