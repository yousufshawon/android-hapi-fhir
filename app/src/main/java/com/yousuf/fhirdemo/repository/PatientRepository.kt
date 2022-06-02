package com.yousuf.fhirdemo.repository

import ca.uhn.fhir.rest.api.MethodOutcome
import ca.uhn.fhir.rest.client.api.IGenericClient
import org.hl7.fhir.r4.model.Patient
import timber.log.Timber


/**
 * Created by Android Studio.
 * User:  Yousuf
 * Email:
 * Date:  2/6/22
 * To change this template use File | Settings | File and Code Templates.
 *
 */

class PatientRepository(private val client: IGenericClient) {

    fun createPatient(patient: Patient) : MethodOutcome {
        Timber.i("createPatient Thread: ${Thread.currentThread().name}")
        return client.create()
            .resource(patient)
            .prettyPrint()
            .encodedJson()
            .execute()
    }


    fun getPatientDetails(id:String): Patient {
        Timber.i("getPatientDetails Thread: ${Thread.currentThread().name}")
        return client.read()
            .resource(Patient::class.java)
            .withId(id)
            .encodedJson()
            .execute()
    }

    fun editPatient(patient: Patient) : MethodOutcome {
        Timber.i("editPatient Thread: ${Thread.currentThread().name}")
        return client.update()
            .resource(patient)
            .encodedJson()
            .execute()
    }

}