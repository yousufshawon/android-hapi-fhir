package com.yousuf.fhirdemo.repository

import ca.uhn.fhir.rest.client.api.IGenericClient
import ca.uhn.fhir.rest.gclient.ICriterion
import ca.uhn.fhir.rest.gclient.StringClientParam
import com.yousuf.fhirdemo.config.FhirConfig
import org.hl7.fhir.r4.model.Bundle
import org.hl7.fhir.r4.model.Patient
import timber.log.Timber

class SearchRepository(private val client: IGenericClient) {

    fun search(criteria : ICriterion<StringClientParam>) : Bundle{
        Timber.i("Thread: ${Thread.currentThread().name}")

        return client.search<Bundle>()
            .forResource(Patient::class.java)
            .where(criteria)
            .count(FhirConfig.searchResultLimit)
            .prettyPrint()
            .returnBundle(Bundle::class.java)
            .execute()
    }

}