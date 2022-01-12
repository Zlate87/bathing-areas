package com.example.zlatko.beaches.base.injection

import android.content.Context
import androidx.room.Room
import com.example.zlatko.beaches.base.client.brandenburg.BrandenburgClient
import com.example.zlatko.beaches.base.repository.AppDatabase
import com.example.zlatko.beaches.base.service.location.LocationService
import com.example.zlatko.beaches.base.service.location.ModelConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BaseModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideBrandenburgClient(client: OkHttpClient): BrandenburgClient {
        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(BrandenburgClient.BASE_URL)
            .addConverterFactory(getConverter())
            .build()
        return retrofit.create(BrandenburgClient::class.java)
    }

    @Provides
    @Singleton
    fun provideLocationService(
        appDatabase: AppDatabase,
        brandenburgClient: BrandenburgClient,
        modelConverter: ModelConverter
    ) = LocationService(appDatabase.locationDao(), brandenburgClient, modelConverter)

    @Provides
    @Singleton
    fun providePlacemarkToLocationConverter() = ModelConverter()

    @Provides
    @Singleton
    fun provideApplicationDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "database"
        ).build()
    }

    private fun getConverter(): SimpleXmlConverterFactory {
        return SimpleXmlConverterFactory.createNonStrict(
            Persister(
                AnnotationStrategy()
            )
        )
    }
}