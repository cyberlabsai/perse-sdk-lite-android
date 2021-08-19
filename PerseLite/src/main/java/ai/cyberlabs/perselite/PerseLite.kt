/**
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * | Perse SDK Lite Android.                                         |
 * | More About: https://www.getperse.com/                           |
 * | From CyberLabs.AI: https://cyberlabs.ai/                        |
 * | Haroldo Teruya & Victor Goulart @ Cyberlabs AI 2021             |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 */

package ai.cyberlabs.perselite

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

/**
 * This class has the classes responsible to call the API and retrieve the Data.
 */
open class PerseLite(
    apiKey: String,
    baseUrl: String = BuildConfig.BASE_URL
) {

    val face: Face = Face()

    companion object {
        lateinit var perseAPIKey: String
        lateinit var api: PerseAPI
    }

    /**
     *  Constructor to initialize the Perse class and Retrofit API instance.
     */
    init {
        perseAPIKey = apiKey

        api = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(JacksonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(PerseAPI::class.java)
    }
}