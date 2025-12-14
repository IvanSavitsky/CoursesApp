package com.example.coursesapp.presentation.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.example.coursesapp.R
import com.example.coursesapp.databinding.FragmentLoginBinding
import com.example.coursesapp.presentation.MainFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.vkButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, "https://vk.com/".toUri())
            startActivity(intent)
        }
        binding.okButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, "https://ok.ru/".toUri())
            startActivity(intent)
        }

        binding.emailEdittext.filters = arrayOf(CyrillicInputFilter())

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.checkValidation(
                    email = binding.emailEdittext.text.toString().trim(),
                    password = binding.paswordEdittext.text.toString().trim()
                )
            }
        }

        binding.emailEdittext.addTextChangedListener(textWatcher)
        binding.paswordEdittext.addTextChangedListener(textWatcher)
        binding.loginButton.setOnClickListener {
            if (viewModel.isValid.value) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment, MainFragment())
                    .addToBackStack("MainFragment").commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}