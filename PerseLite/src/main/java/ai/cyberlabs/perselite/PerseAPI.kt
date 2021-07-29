/**
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * | PerseLite is lib for Android applications                       |
 * | Haroldo Teruya & Victor Goulart @ Cyberlabs AI 2021             |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 */

package ai.cyberlabs.perselite

import ai.cyberlabs.perselite.model.CompareResponse
import ai.cyberlabs.perselite.model.DetectResponse
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * This class has the interface that represents the API Call
 */
interface PerseAPI {
    @Multipart
    @POST("face/detect")
    fun detect(
        @Header("x-api-key") apiKey: String,
        @Part("image_file") imageFile: RequestBody
    ) :Observable<DetectResponse>

    @Multipart
    @POST("face/compare")
    fun compare(
        @Header("x-api-key") apiKey: String,
        @Part("image_file1") imageFile1: RequestBody,
        @Part("image_file2") imageFile2: RequestBody,
    ) :Observable<CompareResponse>
}