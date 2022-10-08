package com.example.zlatko.beaches.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.zlatko.beaches.data.FavoritesRepository
import com.example.zlatko.beaches.data.berlin.BerlinBathingAreasNetworkDataSource
import com.example.zlatko.beaches.data.berlin.BerlinDataNetworkDataSource
import com.example.zlatko.beaches.data.brandenburg.BrandenburgBathingAreasNetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Singleton

private const val USER_PREFERENCES = "user_preferences"

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


    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(appContext,USER_PREFERENCES)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(USER_PREFERENCES) }
        )
    }

    @Provides
    @Singleton
    fun provideBrandenburgDataSource(client: OkHttpClient): BrandenburgBathingAreasNetworkDataSource {
        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(BrandenburgBathingAreasNetworkDataSource.BASE_URL)
            .addConverterFactory(getConverter())
            .build()
        return retrofit.create(BrandenburgBathingAreasNetworkDataSource::class.java)
    }

    @Provides
    @Singleton
    fun provideBerlinBathingAreasNetworkDataSource(client: OkHttpClient): BerlinBathingAreasNetworkDataSource {
        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(BerlinBathingAreasNetworkDataSource.BASE_URL)
            .build()
        return retrofit.create(BerlinBathingAreasNetworkDataSource::class.java)
    }

    @Provides
    @Singleton
    fun provideBerlinDataNetworkDataSource(client: OkHttpClient): BerlinDataNetworkDataSource {
        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(BerlinDataNetworkDataSource.BASE_URL)
            .build()
        return retrofit.create(BerlinDataNetworkDataSource::class.java)
    }

    private fun getConverter(): SimpleXmlConverterFactory {
        return SimpleXmlConverterFactory.createNonStrict(
            Persister(
                AnnotationStrategy()
            )
        )
    }
}