package com.entrevista.rickymorty.ui.view.main

import android.content.res.Configuration
import android.graphics.Color

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.entrevista.rickymorty.R
import com.entrevista.rickymorty.dominio.modelo.Personaje
import com.entrevista.rickymorty.databinding.ActivityRickyMortyMainBinding
import com.entrevista.rickymorty.ui.view.adapter.PersonajesAdapter
import com.entrevista.rickymorty.ui.view.fragments.FragmentLista
import com.entrevista.rickymorty.ui.viewmodel.MainViewModel

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RickyMortyMain : AppCompatActivity() {

    val frag = supportFragmentManager

    private lateinit var binding: ActivityRickyMortyMainBinding

    private val viewModel : MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRickyMortyMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


//AGREGAR VENTANA AL FALLAR DE SI DESEA REINTENTARLO
        viewModel.BuscarPagina()//Inicializo la busqueda -> En la primera pagina
        viewModel.VerificarBotones()


        //Queda a la espera de si hay algun cambio en la variable observada
        viewModel.MensajeNotificacion.observe(this,{
            mostrarMensaje(it)
            if(it.equals("Error al intentar obtener los datos")){
                AlertReintentar()
            }
            //MOstrar mensaje de notificacion UI
        })

        viewModel.BotonRegresarHabilitado.observe(this,{
            if(!it){
                binding.btnAnterior.setBackgroundColor(Color.parseColor("#707B7C"))
                binding.btnAnterior.isClickable=false;
            }else{
                binding.btnAnterior.setBackgroundColor(Color.parseColor("#F44336"))
                binding.btnAnterior.isClickable=true;
            }
        })

        viewModel.BotonSeguirHabilitado.observe(this,{
            if(!it){
                binding.btnSiguiente.setBackgroundColor(Color.parseColor("#707B7C"))
                binding.btnSiguiente.isClickable=false;
            }else{
                binding.btnSiguiente.setBackgroundColor(Color.parseColor("#8BC34A"))
                binding.btnSiguiente.isClickable=true;
            }
        })

        viewModel.Loading.observe(this,{
            if(it){
              binding.pbarloading!!.visibility= VISIBLE
                println("Cargando informacion")
            }else{
                binding.pbarloading!!.visibility=GONE
                println("Informacion cargada")
            }
        })




        //RECYCLER Y DATOS
        viewModel.PersonajeModel.observe(this,{
            iniciarRecycler(it.results) //
        })

        replaceFragment (FragmentLista()) // CARGA EL FRAGMENT DEL RECYCLERVIEW


        //Verifico si el telefono esta horizontal o vertical (Si esta horizontal cargo el fragment del lado derecho)
        if (resources.configuration.orientation != Configuration.ORIENTATION_PORTRAIT) {
            iniciarFragmentoDetalle(Fragment())    // CARGA EL FRAGMENTO DETALLE Patron MASTER DETAIL
        }

        binding.btnAnterior.setOnClickListener {
            viewModel.PaginaAnterior()  // BOTON PAGINA ANTERIOR
        }


        binding.btnSiguiente.setOnClickListener {
           viewModel.PaginaSiguiente()  // BOTON PAGINA SIGUIENTE

        }








    }//FIN ON CREATE


    //METODO PARA INICIAR EL RECYCLER
    private fun iniciarRecycler(result: List<Personaje>) {

        val adapter = PersonajesAdapter(result, frag)  //Paso un FragmentManager como parametro

        val recyclerView = findViewById<RecyclerView>(R.id.rvPersonajes) //Inicializo el recicler view de personajes

        recyclerView?.layoutManager =
            StaggeredGridLayoutManager(
                1, //Le indico al recicler view que quiero 1 elemento por lista
                StaggeredGridLayoutManager.VERTICAL //De forma vertical
            )

        recyclerView?.adapter = adapter

    }

    // ********************* METODOS PARA REEMPLAZAR FRAGMENTOS ********************************

    private fun replaceFragment(fragment: Fragment) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()

    }

    private fun iniciarFragmentoDetalle(fragment: Fragment) {

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragmentDetail, fragment) //Inicio el fragmento detalle
        transaction.commit()

    }


    //METODO PARA MOSTRAR MENSAJES DEL VIEW MODEL
    private fun mostrarMensaje (x: String) {
        Toast.makeText(this,x, Toast.LENGTH_LONG ).show()
    }


    private fun AlertReintentar() {
        try {
            val builder = AlertDialog.Builder(this@RickyMortyMain)
            builder.setMessage(R.string.Reintentar)
                .setCancelable(false)
                .setPositiveButton(
                    "Reintentar"
                ) { dialog, id -> viewModel.BuscarPagina() }
            builder.create()
            builder.show()
        }catch (E:Exception){
            Log.e("Error","Error al intentar mostrar mensaje de reintentar")
            E.printStackTrace()
        }
    }


}