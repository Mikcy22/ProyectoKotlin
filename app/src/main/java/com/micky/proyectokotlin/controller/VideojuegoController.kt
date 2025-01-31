package com.micky.proyectokotlin.controller

import com.micky.proyectokotlin.modelo.Videojuego

class VideojuegoController {
    private val videojuegos = mutableListOf<Videojuego>()

    fun addvideojuego(videojuego: Videojuego) {
        videojuegos.add(videojuego)
    }

    fun deletevideojuego(videojuego: Videojuego) {
        videojuegos.remove(videojuego)
    }

    fun getAllvideojuegos(): List<Videojuego> = videojuegos
}
