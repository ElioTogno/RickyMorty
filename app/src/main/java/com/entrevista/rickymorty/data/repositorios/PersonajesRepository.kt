package com.entrevista.rickymorty.data.repositorios

import android.util.Log
import com.entrevista.rickymorty.data.api.ApiServicio
import com.entrevista.rickymorty.dominio.modelo.Info
import com.entrevista.rickymorty.dominio.modelo.ResponseApiPersonaje
import java.lang.Exception
import javax.inject.Inject

class PersonajesRepository @Inject
constructor(
    private val api: ApiServicio //inyeccion de dependencias al Servicio
){

    suspend fun getAllPersonajesFromApi(pagina: Int): ResponseApiPersonaje{

            var repagina = pagina;
            if(repagina==-1){
                repagina=1
            }
            val response = api.GetPersonajes(repagina) //obtengo los datos en JSON
            if (response != null) {
                if (response.body()?.info != null) {
                    val datos =
                        response.body()?.results ?: emptyList() //Separo los datos de los personajes
                    val info = response.body()!!.info //Separo los datos de informacion
                    return ResponseApiPersonaje(
                        info,
                        datos
                    ) //Devuelvo los datos en un objeto "ResponseApiPersonaje"
                }

            }
        return ResponseApiPersonaje(Info(0,"0",0,"0"), emptyList()) //Devuelvo un objeto vacio si falla la conexion
        }


}