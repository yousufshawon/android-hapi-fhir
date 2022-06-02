package com.yousuf.fhirdemo.usecase

import ca.uhn.fhir.rest.gclient.ICriterion
import ca.uhn.fhir.rest.gclient.StringClientParam
import ca.uhn.fhir.util.BundleUtil
import com.yousuf.fhirdemo.network.RestClient
import com.yousuf.fhirdemo.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.hl7.fhir.r4.model.Patient

/**
 * Created by Android Studio.
 * User:  Yousuf
 * Email:
 * Date:  31/5/22
 * To change this template use File | Settings | File and Code Templates.
 *
 */


class SearchUseCase(private val searchRepository: SearchRepository) {

    suspend fun searchByAddress(keyword:String) : List<Patient>{
        return withContext(Dispatchers.IO){
            val bundle = searchRepository.search(getAddressSearchCriteria(keyword))
            BundleUtil.toListOfResourcesOfType(RestClient.getContext(), bundle, Patient::class.java )
        }

    }

    private fun getAddressSearchCriteria(keyword: String) : ICriterion<StringClientParam>{
        return Patient.ADDRESS.contains().value(keyword)
    }

}