package com.yousuf.fhirdemo.ui.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ca.uhn.fhir.rest.api.MethodOutcome
import com.yousuf.fhirdemo.data.PatientData
import com.yousuf.fhirdemo.data.TaskStatus
import com.yousuf.fhirdemo.network.RestClient
import com.yousuf.fhirdemo.repository.PatientRepository
import com.yousuf.fhirdemo.usecase.AddPatientUseCase
import kotlinx.coroutines.launch

/**
 * Created by Android Studio.
 * User:  Yousuf
 * Email:
 * Date:  2/6/22
 * To change this template use File | Settings | File and Code Templates.
 *
 */

class AddPatientViewModel(application: Application) : AndroidViewModel(application) {

    private val _addPatientTaskStatus = MutableLiveData<TaskStatus<MethodOutcome>>()
    val addPatientTaskStatus : LiveData<TaskStatus<MethodOutcome>> get() = _addPatientTaskStatus

    private val addPatientUseCase = AddPatientUseCase(PatientRepository(RestClient.getClient()))

    fun addPatient(patientData: PatientData) {
        viewModelScope.launch {
            _addPatientTaskStatus.value = TaskStatus.Running
            try {
                val outcome = addPatientUseCase.addPatient(patientData)
                _addPatientTaskStatus.value = TaskStatus.Finished(outcome)
            }catch (e:Exception){
                _addPatientTaskStatus.value = TaskStatus.Error(e, e.message ?: e.javaClass.simpleName)
            }
        }
    }
}