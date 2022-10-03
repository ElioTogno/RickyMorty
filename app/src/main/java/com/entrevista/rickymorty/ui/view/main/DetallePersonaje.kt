package com.entrevista.rickymorty.ui.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.load
import com.entrevista.rickymorty.R
import com.entrevista.rickymorty.databinding.ActivityDetallePersonajeBinding

class DetallePersonaje : AppCompatActivity() {
    private lateinit var binding: ActivityDetallePersonajeBinding


    //Activity de Detalle personaje
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallePersonajeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras


        binding.outputGenero.text = extras?.getString("id")
        binding.outputDetailNombre.text = extras?.getString("nombre")
        binding.outputDetailImagen.load(extras?.getString("imagen"))
        binding.outputEstado.text = extras?.getString("estado")
        binding.outputGenero.text = getString(R.string.Genero, extras?.getString("genero"))
        binding.outputOrigen.text = getString(R.string.Origen, extras?.getString("origen"))
        binding.outputUbicacion.text = getString(R.string.UltimaUbicacion, extras?.getString("ubicacion"))


    }
}