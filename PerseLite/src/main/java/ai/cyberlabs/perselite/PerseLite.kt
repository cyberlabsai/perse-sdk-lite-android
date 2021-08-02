/**
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * | PerseLite is lib for Android applications                       |
 * | Haroldo Teruya & Victor Goulart @ Cyberlabs AI 2021             |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 */

package ai.cyberlabs.perselite

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

/**
 * This class has the classes responsibles to call the API and retrieve the Data
 */
open class PerseLite(apiKey: String) {

    val face: Face = Face()

    companion object {
        lateinit var perseAPIKey: String
        lateinit var apiInstance: PerseAPI
    }

    /**
     *  Constructor to initialize the Perse class and Retrofit API instance
     */
    init {
        perseAPIKey = apiKey

        apiInstance = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(JacksonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(PerseAPI::class.java)
    }
}