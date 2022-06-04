package com.yousuf.fhirdemo.usecase

import ca.uhn.fhir.util.BundleUtil
import com.yousuf.fhirdemo.data.EcgObservationData
import com.yousuf.fhirdemo.network.RestClient
import com.yousuf.fhirdemo.repository.ObservationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.hl7.fhir.r4.model.Observation
import org.hl7.fhir.r4.model.SampledData


/**
 * Created by Android Studio.
 * User:  Yousuf
 * Email:
 * Date:  3/6/22
 * To change this template use File | Settings | File and Code Templates.
 *
 */

class EcgDataListUsecase(private val observationRepository: ObservationRepository) {

    suspend fun getEcgByPatientId(patientId:String) : List<EcgObservationData>{
        return withContext(Dispatchers.IO){
            val bundle = observationRepository.searchEcgObservationByPatientId(patientId)
            val observationList =  BundleUtil.toListOfResourcesOfType(RestClient.getContext(), bundle, Observation::class.java)
            observationList.filter { it.value is SampledData }
                .map {
                    EcgObservationData(it.idElement.idPart, it.subject.reference, it.value as SampledData, it.effectiveDateTimeType.toHumanDisplay() )
                }
        }

    }
}