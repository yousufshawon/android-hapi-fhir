package com.yousuf.fhirdemo.repository

import ca.uhn.fhir.rest.api.MethodOutcome
import ca.uhn.fhir.rest.client.api.IGenericClient
import org.hl7.fhir.r4.model.Observation


/**
 * Created by Android Studio.
 * User:  Yousuf
 * Email:
 * Date:  2/6/22
 * To change this template use File | Settings | File and Code Templates.
 *
 */

class ObservationRepository(private val client : IGenericClient) {

 suspend fun upload(observation: Observation) : MethodOutcome {
  return client.update()
   .resource(observation)
   .execute()
 }

 suspend fun get(observationId:String): Observation {
  return client.read()
   .resource(Observation::class.java)
   .withId(observationId)
   .execute()
 }

}