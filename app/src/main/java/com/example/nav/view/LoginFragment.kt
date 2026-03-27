package com.example.nav.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.nav.R
import com.example.nav.databinding.FragmentLoginBinding
import com.example.nav.viewModel.LoginViewModel
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Verificar si ya hay una sesión activa
        if (viewModel.isUserLoggedIn()) {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            return
        }

        setupListeners()
        observeUIState()
    }

    private fun setupListeners() {
        with(binding) {
            usernameEditText.doAfterTextChanged { text ->
                viewModel.onUsernameChanged(text?.toString().orEmpty())
            }

            passwordEditText.doAfterTextChanged { text ->
                viewModel.onPasswordChanged(text?.toString().orEmpty())
            }

            loginButton.setOnClickListener {
                viewModel.login(
                    onSuccess = {
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    },
                    onError = { message ->
                        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                    }
                )
            }

            registerButton.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }

    private fun observeUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.usernameTextInputLayout.error = state.usernameError
                    binding.passwordTextInputLayout.error = state.passwordError
                    
                    // El botón solo se habilita si el formulario es válido y no hay una petición en curso
                    binding.loginButton.isEnabled = state.isFormValid && !state.isLoading

                    binding.loginButton.text =
                        if (state.isLoading) "Iniciando sesión..." else "Iniciar Sesión"
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
