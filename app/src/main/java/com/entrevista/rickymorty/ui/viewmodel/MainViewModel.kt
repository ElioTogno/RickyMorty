package com.entrevista.rickymorty.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.entrevista.rickymorty.dominio.modelo.ResponseApiPersonaje
import com.entrevista.rickymorty.dominio.usecase.GetPersonajesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

    private val PersonajesUseCase: GetPersonajesUseCase,


    ): ViewModel(){

    val PersonajeModel = MutableLiveData<ResponseApiPersonaje>()
    val MensajeNotificacion = MutableLiveData<String>()

    val BotonRegresarHabilitado = MutableLiveData<Boolean>()
    val BotonSeguirHabilitado = MutableLiveData<Boolean>()



    val Loading = MutableLiveData<Boolean>()

    var paginaActual = -1;
    var CantidadMaxima = 0; //Este valor indica la cantidad maxima de paginas




    fun VerificarBotones(){

        if(paginaActual==1){
            BotonRegresarHabilitado.postValue(false)
        }else{
            BotonRegresarHabilitado.postValue(true)
        }

        if(paginaActual==CantidadMaxima){
            BotonSeguirHabilitado.postValue(false)
        }else{
            BotonSeguirHabilitado.postValue(true)
        }

        if(paginaActual==-1){//Caso inicial cuando se estan buscando datos
            BotonSeguirHabilitado.postValue(false)
            BotonRegresarHabilitado.postValue(false)
            Loading.postValue(true)
        }
    }

    fun PaginaAnterior() {

            if(paginaActual==1){
                println("Pagina actual no hay anterior")
                MensajeNotificacion.postValue("Pagina 1. No hay elementos anteriores")

            }else{
                paginaActual--
                BuscarPagina()

            }
        VerificarBotones()

        }

    fun PaginaSiguiente(){
        if(paginaActual>=CantidadMaxima){
            println("Pagina actual no hay siguientes")
            MensajeNotificacion.postValue("Pagina $CantidadMaxima. No hay mas personajes aun")
        }else{
            paginaActual++;
            BuscarPagina()
        }
        VerificarBotones()

    }



    fun BuscarPagina () {    //  CONSULTA AL API CON RETROFIT
        Loading.postValue(true)

            viewModelScope.launch() {

                try {


                    val response: ResponseApiPersonaje = PersonajesUseCase.invoke(paginaActual)



                    if (response?.results!!.isNotEmpty()) {
                        CantidadMaxima = response!!.info.pages

                        if (paginaActual == -1) {
                            paginaActual = 1
                            BotonSeguirHabilitado.postValue(true)
                        }

                        PersonajeModel.postValue(response!!) //No tiene que ser nulo
                        Loading.postValue(false)
                    }else{
                        MensajeNotificacion.postValue("Error al intentar obtener los datos")
                    }
                } catch (E: Exception) {
                    println("Error al intentar obtener los datos "+E);
                   MensajeNotificacion.postValue("Error al intentar obtener los datos")
                }
            }



    }



}