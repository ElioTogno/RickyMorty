package com.entrevista.rickymorty.dominio.usecase

import com.entrevista.rickymorty.data.repositorios.PersonajesRepository
import com.entrevista.rickymorty.dominio.modelo.*
import io.mockk.MockKAnnotations

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase

import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

import org.mockito.MockitoAnnotations


class GetPersonajesUseCaseTest {

    @MockK
    private lateinit var repositorio: PersonajesRepository //Mockeo el repositorio

    lateinit var getPersonajesUseCase : GetPersonajesUseCase


    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getPersonajesUseCase = GetPersonajesUseCase(repositorio) //Le indico al caso de uso el repositorio mockeado
    }

    @Test
    fun test_funcionamientoApi_empty_list() = runBlocking {
        //Given
        val listado = ResponseApiPersonaje(
            Info(0,"",0,0),
            emptyList()
        )

        coEvery {repositorio.getAllPersonajesFromApi(1)} returns listado

        //When
        getPersonajesUseCase(1)

        //Then
        //Verifico que se llame solo una vez y no X cantidad de veces
        coVerify(exactly = 1) { repositorio.getAllPersonajesFromApi(1) }
    }

    @Test
    fun test_funcionamientoApi_con_datos_No_nulos() = runBlocking {
        //Given

        val personajes = Personaje(
            "prueba",
            emptyList(),
            "prueba",
            1,
            "prueba",
            Location("prueba","prueba"),
            "prueba",
            Origin("prueba","prueba"),
            "prueba",
            "prueba",
            "prueba",
            "prueba"
        )



        val listado = ResponseApiPersonaje(
            Info(0,"",0,0),
            listOf(personajes)
        )

        coEvery {repositorio.getAllPersonajesFromApi(1)} returns listado

        //When
        val respuesta = getPersonajesUseCase(1)

        //Then
       // assert(respuesta != null)
    }



}