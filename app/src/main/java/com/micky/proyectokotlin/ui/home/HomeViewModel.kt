package com.micky.proyectokotlin.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.micky.proyectokotlin.modelo.Videojuego

class HomeViewModel : ViewModel() {

    // Lista de videojuegos observable
  /* private val _videojuegos = MutableLiveData<List<Videojuego>>()
    val videojuegos: LiveData<List<Videojuego>> = _videojuegos*/

    private val _videojuegos = MutableLiveData<List<Videojuego>>(emptyList())
    val videojuegos: LiveData<List<Videojuego>> get() = _videojuegos

    init {
        // Inicializar con algunos videojuegos de ejemplo
        _videojuegos.value = listOf(
            Videojuego("Ark Survival Evolve", "Despiertas en la orilla de una isla misteriosa en la que debes aprender a sobrevivir.", "ark","Supervivencia"),
            Videojuego("Fifa 2025", "Forma equipo con tus colegas en tus modos favoritos con el nuevo Rush de 5c5 y triunfa gracias a un control táctico ", "fifa","Deportes"),
            Videojuego("Tetris", "El Tetris es un juego de puzle centrado en elegir la forma idónea de las piezas que van apareciendo en la pantalla con el fin de conseguir que todas encajen", "tetris","rompecabezas"),
            Videojuego("The Legend of Zelda: Breath of the Wild", "Explora un vasto mundo abierto lleno de misterios, enemigos y desafíos en esta aventura épica.", "zelda_breath", "Aventura"),
            Videojuego("Minecraft", "Crea, explora y sobrevive en un mundo generado aleatoriamente hecho de bloques.", "minecraft", "Construcción"),
            Videojuego("Call of Duty: Modern Warfare", "Enfréntate a intensos combates en una experiencia cinematográfica llena de acción.", "cod_modern_warfare", "Acción"),
            Videojuego("Stardew Valley", "Vive la experiencia de ser un granjero en un tranquilo pueblo, cultivando, pescando y relacionándote con los vecinos.", "stardew_valley", "Simulación"),
            Videojuego("Hollow Knight", "Explora un mundo subterráneo lleno de insectos, secretos y desafíos en esta aventura metroidvania.", "hollow_knight", "Plataformas"),
            Videojuego("Elden Ring", "Sumérgete en un vasto mundo de fantasía oscura creado por Hidetaka Miyazaki y George R.R. Martin.", "elden_ring", "RPG"),
            Videojuego("Among Us", "Colabora con tu equipo o sabotea sus esfuerzos como impostor en este divertido juego de deducción social.", "among_us", "Multijugador"),
            Videojuego("Animal Crossing: New Horizons", "Construye y personaliza tu propia isla, interactuando con adorables vecinos animales.", "animal_crossing", "Casual"),
            Videojuego("Overwatch", "Compite en equipos en este dinámico shooter basado en héroes con habilidades únicas.", "overwatch", "Shooter"),
            Videojuego("Fortnite", "Sobrevive y lucha hasta ser el último en pie en este popular battle royale.", "fortnite", "Battle Royale"),
            Videojuego("Resident Evil Village", "Enfréntate al terror en un pueblo lleno de horrores en esta entrega de la clásica saga.", "resident_evil_village", "Terror"),
            Videojuego("Hades", "Desafía al dios de los muertos en este roguelike lleno de acción y narrativa.", "hades", "Roguelike"),
            Videojuego("Red Dead Redemption 2", "Vive la historia de un forajido en el ocaso del Salvaje Oeste.", "red_dead_redemption", "Mundo Abierto"),
            Videojuego("The Sims 4", "Crea y controla a tus propios personajes en un mundo virtual lleno de posibilidades.", "the_sims_4", "Simulación"),
            Videojuego("Cyberpunk 2077", "Sumérgete en un mundo futurista lleno de tecnología avanzada y peligrosas intrigas.", "cyberpunk_2077", "RPG")
        )
    }

    // Función para agregar un nuevo videojuego a la lista
    fun agregarVideojuego(videojuego: Videojuego) {
        val nuevaLista = _videojuegos.value?.toMutableList() ?: mutableListOf()
        nuevaLista.add(videojuego)
        _videojuegos.value = nuevaLista
    }


    // Método para eliminar videojuegos
    fun eliminarVideojuego(videojuego: Videojuego) {
        // Crear una nueva lista sin el videojuego a eliminar
        val listaActual = _videojuegos.value?.toMutableList() ?: mutableListOf()
        listaActual.remove(videojuego)
        // Asignar la lista actualizada
        _videojuegos.value = listaActual
    }

    // Método para actualizar un videojuego existente
    fun actualizarVideojuego(videojuegoOriginal: Videojuego, videojuegoActualizado: Videojuego) {
        val listaActual = _videojuegos.value?.toMutableList() ?: mutableListOf()

        // Buscar el videojuego original y reemplazarlo con el nuevo
        val index = listaActual.indexOf(videojuegoOriginal)
        if (index != -1) {
            listaActual[index] = videojuegoActualizado
        }

        // Asignar la lista actualizada
        _videojuegos.value = listaActual
    }
}
