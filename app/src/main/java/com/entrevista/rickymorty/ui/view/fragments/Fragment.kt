package com.entrevista.rickymorty.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import coil.load
import com.entrevista.rickymorty.R

class Fragment : Fragment(R.layout.fragment_detalle) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_detalle, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        if (arguments != null) { //Recibo los argumentos que me envian de la pantalla anterior

            val getid = requireArguments().getString("id")
            val getnombre = requireArguments().getString("nombre")
            val getimagen = requireArguments().getString("imagen")
            val getgenero = requireArguments().getString("genero")
            val getestado = requireArguments().getString("estado")
            val getorigen = requireArguments().getString("origen")
            val getubicacion = requireArguments().getString("ubicacion")



            val genero = requireView().findViewById<TextView>(R.id.fragment_genero)
            val name = requireView().findViewById<TextView>(R.id.fragment_detail_nombre)
            val image = requireView().findViewById<ImageView>(R.id.fragment_detail_imagen)
            val estado = requireView().findViewById<TextView>(R.id.fragment_estado)
            val origen = requireView().findViewById<TextView>(R.id.fragment_Origen)
            val ubicacion = requireView().findViewById<TextView>(R.id.fragment_Ubicacion)


            name.text = getnombre
            image.load(getimagen)
            genero.text = getgenero
            estado.text = getestado
            origen.text = getString(R.string.Origen, getorigen)
            ubicacion.text = getString(R.string.UltimaUbicacion,getubicacion)


        }

    }

}