package com.example.zlatko.beaches.data.berlin

import okhttp3.ResponseBody
import retrofit2.http.GET

interface BerlinBathingAreasNetworkDataSource {
    companion object {
        const val BASE_URL = "https://badestellen.berlin.de"
        const val CSV_PATH = "assets/data/new_build.csv"
    }

    @GET(CSV_PATH)
    suspend fun getBathingAreasInCSV(): ResponseBody?
}