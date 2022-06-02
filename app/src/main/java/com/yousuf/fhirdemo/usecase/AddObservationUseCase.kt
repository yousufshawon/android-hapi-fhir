package com.yousuf.fhirdemo.usecase

import ca.uhn.fhir.rest.api.MethodOutcome
import com.yousuf.fhirdemo.repository.ObservationRepository
import com.yousuf.fhirdemo.utility.DataGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.hl7.fhir.r4.model.Observation


/**
 * Created by Android Studio.
 * User:  Yousuf
 * Email:
 * Date:  2/6/22
 * To change this template use File | Settings | File and Code Templates.
 *
 */

class AddObservationUseCase(private val addObservationRepository: ObservationRepository) {

 suspend fun uploadEcg(patientId:String, ecgData:String ): MethodOutcome {
  return withContext(Dispatchers.IO){
   val observation = getEcgObservation(patientId, ecgData)
   addObservationRepository.upload(observation)
  }
 }


 private fun getEcgObservation(patientId:String, ecgData: String): Observation {
  return DataGenerator.EcgData.getEcgObservation(patientId, ecgData)
 }

 private fun getDummyEcgObservation(patientId: String): Observation {
  val ekgData = DataGenerator.EcgData.getRandomEkgData(100)
  val ekgStringData = ekgData.joinToString(" ")
  return DataGenerator.EcgData.getEcgObservation(patientId, ekgStringData)
 }

}