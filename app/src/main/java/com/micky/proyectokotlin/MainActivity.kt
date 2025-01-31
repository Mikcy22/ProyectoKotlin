package com.micky.proyectokotlin

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.micky.proyectokotlin.controller.VideojuegoController
import com.micky.proyectokotlin.databinding.ActivityMainBinding
import com.micky.proyectokotlin.modelo.Videojuego
import com.micky.proyectokotlin.ui.home.HomeViewModel



class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val videojuegoController = VideojuegoController()

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)


        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )

        
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        binding.appBarMain.fab.setOnClickListener {
            showAddVideojuegoDialog()
        }










































    }











    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = binding.drawerLayout
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }



    private fun showAddVideojuegoDialog() {
        // Inflar el diseño del diálogo
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_videojuego, null)

        // Obtener referencias a los elementos de la vista
        val editTextNombre = dialogView.findViewById<EditText>(R.id.editTextNombre)
        val editTextDescripcion = dialogView.findViewById<EditText>(R.id.editTextDescripcion)
        val editTextImagen = dialogView.findViewById<EditText>(R.id.editTextImagen)
        val editTextCategoria = dialogView.findViewById<EditText>(R.id.editTextCategoria)
        val btnAgregar = dialogView.findViewById<Button>(R.id.btnAgregar)

        // Crear el diálogo
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Añadir Videojuego")
        val dialog = dialogBuilder.create()

        // Configurar el botón "Agregar"
        btnAgregar.setOnClickListener {
            val nombre = editTextNombre.text.toString().trim()
            val descripcion = editTextDescripcion.text.toString().trim()
            val imagen = editTextImagen.text.toString().trim()
            val categoria = editTextCategoria.text.toString().trim()

            if (nombre.isEmpty() || descripcion.isEmpty() || imagen.isEmpty() || categoria.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                // Lógica para manejar los datos del videojuego
                Toast.makeText(
                    this,
                    "Videojuego agregado:\n$nombre ($categoria)",
                    Toast.LENGTH_SHORT
                ).show()


                // Crear una instancia de Videojuego
                val nuevo = Videojuego(nombre, descripcion, imagen, categoria)

                // Llamar al controlador para agregar el videojuego
                //videojuegoController.addvideojuego(videojuego)

                homeViewModel.agregarVideojuego(nuevo)


                // Observa los cambios en la lista para verificar si la función está funcionando
                homeViewModel.videojuegos.observe(this) { lista ->
                    println("Lista de videojuegos actualizada: $lista")
                }


                // Confirmar acción
                Toast.makeText(this, "Videojuego agregado: ${nuevo.nombre}", Toast.LENGTH_SHORT).show()


                // Cerrar el diálogo
                dialog.dismiss()
            }
        }

        // Mostrar el diálogo
        dialog.show()
    }





}




