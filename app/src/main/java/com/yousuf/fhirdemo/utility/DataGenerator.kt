package com.yousuf.fhirdemo.utility

import org.hl7.fhir.r4.model.*
import java.util.*


/**
 * Created by Android Studio.
 * User:  Yousuf
 * Email:
 * Date:  2/6/22
 * To change this template use File | Settings | File and Code Templates.
 *
 */

object DataGenerator {

    object EcgData{
        private const val ekgLowerLimit = -3300L
        private const val ekgUpperLimit = 3300L
        private const val ekgOrigin = 2048L
        private const val ekgPeriod = 10L
        private const val ekgFactor = 1.612
        private const val ekgDimensions = 1

        const val sampleEcgData =
            "2041 2043 2037 2047 2060 2062 2051 2023 2014 2027 2034 2033 2040 2047 2047 2053 2058 2064 2059 2063 2061 2052 2053 2038 1966 1885 1884 2009 2129 2166 2137 2102 2086 2077 2067 2067 2060 2059 2062 2062 2060 2057 2045 2047 2057 2054 2042 2029 2027 2018 2007 1995 2001 2012 2024 2039 2068 2092 2111 2125 2131 2148 2137 2138 2128 2128 2115 2099 2097 2096 2101 2101 2091 2073 2076 2077 2084 2081 2088 2092 2070 2069 2074 2077 2075 2068 2064 2060 2062 2074 2075 2074 2075 2063 2058 2058 2064 2064 2070 2074 2067 2060 2062 2063 2061 2059 2048 2052 2049 2048 2051 2059 2059 2066 2077 2073"

        fun getEcgObservation(patientId:String, ecgData:String): Observation {

            val ekgObservation = Observation()
            ekgObservation.id = "Observation/ekg-$patientId"
            ekgObservation.status = Observation.ObservationStatus.FINAL
            ekgObservation.effective = DateTimeType.now()

            val ekgCode = Coding()
                .setSystem("unknown")
                .setCode("131328")
                .setDisplay("MDC_ECG_ELEC_POTL")

            ekgObservation.code.addCoding(ekgCode)

            val sampledData: SampledData = SampledData()
                .setData(ecgData)
                .setLowerLimit(ekgLowerLimit)
                .setUpperLimit(ekgUpperLimit)
                .setDimensions(ekgDimensions)
                .setPeriod(ekgPeriod)
                .setFactor(ekgFactor)
                .setOrigin(Quantity().setValue(ekgOrigin))

            ekgObservation.value = sampledData
            ekgObservation.device = Reference().setDisplay("12 lead EKG Device Metric")
            ekgObservation.subject = Reference("Patient/$patientId")
            //.setDisplay(patient.getNameFirstRep().getNameAsSingleString())

            return ekgObservation
        }

        fun getRandomEkgData(total: Int): IntArray {
            val data = IntArray(total)
            val random = Random()
            for (i in 0 until total) {
                data[i] = random.nextInt(ekgUpperLimit.toInt())
            }
            return data
        }

        fun getRandomEcgData(total:Int):String{
            val ekgData = getRandomEkgData(total)
            return ekgData.joinToString(" ")
        }

    }
}