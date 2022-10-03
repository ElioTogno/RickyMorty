package com.entrevista.rickymorty.ui.view.adapter

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.entrevista.rickymorty.R
import com.entrevista.rickymorty.dominio.modelo.Personaje
import com.entrevista.rickymorty.databinding.CardBinding
import com.entrevista.rickymorty.ui.view.fragments.Fragment
import com.entrevista.rickymorty.ui.view.main.DetallePersonaje


//Adapter utilizado para cada item del recycler view, indicando los elementos que contendra
class PersonajesAdapter (val charactersList: List<Personaje>, val f : FragmentManager) : RecyclerView.Adapter<PersonajesAdapter.MainViewHolder>()  {

    inner class MainViewHolder (private val itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = CardBinding.bind(itemView)

        init {               // listener para cada item del recyclerView

            itemView.setOnClickListener { v: View ->

                val position : Int = adapterPosition

                if(v.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {

                    val intent = Intent (v.context.applicationContext,DetallePersonaje::class.java)

                    intent.putExtra("id", charactersList[position].id.toString())
                    intent.putExtra("nombre", charactersList[position].name)
                    intent.putExtra("imagen", charactersList[position].image)
                    intent.putExtra("genero", charactersList[position].gender)
                    intent.putExtra("estado", charactersList[position].status)
                    intent.putExtra("origen", charactersList[position].origin.name)
                    intent.putExtra("ubicacion", charactersList[position].location.name)

                    v.context.startActivity(intent)

                } else {

                    val bundle =  Bundle ()

                    bundle.putString("id", charactersList[position].id.toString())
                    bundle.putString("nombre", charactersList[position].name)
                    bundle.putString("imagen", charactersList[position].image)
                    bundle.putString("genero", charactersList[position].gender)
                    bundle.putString("estado", charactersList[position].status)
                    bundle.putString("origen", charactersList[position].origin.name)
                    bundle.putString("ubicacion", charactersList[position].location.name)

                    val transaction = f.beginTransaction()

                    val nuevoFragmento = Fragment()

                    nuevoFragmento.arguments = bundle

                    transaction.replace(R.id.fragmentDetail, nuevoFragmento)

                    transaction.addToBackStack(null)

                    transaction.commit()

                }

            }

        }

        fun bindData (character: Personaje) {


            //binding.textView.text=character.name

            binding.nombrePersonaje.text= character.name
            binding.genero.text= character.gender
            binding.estado.text= character.status

            when {
                character.status.equals("Alive") -> {
                    binding.estado.setTextColor(Color.parseColor("#145A32"))
                }
                character.status.equals("Dead") -> {
                    binding.estado.setTextColor(Color.parseColor("#641E16"))
                }
                else -> {
                    binding.estado.setTextColor(Color.parseColor("#7E5109"))

                }
            }

            binding.imageView.load(character.image)


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        return MainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card,parent, false))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        holder.bindData(charactersList[position])
    }

    override fun getItemCount(): Int {

        return charactersList.size
    }

}