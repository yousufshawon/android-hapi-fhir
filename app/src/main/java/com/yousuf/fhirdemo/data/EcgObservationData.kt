package com.yousuf.fhirdemo.data

import org.hl7.fhir.r4.model.SampledData


/**
 * Created by Android Studio.
 * User:  Yousuf
 * Email:
 * Date:  3/6/22
 * To change this template use File | Settings | File and Code Templates.
 *
 */

data class EcgObservationData(
    val id:String,
    val subject:String,
    val data : SampledData,
    val dateTime : String
)
