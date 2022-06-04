package com.yousuf.fhirdemo.ui.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yousuf.fhirdemo.data.TaskStatus
import com.yousuf.fhirdemo.network.RestClient
import com.yousuf.fhirdemo.repository.ObservationRepository
import com.yousuf.fhirdemo.repository.PatientRepository
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

class PatientDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val patientDetailsUseCase = PatientDetailsUseCase(
        PatientRepository(RestClient.getClient()),
        ObservationRepository(RestClient.getClient())
    )

    private val _detailsStatus = MutableLiveData<TaskStatus<Patient>>()
    val detailsStatus: LiveData<TaskStatus<Patient>> get() = _detailsStatus


    fun loadPatientDetails(patientId: String) {
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