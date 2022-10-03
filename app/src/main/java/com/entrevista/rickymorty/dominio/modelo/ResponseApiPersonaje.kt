package com.entrevista.rickymorty.dominio.modelo

data class ResponseApiPersonaje(
    val info: Info,
    val results: List<Personaje>
)