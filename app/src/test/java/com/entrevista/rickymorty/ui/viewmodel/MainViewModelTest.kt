package com.entrevista.rickymorty.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.entrevista.rickymorty.dominio.modelo.*
import com.entrevista.rickymorty.dominio.usecase.GetPersonajesUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @MockK
    private lateinit var PersonajesUseCase: GetPersonajesUseCase

    private lateinit var MainViewModel:MainViewModel

    //Arch Core Reglas
    @get:Rule
    var rule:InstantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        MainViewModel = MainViewModel(PersonajesUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)//Le indico el hilo a utilizar del dispatcher
    }


    @After
    fun finalizando(){
        Dispatchers.resetMain() //Reseteo al hilo principal
    }


    //TEST DE PERSONAJE LIVE DATA
    @Test
    fun test_Get_Personajes_CompararResultados()= runTest {
        //Given
        val personajes = Personaje("prueba", emptyList(), "prueba",1,"prueba",
            Location("prueba","prueba"),
            "prueba",
            Origin("prueba","prueba"),
            "prueba","prueba","prueba","prueba"
        )

        val listado = ResponseApiPersonaje(
            Info(1,"1",1,1),
            listOf(personajes)
        )

        //EL -1 es dado a que en la primera ves que se ejecuta inyecta -
        coEvery { PersonajesUseCase.invoke(-1) } returns listado

        val data = PersonajesUseCase.invoke(-1)
        println("Personajes "+data?.results?.size)
        //When
        MainViewModel.BuscarPagina()

        //Then
       // println("paginas: "+MainViewModel.PersonajeModel.value?.info?.pages)


        assert(MainViewModel.PersonajeModel.value == listado)


    }

    @Test
    fun test_Get_Personajes_ES_NULO()= runTest {
        //Given
        val personajes = Personaje("prueba",emptyList(), "prueba",1,"prueba",
            Location("prueba","prueba"),
            "prueba",
            Origin("prueba","prueba"),
            "prueba","prueba","prueba","prueba"
        )

        val listado = ResponseApiPersonaje(
            Info(0,"",0,0),
            listOf(personajes)
        )

       MainViewModel.PersonajeModel.value = listado //Le seteo el valor al livedata

        coEvery { PersonajesUseCase.invoke(1) } returns listado
        //When
        MainViewModel.BuscarPagina()

        //Then
        assert(MainViewModel.PersonajeModel.value == listado) //SI el caso de uso retorna nulo, entonces mantengo los datos almacenados en cache

    }


    //TEST DE BOTONES
    @Test
    fun test_BotonAnterior_Deshabilitado()= runTest {
        //Given


        MainViewModel.paginaActual = 1 //Le seteo el valor de pagina Actual

        //When
        MainViewModel.PaginaAnterior()

        //Then
        assert(MainViewModel.BotonRegresarHabilitado.value == false) //SI el caso de uso retorna nulo, entonces mantengo los datos almacenados en cache

    }

    @Test
    fun test_BotonSiguiente_Deshabilitado()= runTest {
        //Given
        val personajes = Personaje("prueba",emptyList(), "prueba",1,"prueba",
            Location("prueba","prueba"),
            "prueba",
            Origin("prueba","prueba"),
            "prueba","prueba","prueba","prueba"
        )

        val listado = ResponseApiPersonaje(
            Info(0,"",42,0),
            listOf(personajes)
        )


        MainViewModel.CantidadMaxima = listado.info.pages
        MainViewModel.paginaActual = listado.info.pages

        coEvery { PersonajesUseCase.invoke(1) } returns listado
        //When
        MainViewModel.PaginaSiguiente()


        //Then
        assert(MainViewModel.BotonSeguirHabilitado.value == false) //SI el caso de uso retorna nulo, entonces mantengo los datos almacenados en cache

    }

    @Test
    fun test_BotonAnterior_Habilitado()= runTest {
        //Given


        MainViewModel.paginaActual = 4 //Le seteo el valor de pagina Actual

        //When
        MainViewModel.PaginaAnterior()

        //Then
        assert(MainViewModel.BotonRegresarHabilitado.value == true) //SI el caso de uso retorna nulo, entonces mantengo los datos almacenados en cache

    }

    @Test
    fun test_BotonSiguiente_Habilitado()= runTest {
        //Given
        val personajes = Personaje("prueba",emptyList(), "prueba",1,"prueba",
            Location("prueba","prueba"),
            "prueba",
            Origin("prueba","prueba"),
            "prueba","prueba","prueba","prueba"
        )

        val listado = ResponseApiPersonaje(
            Info(0,"",42,0),
            listOf(personajes)
        )


        MainViewModel.CantidadMaxima = listado.info.pages
        MainViewModel.paginaActual = 1

        coEvery { PersonajesUseCase.invoke(1) } returns listado
        //When
       // MainViewModel.BuscarPagina() //Fuerzo a sobrepasar el limite
        MainViewModel.PaginaSiguiente()


        //Then
        assert(MainViewModel.BotonSeguirHabilitado.value == true) //SI el caso de uso retorna nulo, entonces mantengo los datos almacenados en cache

    }
}