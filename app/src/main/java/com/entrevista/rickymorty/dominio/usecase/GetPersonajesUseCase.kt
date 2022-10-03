package com.entrevista.rickymorty.dominio.usecase

import com.entrevista.rickymorty.data.repositorios.PersonajesRepository
import com.entrevista.rickymorty.dominio.modelo.ResponseApiPersonaje
import javax.inject.Inject

class GetPersonajesUseCase @Inject constructor(
    private val repositorio: PersonajesRepository


) {

    suspend operator fun invoke(pagina: Int): ResponseApiPersonaje{ //En este caso puedo dividir si lo obtengo de la BD o de la API
        return repositorio.getAllPersonajesFromApi(pagina)  //Como no se ha implementado BD se devuelve de la API
    }


}