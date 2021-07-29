/**
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * | PerseLite is lib for Android applications                       |
 * | Haroldo Teruya & Victor Goulart @ Cyberlabs AI 2021             |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 */

package ai.cyberlabs.perselite.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

/**
 * Models to wrapper the API Response.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class CompareResponse(
    @JsonProperty("similarity") val similarity: Float,
    @JsonProperty("time_taken") val timeTaken: Float,
    @JsonProperty("default_thresholds") val thresholds: CompareThresholdsResponse
): Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
data class DetectResponse(
    @JsonProperty("total_faces") val totalFaces: Int,
    @JsonProperty("faces") val faces: List<FaceResponse>,
    @JsonProperty("image_metrics") val imageMetrics: MetricsResponse,
    @JsonProperty("time_take") val timeTaken: Float,
    @JsonProperty("default_thresholds") val thresholds: DetectThresholdsResponse
): Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
data class FaceResponse(
    @JsonProperty("landmarks") val landmarks: LandmarksResponse,
    @JsonProperty("confidence") val confidence: Int,
    @JsonProperty("bounding_box") val boundingBox: List<Int>,
    @JsonProperty("face_metrics") val faceMetrics: MetricsResponse,
    @JsonProperty("liveness_score") val livenessScore: Float
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class MetricsResponse(
    @JsonProperty("underexpose") val underexpose: Float,
    @JsonProperty("overexpose") val overexpose: Float,
    @JsonProperty("sharpness") val sharpness: Float
): Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
data class LandmarksResponse(
    @JsonProperty("right_eye") val rightEye: List<Int>,
    @JsonProperty("left_eye") val leftEye: List<Int>,
    @JsonProperty("nose") val nose: List<Int>,
    @JsonProperty("mouth_right") val mouthRight: List<Int>,
    @JsonProperty("mouth_left") val mouthLeft: List<Int>
): Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
data class DetectThresholdsResponse(
        @JsonProperty("sharpnessThreshold") val sharpnessThreshold: Float,
        @JsonProperty("underexposerThreshold") val underexposerThreshold: Float,
        @JsonProperty("overexposureThreshold") val overexposureThreshold: Float,
        @JsonProperty("livenessThreshold") val livenessThreshold: Float
): Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
data class CompareThresholdsResponse(
        @JsonProperty("similarityThreshold") val similarityThreshold: Float
): Serializable