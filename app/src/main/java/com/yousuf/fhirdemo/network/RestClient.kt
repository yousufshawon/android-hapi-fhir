package com.yousuf.fhirdemo.network

import ca.uhn.fhir.context.FhirContext
import ca.uhn.fhir.context.PerformanceOptionsEnum
import ca.uhn.fhir.parser.IParser
import ca.uhn.fhir.rest.client.api.IGenericClient
import ca.uhn.fhir.rest.client.api.ServerValidationModeEnum
import com.yousuf.fhirdemo.config.FhirConfig

class RestClient {

    companion object{
        private var ctx: FhirContext? = null
        private var client: IGenericClient? = null
        private var parser: IParser? = null
        private const val socketTimeOut = 20 * 1000
        private const val connectionTimeOut = 20 * 1000

        fun getContext() : FhirContext {
            return ctx ?: createFhirContext()
        }

        fun getClient() : IGenericClient{
            return client ?: createClient()
        }

        private fun createFhirContext():FhirContext{
            val fhirContext = FhirContext.forR4()

            fhirContext.apply {
                // Disable server validation (don't pull the server's metadata first)
                restfulClientFactory.serverValidationMode = ServerValidationModeEnum.NEVER

                // configure it for deferred child scanning
                setPerformanceOptions(PerformanceOptionsEnum.DEFERRED_MODEL_SCANNING)

                // timeout config
                restfulClientFactory.socketTimeout = socketTimeOut
                restfulClientFactory.connectTimeout = connectionTimeOut

            }.also {
                ctx = it
            }

            return fhirContext
        }

        private fun createClient() : IGenericClient{
            return getContext().newRestfulGenericClient(FhirConfig.SERVER_URL_SERVER_FIRE_LY).also {
                client = it
            }
        }

        public fun getParser():IParser {
            return parser ?: createParser()
        }

        private fun createParser():IParser{
            return getContext().newJsonParser().setPrettyPrint(true).also {
                parser = it
            }
        }
    }
}