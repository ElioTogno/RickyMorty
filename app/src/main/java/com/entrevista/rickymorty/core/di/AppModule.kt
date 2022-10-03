package com.entrevista.rickymorty.core.di

import com.entrevista.rickymorty.data.api.ApiServicio
import com.entrevista.rickymorty.util.Constantes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl()= Constantes.URL_BASE //Provee la direccion URL basica

    @Provides
    @Singleton
    fun provideRetrofitInstance(URL_BASE:String):ApiServicio =
        Retrofit.Builder()
            .baseUrl(URL_BASE)
            .addConverterFactory(GsonConverterFactory.create()) //Interprete de JSON
            .build()
            .create(ApiServicio::class.java)
}


