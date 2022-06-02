package com.yousuf.fhirdemo.usecase

import ca.uhn.fhir.rest.api.MethodOutcome
import com.yousuf.fhirdemo.repository.PatientRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.hl7.fhir.r4.model.Patient


/**
 * Created by Android Studio.
 * User:  Yousuf
 * Email:
 * Date:  2/6/22
 * To change this template use File | Settings | File and Code Templates.
 *
 */

class EditPatientUseCase(private val patientRepository: PatientRepository) {

 suspend fun updatePatient(patient: Patient) : MethodOutcome {
  return withContext(Dispatchers.IO){
   patientRepository.editPatient(patient)
  }
 }

}