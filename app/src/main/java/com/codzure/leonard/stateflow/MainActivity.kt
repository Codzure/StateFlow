package com.codzure.leonard.stateflow

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.codzure.leonard.stateflow.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null

    private val viewModel: MainViewModel by viewModels()
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        login()
        lifecycleObserver()
    }

    private fun login() {
        binding.apply {
            submitButton.setOnClickListener {
                viewModel.login(
                    username = userName.text.toString(),
                    password = password.text.toString()
                )
            }
        }

    }

    private fun lifecycleObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.loginUiState.collect {
                when (it) {
                    is MainViewModel.LoginUiState.Success -> {
                        Snackbar.make(binding.root, "Successfully logged in", Snackbar.LENGTH_LONG)
                            .show()
                        binding.apply {
                            progressBar.isVisible = false
                            submitButton.isVisible = true
                        }
                    }

                    is MainViewModel.LoginUiState.Error -> {
                        Snackbar.make(binding.root, it.message, Snackbar.LENGTH_LONG)
                            .show()

                        binding.apply {
                            progressBar.isVisible = false
                            submitButton.isVisible = true
                        }
                    }

                    is MainViewModel.LoginUiState.Loading -> {
                        binding.apply {
                            progressBar.isVisible = true
                            submitButton.isVisible = false
                        }

                    }

                    else -> {}
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}