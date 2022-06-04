package com.yousuf.fhirdemo.repository

import ca.uhn.fhir.rest.api.MethodOutcome
import ca.uhn.fhir.rest.client.api.IGenericClient
import com.yousuf.fhirdemo.utility.ObservationCode
import org.hl7.fhir.instance.model.api.IBaseBundle
import org.hl7.fhir.r4.model.Bundle
import org.hl7.fhir.r4.model.Observation


/**
 * Created by Android Studio.
 * User:  Yousuf
 * Email:
 * Date:  2/6/22
 * To change this template use File | Settings | File and Code Templates.
 *
 */

class ObservationRepository(private val client: IGenericClient) {

    suspend fun upload(observation: Observation): MethodOutcome {
        return client.update()
            .resource(observation)
            .execute()
    }

    suspend fun get(observationId: String): Observation {
        return client.read()
            .resource(Observation::class.java)
            .withId(observationId)
            .execute()
    }

    suspend fun searchEcgObservationByPatientId(patientId:String) : Bundle {
        return client.search<IBaseBundle>()
            .forResource(Observation::class.java)
            .where(Observation.SUBJECT.hasId("Patient/$patientId"))
            .where(Observation.CODE.exactly().code(ObservationCode.ecgCode))
            .returnBundle(Bundle::class.java)
            .execute()
    }



}