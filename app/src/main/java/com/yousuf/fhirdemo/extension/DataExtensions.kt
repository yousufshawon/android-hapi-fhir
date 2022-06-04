package com.yousuf.fhirdemo.extension

import org.hl7.fhir.r4.model.Patient

fun Patient.toDisplayString():String{
    return if (name.isNotEmpty()){
        name[0].givenAsSingleString
    }else{
        "null"
    }
}

fun Patient.getDisplayName():String{
    return if (name.isNotEmpty()){
        name[0].givenAsSingleString
    }else{
        ""
    }
}

fun Patient.getGivenName():String{
    return if (name.isNotEmpty() && name[0].given.isNotEmpty()){
        name[0].given[0].value ?: ""
    }else{
        ""
    }
}

fun Patient.getFamilyName():String{
    return if (name.isNotEmpty()){
        name[0].family ?: ""
    }else{
        ""
    }
}

fun Patient.getCity() : String{
    return if (address.isNotEmpty()){
        address[0].city ?: ""
    }else{
        ""
    }
}

fun Patient.getState() : String{
    return if (address.isNotEmpty()){
        address[0].state ?: ""
    }else{
        ""
    }
}

fun Patient.getCountry() : String{
    return if (address.isNotEmpty()){
        address[0].country ?: ""
    }else{
        ""
    }
}

fun Patient.getDisplayAddress() : String{
    return if (address.isNotEmpty()){
        address[0].city + ", " + address[0].state + ", " + address[0].country
    }else{
        ""
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