package com.yousuf.fhirdemo.ui.details.ecg

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yousuf.fhirdemo.data.EcgObservationData
import com.yousuf.fhirdemo.data.TaskStatus
import com.yousuf.fhirdemo.network.RestClient
import com.yousuf.fhirdemo.repository.ObservationRepository
import com.yousuf.fhirdemo.usecase.EcgDataListUsecase
import kotlinx.coroutines.launch


/**
 * Created by Android Studio.
 * User:  Yousuf
 * Email:
 * Date:  3/6/22
 * To change this template use File | Settings | File and Code Templates.
 *
 */

class EcgDataListViewModel(application: Application) : AndroidViewModel(application) {

    private val ecgListDataUseCase = EcgDataListUsecase(ObservationRepository(RestClient.getClient()))
    private val _ecgListDataTaskStatus = MutableLiveData<TaskStatus<List<EcgObservationData>>>()
    val ecgListDataTaskStatus : LiveData<TaskStatus<List<EcgObservationData>>> get() = _ecgListDataTaskStatus


    fun getAllEcgData(patientId:String){
        viewModelScope.launch {
            _ecgListDataTaskStatus.value = TaskStatus.Running
            try {
                val ecgDataList = ecgListDataUseCase.getEcgByPatientId(patientId)
                _ecgListDataTaskStatus.value = TaskStatus.Finished(ecgDataList)
            }catch (ex:Exception){
                _ecgListDataTaskStatus.value = TaskStatus.Error(ex, ex.message ?: ex::class.java.simpleName)
            }

        }

    }

}