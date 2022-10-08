package com.example.zlatko.beaches.data.berlin


import okhttp3.ResponseBody
import retrofit2.http.GET

interface BerlinDataNetworkDataSource {
    companion object {
        const val BASE_URL = "https://flsshygn-lageso-berlin-prediction-merge-dev.s3.eu-central-1.amazonaws.com"
        const val CSV_PATH = "app/data.csv"
    }

    @GET(CSV_PATH)
    suspend fun getDataInCSV(): ResponseBody?
}