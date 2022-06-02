package com.yousuf.fhirdemo.usecase

import com.yousuf.fhirdemo.repository.ObservationRepository
import com.yousuf.fhirdemo.repository.PatientRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.hl7.fhir.r4.model.Observation
import org.hl7.fhir.r4.model.Patient


/**
 * Created by Android Studio.
 * User:  Yousuf
 * Email:
 * Date:  2/6/22
 * To change this template use File | Settings | File and Code Templates.
 *
 */

class PatientDetailsUseCase(
    private val patientRepository: PatientRepository,
    private val observationRepository: ObservationRepository
) {

    suspend fun getPatientDetails(patientId:String) : Patient {
        return withContext(Dispatchers.IO){
            patientRepository.getPatientDetails(patientId)
        }
    }

    suspend fun getObservation(observationId:String) : Observation {
        return withContext(Dispatchers.IO){
            observationRepository.get(observationId)
        }
    }

}