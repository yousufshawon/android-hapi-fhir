package com.yousuf.fhirdemo.usecase

import ca.uhn.fhir.rest.gclient.ICriterion
import ca.uhn.fhir.rest.gclient.StringClientParam
import ca.uhn.fhir.util.BundleUtil
import com.yousuf.fhirdemo.network.RestClient
import com.yousuf.fhirdemo.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.hl7.fhir.r4.model.Patient
import timber.log.Timber

class SearchUseCase(private val searchRepository: SearchRepository) {

    suspend fun searchByAddress(keyword:String) : List<Patient>{
        return withContext(Dispatchers.IO){
            try {
                val bundle = searchRepository.search(getAddressSearchCriteria(keyword))
                BundleUtil.toListOfResourcesOfType(RestClient.getContext(), bundle, Patient::class.java )
            }catch (e:Exception){
                Timber.e("Exception: ${e.message}")
                listOf()
            }
        }

    }

    private fun getAddressSearchCriteria(keyword: String) : ICriterion<StringClientParam>{
        return Patient.ADDRESS.contains().value(keyword)
    }

}