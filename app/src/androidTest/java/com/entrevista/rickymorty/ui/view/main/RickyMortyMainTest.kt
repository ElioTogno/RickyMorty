package com.entrevista.rickymorty.ui.view.main

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.entrevista.rickymorty.R
import com.entrevista.rickymorty.ui.view.adapter.PersonajesAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.rule.ActivityTestRule






@RunWith(AndroidJUnit4ClassRunner::class)
class RickyMortyMainTest{

    //Variables globales
    @get: Rule
    val actividadRule = ActivityScenarioRule(RickyMortyMain::class.java)

    @get: Rule
    var mActivityTestRule: ActivityTestRule<RickyMortyMain> =
        ActivityTestRule<RickyMortyMain>(RickyMortyMain::class.java)

    val LIST_ITEM_TEST = 0
    val PERSONAJE_TEST = "Rick Sanchez"

    /**
     * Recycler view mostrado en la vista
     */
    @Test
    fun test_RecyclerView_Esvisible(){//Verifica si el recycler view es visible

        onView(withId(R.id.rvPersonajes)).check(matches(isDisplayed()))

    }

    /**
     * Seleccionar un item y navegar al fragmento detalle
     * Verificar si es el item correcto
     * PANTALLA CELULAR o PORTRAIT
     */
    @Test
    fun test_SeleccionarItem_recyclerView_PORTRAIT(){


        //Verifico si el listado se ha mostrado
        onView(withId(R.id.rvPersonajes)).check(matches(isDisplayed()))

        //Duermo el hilo 2 segundos para realizar la accion
        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        //Le indico que presione la posicion indicada
        onView(withId(R.id.rvPersonajes))
            .perform(actionOnItemAtPosition<PersonajesAdapter.MainViewHolder>(0,click()))

        //Duermo el hilo 2 segundos mas para cargar la informacion
        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        //Verifico si la posicion elegida coincide con el nombre indicado
        onView(withId(R.id.output_detail_nombre)).check(matches(withText(PERSONAJE_TEST)))

    }

    /**
     * Seleccionar un item y navegar al fragmento detalle
     * Verificar si es el item correcto
     * PANTALLA TABLET O LANDSCAPE
     */
    @Test
    fun test_SeleccionarItem_recyclerView_LANDSCAPE(){

        //Giro la pantalla
        mActivityTestRule.getActivity().requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        //Verifico si el listado se ha mostrado
        onView(withId(R.id.rvPersonajes)).check(matches(isDisplayed()))

        //Duermo el hilo 2 segundos para realizar la accion
        try {
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        //Le indico que presione la posicion indicada
        onView(withId(R.id.rvPersonajes))
            .perform(actionOnItemAtPosition<PersonajesAdapter.MainViewHolder>(0,click()))

        //Duermo el hilo 2 segundos mas para cargar la informacion
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        //Verifico si la posicion elegida coincide con el nombre indicado
        onView(withId(R.id.fragment_detail_nombre)).check(matches(withText(PERSONAJE_TEST)))

    }



    /**
     * Seleccionar un item y volver a la pantalla principal
     */








}