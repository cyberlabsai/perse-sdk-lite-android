# Perse SDK Android
From [CyberLabs.AI](https://cyberlabs.ai/).  
_Ready to go biometric verification for the internet._

The Perse library SDK:
* Top notch facial detection model;
* Anti-spoofing;
* Feedback on image quality;
* Compare the similarity between two faces;
* Doesn't store any photos;

For more details, you can see the [Official Perse](https://www.getperse.com/).

> #### Soon voice biometric verification.

## Content of Table

* [About](#about)
* [Get Started](#get-started)
  * [Install](#install)
  * [Get API Key](#get-api-key)
* [Usage](#usage)
  * [Face Detect](#face-detect)
  * [Face Compare](#face-compare)
* [API](#api)
  * [Methods](#methods)
    * [face.detect](#face.detect)
    * [face.compare](#face.compare)
  * [Responses](#responses)
  * [Errors](#errors)
* [To Contribute and Make It Better](#to-contribute-and-make-it-better)

## About

This SDK provides abstracts the communication with the Perse's API endpoints and also convert the response from json to a pre-defined [responses](#responses).

> #### Want to test the endpoints?
> You can test our endpoints using this [Swagger UI Playground](https://api.getperse.com/swagger/).

> #### Want to test a web live demo?
> You can test our web live demos in the [CyberLabs.AI CodePen](https://codepen.io/cyberlabsai) or in the [Perse Oficial Docs](https://docs.getperse.com/sdk-js/demo.html#authentication-demo
). Do not forget your [API Key](#get-api-key).

> #### Want to try a backend client?
> We have some examples in `Python`, `Go` and `javaScript`.
> You can see documented [here](https://docs.getperse.com/face-api/#introduction).

## Get Started

### Install

1. Add the JitPack repository to your root `build.gradle` at the end of repositories:

```groovy
allprojects {
	repositories {
	..
	maven { url 'https://jitpack.io' }
	}
}
```

2. Add the dependency:

```groovy
dependencies {
	implementation 'com.github.cyberlabsai:perse-sdk-android'
}
```

3. Build project.

### API Key

Perse API authenticates your requests using an API Key.
We are currently in Alpha. So you can get your API Key:
1. Sending an email to [developer@getperse.com](mailto:%20developer@getperse.com);
2. Or in the Perse official site [https://www.getperse.com/](https://www.getperse.com/);

## Usage

### Face Detect

Detect allows you process images with the intent of detecting human faces.

```kotlin
import ai.cyberlabs.perse.sdk.Perse

fun detect(file: String) {
    val perse = Perse("API_KEY")

    perse.face.detect(
        file,
        { detectResponse ->
            // See the DetectResponse.
        },
        { error ->
            Log.d("Perse", error)
        }
    )
}
```

### Face Compare

Compare accepts two sources for similarity comparison.

```kotlin
import ai.cyberlabs.perse.sdk.Perse

fun compare(firstFile, secondFile) {
    val perse = Perse("API_KEY")

    perse.face.compare(
        firstFile,
        secondFile,
        { compareResponse ->
            // See the CompareResponse.
        },
        { error ->
            Log.d("Perse", error)
        }
    )
}
```

## API

This section describes the Perse SDK Android API's, [methods](#methods), your [responses](#responses) and possible [errors](#errors).

### Methods

The Perse is in `alpha` version and for now, only the `Face` module is available.

#### face.detect

* Has the intent of detecting any number of human faces;
* Can use this resource to evaluate the overall quality of the image;
* The input can be the image file path or his [Data](https://developer.apple.com/documentation/foundation/data);
* The `onSuccess` return type is [DetectResponse](#detectresponse) struct;
* The `onError` return type can see in the [Errors](#errors);

```kotlin
fun detect(
    file: String,
    onSuccess: (DetectResponse) -> Unit,
    onError: (String) -> Unit
)
```

```kotlin
fun detect(
    file: Data,
    onSuccess: (DetectResponse) -> Unit,
    onError: (String) -> Unit
)
```

#### face.compare

* Accepts two sources for similarity comparison;
* The inputs can be the image file paths or his [Data's](https://developer.apple.com/documentation/foundation/data);
* The `onSuccess` return type is [CompareResponse](#compareresponse) struct;
* The `onError` return type can see in the [Errors](#errors);

```kotlin
fun compare(
    file: String,
    file: String,
    onSuccess: (CompareResponse) -> Unit,
    onError: (String) -> Unit
)
```

```kotlin
fun compare(
    file: Data,
    file: Data,
    onSuccess: (CompareResponse) -> Unit,
    onError: (String) -> Unit
)
```

> #### Tip
> We recommend considering a match when similarity is above `71`.

### Responses

#### CompareResponse

| Attribute   | Type            | Description
| -           | -               | -
| similarity  | `Long`          | Similarity between faces. Closer to `1` is better.
| imageTokens | `List<String>`  | The image tokens array.
| timeTaken   | `Long`          | Time taken to analyze the image.

#### DetectResponse

| Attribute    | Type                                | Description
| -            | -                                   | -
| totalFaces   | `Int`                               | Total of faces in the image.
| faces        | `List<FaceResponse>`                | List of [FaceResponse](#faceresponse).
| imageMetrics | [MetricsResponse](#metricsresponse) | Metrics of the detected image.
| imageToken   | `String`                            | The image token.
| timeTaken    | `Long`                              | Time taken to analyze the image.

#### FaceResponse

| Attribute     | Type                                    | Description
| -             | -                                       | -
| landmarks     | [LandmarksResponse](#landmarksresponse) |  Detected face landmarks.
| confidence    | `Int` 						          | Confidence that the face is a real face.
| boundingBox   | `List<Int>`                        	  | List with the four values of the face bounding box. The coordinates `x`, `y` and the dimension `width` and `height` respectively.
| faceMetrics   | [MetricsResponse](#metricsresponse)     | Metrics of the detecting face.
| livenessScore | `Long`                                  | Confidence that a detected face is from a live person (1 means higher confidence).

#### MetricsResponse

| Attribute   | Type   | Description
| -           | -      | -
| underexpose | `Long` | Indicates loss of shadow detail. Closer to `0` is better.
| overexpose  | `Long` | Indicates loss of highlight detail. Closer to `0` is better.
| sharpness   | `Long` | Indicates intensity of motion blur. Closer to `1` is better.

#### LandmarksResponse

| Attribute  | Type        | Description
| -          | -           | -
| rightEye   | `List<Int>` | Right eye landmarks.
| leftEye    | `List<Int>` | Left eye landmarks.
| nose       | `List<Int>` | Nose landmarks.
| mouthRight | `List<Int>` | Right side of mouth landmarks.
| mouthLeft  | `List<Int>` | Left side of mouth landmarks.

### Errors

| Error Code | Description
| -          | -
| 400        | The request was unacceptable, often due to missing a required parameter.
| 401        | API key is missing or invalid.
| 402        | The parameters were valid but the request failed.
| 415        | The content type or encoding is not valid.

## To Contribute and Make It Better

Clone the repo, change what you want and send PR.  
For commit messages we use <a href="https://www.conventionalcommits.org/">Conventional Commits</a>.

Contributions are always welcome!

---

Made with ❤ by the [**Cyberlabs AI**](https://cyberlabs.ai/)
