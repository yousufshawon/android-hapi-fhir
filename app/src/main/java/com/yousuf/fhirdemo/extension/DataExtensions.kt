package com.yousuf.fhirdemo.extension

import org.hl7.fhir.r4.model.Patient

fun Patient.toDisplayString():String{
    return if (name.isNotEmpty()){
        name[0].givenAsSingleString
    }else{
        "null"
    }
}

fun Patient.getLogInfo() : String{
    val sb = StringBuilder()
    sb.append("Url Id:").append(id).append("\n")
    sb.append("Id:").append(idElement.idPart).append("\n")

    sb.append("Name: ")
    if (name.size > 0) {
        sb.append(name[0].nameAsSingleString)
    }
    sb.append("\n")
    sb.append("Address:")
    if (address.size > 0) {
        sb.append(address[0].state).append(",")
            .append(address[0].city).append(",")
            .append(address[0].country)
    }
    sb.append("\n")
    return sb.toString()
}