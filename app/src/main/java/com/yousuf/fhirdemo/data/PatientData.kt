package com.yousuf.fhirdemo.data

import org.hl7.fhir.r4.model.Patient


/**
 * Created by Android Studio.
 * User:  Yousuf
 * Email:
 * Date:  2/6/22
 * To change this template use File | Settings | File and Code Templates.
 *
 */

data class PatientData(
    val id : String,
    val firstName : String,
    val familyName : String,
    val city : String,
    val state : String,
    val country : String,
){
    fun getPatient() : Patient {

        val patient = Patient()
        patient.id = id
        patient.addName().setFamily(familyName).addGiven(firstName)
        patient.addAddress().setCity(city).setState(state).country = country

        return patient
    }
}
