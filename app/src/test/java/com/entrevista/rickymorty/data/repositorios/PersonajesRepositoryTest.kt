package com.entrevista.rickymorty.data.repositorios

import android.icu.text.DateTimePatternGenerator.PatternInfo.OK
import com.entrevista.rickymorty.core.di.AppModule
import com.entrevista.rickymorty.data.api.ApiServicio
import com.entrevista.rickymorty.dominio.modelo.*
import com.google.gson.Gson
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import retrofit2.Retrofit
import java.net.HttpURLConnection

class PersonajesRepositoryTest{

    @RelaxedMockK
    private lateinit var  api: ApiServicio

    private lateinit var PersonajesRepository: PersonajesRepository
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        mockWebServer = MockWebServer()
        mockWebServer.start()
        api = AppModule.provideRetrofitInstance(mockWebServer.url("/").toString())
        PersonajesRepository = PersonajesRepository(api)

    }


    //TEST HTTP OK
    @Test
    fun test_GetPersonajesApi_listado_nulo() = runBlocking { //Por ser una funcion suspend
        //GIVEN

        val Personaje = ResponseApiPersonaje(
            Info(0,"",0,0),
            emptyList()
        )

        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(Personaje))
        mockWebServer.enqueue(expectedResponse)


        val actualResponse = PersonajesRepository.getAllPersonajesFromApi(1)

        assert(actualResponse!=null)

        assert(actualResponse?.info!=null)
        assert(actualResponse!!.info.pages>=0)

        assert(actualResponse?.results!=null)
        assert(!actualResponse?.results.isNotEmpty())
    }

    @Test
    fun test_GetPersonajesApi_listado_datos() = runBlocking { //Por ser una funcion suspend
        //GIVEN

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

        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(listado))
        mockWebServer.enqueue(expectedResponse)


        val actualResponse = PersonajesRepository.getAllPersonajesFromApi(1)

        assert(actualResponse!=null)

        assert(actualResponse?.info!=null)


        assert(actualResponse?.results!=null)
        assert(actualResponse?.results!!.isNotEmpty())
    }

    @Test
    fun test_GetPersonajesApi_listado_datos_PRIMERAPAGINA() = runBlocking { //Por ser una funcion suspend
        //GIVEN

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

        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(listado))
        mockWebServer.enqueue(expectedResponse)


        val actualResponse = PersonajesRepository.getAllPersonajesFromApi(-1)

        assert(actualResponse!=null)

        assert(actualResponse?.info!=null)


        assert(actualResponse?.results!=null)
        assert(actualResponse?.results!!.isNotEmpty())
    }

    //TEST HTTP INTERNAL ERROR API
    @Test
    fun TEST_ERROR_SERVIDOR() = runBlocking {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
            .setBody(Gson().toJson(null))
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = api.GetPersonajes(1)
        assert(actualResponse.code()==HttpURLConnection.HTTP_INTERNAL_ERROR)

    }

    @Test
    fun TEST_ERROR_SERVIDOR_RESPUESTA() = runBlocking {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = PersonajesRepository.getAllPersonajesFromApi(1)
        assert(actualResponse!=null) //Si falla la conexion debe devolver un objeto vacio
        assert(actualResponse!!.info.pages==0) //Si falla la conexion debe devolver que NO hay paginas
    }

    @Test
    fun TEST_ERROR_SERVIDOR_TIMEOUT() = runBlocking {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_GATEWAY_TIMEOUT)
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = PersonajesRepository.getAllPersonajesFromApi(1)
        assert(actualResponse!=null) //Si falla la conexion debe devolver un objeto vacio
        assert(actualResponse!!.info.pages==0) //Si falla la conexion debe devolver que NO hay paginas
    }

    @Test
    fun TEST_ERROR_SERVIDOR_TIMEOUT_PRIMERAPAGINA() = runBlocking {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_GATEWAY_TIMEOUT)
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = PersonajesRepository.getAllPersonajesFromApi(-1)
        assert(actualResponse!=null) //Si falla la conexion debe devolver un objeto vacio
        assert(actualResponse!!.info.pages==0) //Si falla la conexion debe devolver que NO hay paginas
    }


    @Test
    fun TEST_CONSTANTE() = runBlocking {
        val expectedResponse = MockResponse()
            .setResponseCode(200)
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = AppModule.provideBaseUrl()
        assert(!actualResponse.isNullOrEmpty())
        assert(!actualResponse.isNullOrBlank())
    }
}