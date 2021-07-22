package ai.cyberlabs.perselite

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

open class PerseLite(apiKey: String) {

    val face: Face = Face()

    companion object {
        lateinit var perseAPIKey: String
        lateinit var apiInstance: PerseAPI
    }

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