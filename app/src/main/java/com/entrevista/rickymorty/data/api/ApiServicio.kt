package com.entrevista.rickymorty.data.api

import com.entrevista.rickymorty.dominio.modelo.ResponseApiPersonaje
import com.entrevista.rickymorty.util.Constantes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServicio {

    @GET(Constantes.URL_PERSONAJE)
    suspend fun GetPersonajes(
        @Query("page") page:Int
    ):Response<ResponseApiPersonaje> //Obtengo la respuesta de la consulta de tipo PERSONAJE


}