package com.yousuf.fhirdemo.ui.edit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ca.uhn.fhir.rest.api.MethodOutcome
import com.yousuf.fhirdemo.data.TaskStatus
import com.yousuf.fhirdemo.network.RestClient
import com.yousuf.fhirdemo.repository.ObservationRepository
import com.yousuf.fhirdemo.repository.PatientRepository
import com.yousuf.fhirdemo.usecase.EditPatientUseCase
import com.yousuf.fhirdemo.usecase.PatientDetailsUseCase
import kotlinx.coroutines.launch
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

class ProfileEditViewModel(application: Application) : AndroidViewModel(application) {

    private val patientDetailsUseCase = PatientDetailsUseCase(
        PatientRepository(RestClient.getClient()),
        ObservationRepository(RestClient.getClient())
    )
    private val editPatientUseCase = EditPatientUseCase(PatientRepository(RestClient.getClient()))

    private val _detailsStatus = MutableLiveData<TaskStatus<Patient>>(TaskStatus.Idle)
    val detailsStatus: LiveData<TaskStatus<Patient>> get() = _detailsStatus

    private val _editTaskStatus = MutableLiveData<TaskStatus<MethodOutcome>>(TaskStatus.Idle)
    val editTaskStatus : LiveData<TaskStatus<MethodOutcome>> get() = _editTaskStatus

    fun updatePatient(patient: Patient){
        viewModelScope.launch {
            try {
                _editTaskStatus.value = TaskStatus.Running
                val outcome = editPatientUseCase.updatePatient(patient)
                _editTaskStatus.value = TaskStatus.Finished(outcome)
            }catch (ex:Exception){
                Timber.e("Error: Class -> ${ex::class.java.simpleName} ")
                ex.printStackTrace()
                _editTaskStatus.value = TaskStatus.Error(ex, ex.message ?: ex::class.java.simpleName)
            }
        }
    }

    fun loadPatient(patientId: String) {
        viewModelScope.launch {
            try {
                _detailsStatus.value = TaskStatus.Running
                val patient = patientDetailsUseCase.getPatientDetails(patientId)
                _detailsStatus.value = TaskStatus.Finished(patient)
            }catch (e : Exception){
                Timber.e("Error: ${e.message ?: e::class.java.simpleName}")
                _detailsStatus.value = TaskStatus.Error(e, "Error getting details")
            }
        }
    }


}