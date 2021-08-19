/**
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * | Perse SDK Lite Android.                                         |
 * | More About: https://www.getperse.com/                           |
 * | From CyberLabs.AI: https://cyberlabs.ai/                        |
 * | Haroldo Teruya & Victor Goulart @ Cyberlabs AI 2021             |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 */

package ai.cyberlabs.perselite

import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * This class has the interface that represents the API Call.
 */
interface PerseAPI {

    @Multipart
    @POST("face/detect")
    fun detect(
        @Header("x-api-key") apiKey: String,
        @Part("image_file") imageFile: RequestBody
    ) :Observable<PerseAPIResponse.Face.Detect>

    @Multipart
    @POST("face/compare")
    fun compare(
        @Header("x-api-key") apiKey: String,
        @Part("image_file1") imageFile1: RequestBody,
        @Part("image_file2") imageFile2: RequestBody,
    ) :Observable<PerseAPIResponse.Face.Compare>

    @Multipart
    @POST("face/enrollment")
    fun create(
        @Header("x-api-key") apiKey: String,
        @Part("image_file") imageFile: RequestBody
    ) :Observable<PerseAPIResponse.Enrollment.Face.Create>

    @GET("face/enrollment/list")
    fun read(
        @Header("x-api-key") apiKey: String
    ) :Observable<PerseAPIResponse.Enrollment.Face.Read>

    @Multipart
    @PUT("face/enrollment")
    fun update(
        @Header("x-api-key") apiKey: String,
        @Part("image_file") imageFile: RequestBody,
        @Part("user_token") userToken: String
    ) :Observable<PerseAPIResponse.Enrollment.Face.Update>

    @DELETE("face/enrollment/{user_token}")
    fun delete(
        @Header("x-api-key") apiKey: String,
        @Path("user_token") userToken: String
    ) :Observable<PerseAPIResponse.Enrollment.Face.Delete>
}