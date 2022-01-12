package com.example.zlatko.beaches.base.client.brandenburg

import com.example.zlatko.beaches.base.client.brandenburg.model.Kml
import retrofit2.http.GET


interface BrandenburgClient {

    companion object {
        const val BASE_URL = "https://badestellen.brandenburg.de"
        const val IMAGE_BASE_URL = "${BASE_URL}/documents/823102/1095130/"
        const val DETAILS_BASE_URL = "${BASE_URL}/badestelle/-/details/"
        const val KML_PATH = "badestellen/-/export/badestellen.kml"
    }

    @GET(KML_PATH)
    suspend fun getLocationsInKml(): Kml?
}