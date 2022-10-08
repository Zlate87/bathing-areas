package com.example.zlatko.beaches.data.brandenburg

import retrofit2.http.GET


interface BrandenburgBathingAreasNetworkDataSource {

    companion object {
        const val BASE_URL = "https://badestellen.brandenburg.de"
        const val IMAGE_BASE_URL = "$BASE_URL/documents/51161/63388/"
        const val DETAILS_BASE_URL = "$BASE_URL/badestelle/-/details/"
        const val KML_PATH = "badestellen/-/export/badestellen.kml"
    }

    @GET(KML_PATH)
    suspend fun getBathingAreasInKml(): Kml?
}