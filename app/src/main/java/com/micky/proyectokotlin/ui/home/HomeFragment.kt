package com.micky.proyectokotlin.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.micky.proyectokotlin.R
import com.micky.proyectokotlin.adapter.VideojuegoAdapter
import com.micky.proyectokotlin.databinding.FragmentHomeBinding
import com.micky.proyectokotlin.modelo.Videojuego

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()

    private val _videojuegos = MutableLiveData<List<Videojuego>>(emptyList())
    val videojuegos: LiveData<List<Videojuego>> get() = _videojuegos

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.recyclerViewVideojuegos
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Adaptador para el RecyclerView
        val adapter = VideojuegoAdapter(
            mutableListOf(),
            { videojuego -> mostrarFormularioEdicion(videojuego) },
            { videojuego -> confirmarEliminacion(videojuego) }

        )
        recyclerView.adapter = adapter

        // Observar cambios en la lista de videojuegos
        homeViewModel.videojuegos.observe(viewLifecycleOwner) { videojuegos ->
            adapter.apply {
                videojuegosList.clear()
                videojuegosList.addAll(videojuegos)
                notifyDataSetChanged()
            }
        }

        return root
    }

    private fun confirmarEliminacion(videojuego: Videojuego) {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirmar eliminación")
            .setMessage("¿Estás seguro de que deseas eliminar el videojuego \"${videojuego.nombre}\"?")
            .setPositiveButton("Sí") { _, _ ->
                homeViewModel.eliminarVideojuego(videojuego)
                Toast.makeText(requireContext(), "Videojuego eliminado", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun mostrarFormularioEdicion(videojuego: Videojuego) {
        val inflater = LayoutInflater.from(requireContext())
        val dialogView = inflater.inflate(R.layout.dialog_editar_videojuego, null)

        val nombreEditText = dialogView.findViewById<EditText>(R.id.editTextNombre)
        val descripcionEditText = dialogView.findViewById<EditText>(R.id.editTextDescripcion)
        val imagenEditText = dialogView.findViewById<EditText>(R.id.editTextImagen)
        val categoriaEditText = dialogView.findViewById<EditText>(R.id.editTextCategoria)

        nombreEditText.setText(videojuego.nombre)
        descripcionEditText.setText(videojuego.descripcion)
        imagenEditText.setText(videojuego.imagen)
        categoriaEditText.setText(videojuego.categoria)

        AlertDialog.Builder(requireContext())
            .setTitle("Editar Videojuego")
            .setView(dialogView)
            .setPositiveButton("Guardar") { _, _ ->
                val nombreNuevo = nombreEditText.text.toString()
                val descripcionNueva = descripcionEditText.text.toString()
                val imagenNueva = imagenEditText.text.toString()
                val categoriaNueva = categoriaEditText.text.toString()

                if (nombreNuevo.isNotBlank() && descripcionNueva.isNotBlank() && imagenNueva.isNotBlank() && categoriaNueva.isNotBlank()) {
                    val videojuegoEditado = Videojuego(nombreNuevo, descripcionNueva, imagenNueva, categoriaNueva)

                    homeViewModel.actualizarVideojuego(videojuego, videojuegoEditado)

                    Toast.makeText(requireContext(), "Videojuego editado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
