package com.yousuf.fhirdemo.usecase

import ca.uhn.fhir.rest.api.MethodOutcome
import com.yousuf.fhirdemo.data.PatientData
import com.yousuf.fhirdemo.repository.PatientRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber


/**
 * Created by Android Studio.
 * User:  Yousuf
 * Email:
 * Date:  2/6/22
 * To change this template use File | Settings | File and Code Templates.
 *
 */

class AddPatientUseCase(private val patientRepository: PatientRepository) {

 suspend fun addPatient(patientData: PatientData) : MethodOutcome {
  return withContext(Dispatchers.IO){
   val patient = patientData.getPatient()
   val outCome = patientRepository.createPatient(patient)
   Timber.i("Create id: ${outCome.id}")
   outCome
  }

 }
}