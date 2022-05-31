package com.yousuf.fhirdemo.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yousuf.fhirdemo.extension.getLogInfo
import com.yousuf.fhirdemo.network.RestClient
import com.yousuf.fhirdemo.repository.SearchRepository
import com.yousuf.fhirdemo.usecase.SearchUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.hl7.fhir.r4.model.Patient
import timber.log.Timber

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _patientList = mutableListOf<Patient>()
    val patientList : List<Patient> get() = _patientList

    private val _patientListMutableLiveData = MutableLiveData<List<Patient>>(listOf())
    val patientListLiveData : LiveData<List<Patient>> get() = _patientListMutableLiveData

    private val _loading = MutableLiveData(false)
    val loading : LiveData<Boolean> get() = _loading

    private val searchUseCase = SearchUseCase(SearchRepository(RestClient.getClient()))
    private val mainScope = CoroutineScope(Dispatchers.Main.immediate)

    init {
    }

    fun forceInit(){
        Timber.i("forceInit")
    }

    fun onSearchAction(keyword: String = ""){
        viewModelScope.launch {
            _loading.value = true
            val resList = searchPatientWithAddress(keyword)
            _patientList.clear()
            _patientList.addAll(resList)
            _patientListMutableLiveData.value = resList
            _loading.value = false
        }
    }

    private suspend fun searchPatientWithAddress(keyword:String) : List<Patient>{
        Timber.i("Search with address")
        val patientList = searchUseCase.searchByAddress(keyword)
        val resString = patientList.joinToString("\n") { it.getLogInfo() }
        Timber.i("Patient list:\n $resString")
        return patientList
    }

    private fun getDummyData(): List<Patient> {
        val p1 = Patient().apply {
            id = "1"
            addName().setFamily("Hasan").addGiven("Maruf")
        }
        val p2 = Patient().apply {
            id = "2"
            addName().setFamily("Hasan").addGiven("Jahid")
        }
        val p3 = Patient().apply {
            id = "3"
            addName().setFamily("Hasan").addGiven("Jamil")
        }
        val p4 = Patient().apply {
            id = "4"
            addName().setFamily("Hasan").addGiven("Karim")
        }

        return listOf(p1, p2, p3, p4)
    }


}