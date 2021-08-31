/**
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * | Perse SDK Lite Android.                                         |
 * | More About: https://www.getperse.com/                           |
 * | From CyberLabs.AI: https://cyberlabs.ai/                        |
 * | Haroldo Teruya & Victor Goulart @ Cyberlabs AI 2021             |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

/**
 * Models to wrapper the API Response.
 */

class PerseAPIResponse {
    class Face {

        @JsonIgnoreProperties(ignoreUnknown = true)
        data class Compare(
            @JsonProperty("similarity") val similarity: Float,
            @JsonProperty("time_taken") val timeTaken: Float,
            @JsonProperty("default_thresholds") val defaultThresholds: CompareThresholds
        ): Serializable

        @JsonIgnoreProperties(ignoreUnknown = true)
        data class Detect(
            @JsonProperty("total_faces") val totalFaces: Int,
            @JsonProperty("faces") val faces: List<Face>,
            @JsonProperty("image_metrics") val imageMetrics: Metrics,
            @JsonProperty("time_taken") val timeTaken: Float,
            @JsonProperty("default_thresholds") val defaultThresholds: DetectThresholds
        ): Serializable

        @JsonIgnoreProperties(ignoreUnknown = true)
        data class Face(
            @JsonProperty("landmarks") val landmarks: Landmarks,
            @JsonProperty("bounding_box") val boundingBox: List<Int>,
            @JsonProperty("face_metrics") val faceMetrics: Metrics,
            @JsonProperty("liveness_score") val livenessScore: Float
        )

        @JsonIgnoreProperties(ignoreUnknown = true)
        data class Metrics(
            @JsonProperty("underexposure") val underexposure: Float,
            @JsonProperty("overexposure") val overexposure: Float,
            @JsonProperty("sharpness") val sharpness: Float
        ): Serializable

        @JsonIgnoreProperties(ignoreUnknown = true)
        data class Landmarks(
            @JsonProperty("right_eye") val rightEye: List<Int>,
            @JsonProperty("left_eye") val leftEye: List<Int>,
            @JsonProperty("nose") val nose: List<Int>,
            @JsonProperty("mouth_right") val mouthRight: List<Int>,
            @JsonProperty("mouth_left") val mouthLeft: List<Int>
        ): Serializable

        @JsonIgnoreProperties(ignoreUnknown = true)
        data class DetectThresholds(
            @JsonProperty("sharpness") val sharpness: Float,
            @JsonProperty("underexposure") val underexposure: Float,
            @JsonProperty("overexposure") val overexposure: Float,
            @JsonProperty("liveness") val liveness: Float
        ): Serializable

        @JsonIgnoreProperties(ignoreUnknown = true)
        data class CompareThresholds(
            @JsonProperty("similarity") val similarity: Float
        ): Serializable

        class Enrollment {
            @JsonIgnoreProperties(ignoreUnknown = true)
            data class Create(
                @JsonProperty("user_token") val userToken: String,
                @JsonProperty("time_taken") val timeTaken: Float,
                @JsonProperty("status") val status: Int,
            ): Serializable

            @JsonIgnoreProperties(ignoreUnknown = true)
            data class Read(
                @JsonProperty("user_tokens") val userTokens: List<String>,
                @JsonProperty("total_users") val totalUsers: Int,
                @JsonProperty("time_taken") val timeTaken: Float,
                @JsonProperty("status") val status: Int,
            ): Serializable

            @JsonIgnoreProperties(ignoreUnknown = true)
            data class Update(
                @JsonProperty("user_token") val userToken: String,
                @JsonProperty("time_taken") val timeTaken: Float,
                @JsonProperty("status") val status: Int,
            ): Serializable

            @JsonIgnoreProperties(ignoreUnknown = true)
            data class Delete(
                @JsonProperty("time_taken") val timeTaken: Float,
                @JsonProperty("status") val status: Int,
            ): Serializable
        }
    }
}