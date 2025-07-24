package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import app.lexilabs.basic.ads.AdState
import app.lexilabs.basic.ads.AdUnitId
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds
import app.lexilabs.basic.ads.NativeAdContent
import app.lexilabs.basic.ads.NativeAdHandler

/**
 * A Composable function that remembers a [NativeAdHandler] across compositions.
 *
 * This function creates and manages a [NativeAdHandler] instance, ensuring it persists
 * across recompositions. It automatically attempts to load an ad when the ad's state
 * is [AdState.DISMISSED] or [AdState.NONE].
 *
 * **Note:** This Composable depends on the Google Mobile Ads SDK. Ensure that the
 * SDK is properly integrated into your project.
 *
 * **Usage Example:**
 * ```kotlin
 * val nativeAdHandler by rememberNativeAd(
 *     activity = LocalContext.current as Activity,
 *     adUnitId = "your-native-ad-unit-id",
 *     onLoad = { content ->
 *         println("Native ad loaded with headline: ${content.headline}")
 *     },
 *     onFailure = { exception ->
 *         println("Native ad failed to load: ${exception.message}")
 *     }
 * )
 * 
 * // Use the handler with a template
 * if (nativeAdHandler.state == AdState.READY) {
 *     NativeAd(
 *         nativeAdHandler = nativeAdHandler,
 *         template = NativeAdTemplate.MEDIUM
 *     )
 * }
 * 
 * // Or use with custom layout
 * if (nativeAdHandler.state == AdState.READY) {
 *     NativeAd(nativeAdHandler) {
 *         Column {
 *             Headline(maxLines = 2)
 *             MediaContent(modifier = Modifier.height(200.dp))
 *             CallToActionButton()
 *         }
 *     }
 * }
 * ```
 *
 * @param activity The current Activity or Context. This is crucial for the ad to function correctly.
 *                 It's recommended to pass the result of `LocalContext.current as Activity`.
 * @param adUnitId The ad unit ID for the native ad. Defaults to [AdUnitId.NATIVE_DEFAULT].
 * @param onLoad A callback invoked when the ad has successfully loaded. Provides [NativeAdContent]
 *               with the loaded ad content.
 * @param onFailure A callback invoked when the ad fails to load. It provides an [Exception]
 *                  with details about the failure.
 * @param onClick A callback invoked when the ad is clicked by the user.
 * @param onImpression A callback invoked when an impression is recorded for the ad.
 * @return A [MutableState] holding the [NativeAdHandler]. You can use this state to
 *         interact with the ad and check its loading state.
 */
@DependsOnGoogleMobileAds
@Composable
public fun rememberNativeAd(
    activity: Any?,
    adUnitId: String = AdUnitId.NATIVE_DEFAULT,
    onLoad: (NativeAdContent) -> Unit = {},
    onFailure: (Exception) -> Unit = {},
    onClick: () -> Unit = {},
    onImpression: () -> Unit = {}
): MutableState<NativeAdHandler> {
    val ad = remember(activity) { mutableStateOf(NativeAdHandler(activity)) }
    when(ad.value.state){
        AdState.DISMISSED,
        AdState.NONE -> {
            ad.value.load(
                adUnitId = adUnitId,
                onLoad = onLoad,
                onFailure = onFailure,
                onClick = onClick,
                onImpression = onImpression
            )
        }
        else -> { /** DO NOTHING **/ }
    }
    return ad
}