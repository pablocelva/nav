package com.example.nav.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.nav.R
import com.example.nav.databinding.FragmentSuccessBinding

class SuccessFragment : Fragment(R.layout.fragment_success) {

    private var _binding: FragmentSuccessBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSuccessBinding.bind(view)

        binding.btnBackToLogin.setOnClickListener {
            // Navega de regreso al Login y limpia el historial de navegación
            findNavController().navigate(R.id.action_successFragment_to_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}