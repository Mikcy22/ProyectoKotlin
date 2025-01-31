package com.micky.proyectokotlin.ui.accion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.micky.proyectokotlin.databinding.FragmentAccionBinding

class AccionFragment : Fragment() {

    private var _binding: FragmentAccionBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val accionViewModel =
            ViewModelProvider(this).get(AccionViewModel::class.java)

        _binding = FragmentAccionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textAccion
        accionViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}