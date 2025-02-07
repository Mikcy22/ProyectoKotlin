package com.micky.proyectokotlin

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
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

        // Configurar el listener del menú de navegación
        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_logout -> {
                    borrar_sesion() // Llamar a la función para cerrar sesión
                    true
                }
                else -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    navController.navigate(item.itemId)
                    true
                }
            }
        }

        binding.appBarMain.fab.setOnClickListener {
            showAddVideojuegoDialog()
        }
    }

    private fun borrar_sesion() {
        // 1. Cerrar sesión en Firebase
        FirebaseAuth.getInstance().signOut()

        // Verificar si realmente se cerró sesión
        if (FirebaseAuth.getInstance().currentUser == null) {
            Log.d("Logout", "Usuario ha cerrado sesión correctamente")
        } else {
            Log.e("Logout", "Error al cerrar sesión en Firebase")
        }


        // 2. Borrar las preferencias compartidas
        val sharedPreferences = getSharedPreferences(LoginActivity.Global.preferecias_compartidas, Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()

        // 3. Finalizar todas las actividades y abrir LoginActivity
        finishAffinity() // Cierra todas las actividades de la app

        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

        finish() // Finaliza la actividad actual (por si acaso)
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
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_videojuego, null)
        val editTextNombre = dialogView.findViewById<EditText>(R.id.editTextNombre)
        val editTextDescripcion = dialogView.findViewById<EditText>(R.id.editTextDescripcion)
        val editTextImagen = dialogView.findViewById<EditText>(R.id.editTextImagen)
        val editTextCategoria = dialogView.findViewById<EditText>(R.id.editTextCategoria)
        val btnAgregar = dialogView.findViewById<Button>(R.id.btnAgregar)

        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Añadir Videojuego")
        val dialog = dialogBuilder.create()

        btnAgregar.setOnClickListener {
            val nombre = editTextNombre.text.toString().trim()
            val descripcion = editTextDescripcion.text.toString().trim()
            val imagen = editTextImagen.text.toString().trim()
            val categoria = editTextCategoria.text.toString().trim()

            if (nombre.isEmpty() || descripcion.isEmpty() || imagen.isEmpty() || categoria.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val nuevo = Videojuego(nombre, descripcion, imagen, categoria)
                homeViewModel.agregarVideojuego(nuevo)
                Toast.makeText(this, "Videojuego agregado: ${nuevo.nombre}", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }

        dialog.show()
    }
}