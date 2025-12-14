package com.example.coursesapp

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.coursesapp.databinding.ActivityMainBinding
import com.example.coursesapp.domain.auth.AuthRepository
import com.example.coursesapp.presentation.MainFragment
import com.example.coursesapp.presentation.login.LoginFragment
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val authRepository: AuthRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT))

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        if (savedInstanceState == null) {
            if (authRepository.isAuthorized() == true) {
                supportFragmentManager
                    .beginTransaction()
                    .add(binding.fragment.id, MainFragment())
                    .commit()
            } else {
                supportFragmentManager
                    .beginTransaction()
                    .add(binding.fragment.id, LoginFragment())
                    .commit()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}