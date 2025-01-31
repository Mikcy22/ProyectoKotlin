package com.micky.proyectokotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.micky.proyectokotlin.R
import com.micky.proyectokotlin.modelo.Videojuego

class VideojuegoAdapter(
    private var videojuegos: MutableList<Videojuego>,
    private val onEditClick: (Videojuego) -> Unit,
    private val onDeleteClick: (Videojuego) -> Unit
) : RecyclerView.Adapter<VideojuegoAdapter.ViewHolder>() {

    val videojuegosList: MutableList<Videojuego>
        get() = videojuegos

    fun setVideojuegos(videojuegos: List<Videojuego>) {
        this.videojuegos.clear()
        this.videojuegos.addAll(videojuegos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.videojuego_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val videojuego = videojuegos[position]
        holder.bind(videojuego)
    }

    override fun getItemCount(): Int = videojuegos.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.videojuegoTitle)
        val descripcionTextView: TextView = itemView.findViewById(R.id.videojuegoDescription)
        val deleteButton: ImageButton = itemView.findViewById(R.id.btnEliminar)
        val editButton: Button = itemView.findViewById(R.id.btnEditar)
        val imagenImageView: ImageView = itemView.findViewById(R.id.videojuegoImage)

        fun bind(videojuego: Videojuego) {
            nombreTextView.text = videojuego.nombre
            descripcionTextView.text = videojuego.descripcion

            if (videojuego.imagen.isNotEmpty()) {
                imagenImageView.setImageResource(
                    itemView.context.resources.getIdentifier(videojuego.imagen, "drawable", itemView.context.packageName)
                )
            }

            deleteButton.setOnClickListener {
                onDeleteClick(videojuego)
            }

            editButton.setOnClickListener {
                onEditClick(videojuego)
            }
        }
    }
}
