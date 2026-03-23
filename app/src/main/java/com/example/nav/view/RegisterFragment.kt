package com.example.nav.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.nav.R
import com.example.nav.databinding.FragmentRegisterBinding
import com.example.nav.model.RegisterUIState
import com.example.nav.viewModel.RegisterViewModel
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        with(binding) {
            fullNameEditText.addTextChangedListener { viewModel.onFullNameChanged(it.toString()) }
            emailEditText.addTextChangedListener { viewModel.onEmailChanged(it.toString()) }
            usernameEditText.addTextChangedListener { viewModel.onUsernameChanged(it.toString()) }
            passwordEditText.addTextChangedListener { viewModel.onPasswordChanged(it.toString()) }
            confirmPasswordEditText.addTextChangedListener { viewModel.onConfirmPasswordChanged(it.toString()) }
            termsCheckBox.setOnCheckedChangeListener { _, isChecked -> viewModel.onTermsChanged(isChecked) }

            createAccountButton.setOnClickListener {
                viewModel.register(
                    onSuccess = {
                        findNavController().navigate(R.id.action_registerFragment_to_successFragment)
                    },
                    onError = { error ->
                        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                    }
                )
            }

            backToLoginButton.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    updateUI(state)
                }
            }
        }
    }

    private fun updateUI(state: RegisterUIState) {
        with(binding) {
            fullNameTextInputLayout.error = state.fullNameError
            emailTextInputLayout.error = state.emailError
            usernameTextInputLayout.error = state.usernameError
            passwordTextInputLayout.error = state.passwordError
            confirmPasswordTextInputLayout.error = state.confirmPasswordError
            
            // Habilitar botón solo si el formulario es válido y no está cargando
            createAccountButton.isEnabled = state.isFormValid && !state.isLoading
            
            // Opcional: mostrar un progress bar si state.isLoading es true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
