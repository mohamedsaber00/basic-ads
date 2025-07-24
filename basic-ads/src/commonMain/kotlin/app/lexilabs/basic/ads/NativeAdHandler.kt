package app.lexilabs.basic.ads

/**
 * A [NativeAdHandler] creates native ads that can be customized to match your app's design.
 *
 * This class is responsible for loading and managing the state of a native advertisement
 * provided by Google Mobile Ads. Native ads allow for complete customization of the ad
 * appearance to blend seamlessly with your app's content.
 *
 * **Usage Example:**
 *
 * ```kotlin
 * // In your Composable function or where you have access to an Activity:
 * val activity = LocalContext.current as Activity
 *
 * // Instantiate the NativeAdHandler
 * val adHandler = rememberNativeAd(activity = activity)
 *
 * // Load the Ad (typically in a LaunchedEffect or similar)
 * LaunchedEffect(Unit) {
 *   adHandler.load(
 *     adUnitId = "YOUR_ADMOB_NATIVE_AD_UNIT_ID", // Replace with your actual Ad Unit ID
 *     onLoad = { nativeAdContent ->
 *       // Ad loaded successfully with content
 *       println("Native Ad loaded with headline: ${nativeAdContent.headline}")
 *     },
 *     onFailure = { exception ->
 *       // Ad failed to load
 *       println("Native Ad failed to load: ${exception.message}")
 *     },
 *     onClick = {
 *       // User clicked on the ad
 *       println("Native Ad clicked.")
 *     },
 *     onImpression = {
 *       // Ad impression has been recorded
 *       println("Native Ad impression recorded.")
 *     }
 *   )
 * }
 *
 * // Display the ad in your Composable UI using templates
 * if (adHandler.state == AdState.READY) {
 *   NativeAd(
 *     nativeAdHandler = adHandler,
 *     template = NativeAdTemplate.MEDIUM
 *   )
 * }
 *
 * // Or create a custom layout
 * if (adHandler.state == AdState.READY) {
 *   NativeAd(adHandler) {
 *     Column {
 *       Headline(maxLines = 2)
 *       Spacer(modifier = Modifier.height(8.dp))
 *       MediaContent(modifier = Modifier.height(200.dp))
 *       CallToActionButton()
 *     }
 *   }
 * }
 * ```
 *
 * @param activity The Android `Activity` required for displaying the ad.
 *                 On platforms other than Android, this parameter might be unused or expect a platform-specific equivalent.
 */
@DependsOnGoogleMobileAds
public expect class NativeAdHandler(activity: Any?) {

    /**
     * Determines the [AdState] of the [NativeAdHandler]
     */
    public val state: AdState

    /**
     * The loaded native ad content, available when [state] is [AdState.READY]
     */
    public val nativeAdContent: NativeAdContent?

    /**
     * Loads a Native Ad.
     * Note: Make all calls to the Mobile Ads SDK on the main thread.
     *
     * To load a native ad, call [NativeAdHandler.load] method
     * and pass in an [AdUnitId] as a [String] to receive the loaded ad.
     * 
     * @param adUnitId Your Native Ad AdUnitId [String] from AdMob
     * @param onLoad Callback with [NativeAdContent] after the ad loads successfully
     * @param onFailure Callback with [Exception] when ad fails to load
     * @param onClick Callback when the ad is clicked
     * @param onImpression Callback after the ad makes an impression
     * @see [AdUnitId.autoSelect]
     * @see [AdUnitId.NATIVE_DEFAULT]
     */
    @Suppress("Unused Parameter")
    public fun load(
        adUnitId: String = AdUnitId.NATIVE_DEFAULT,
        onLoad: (NativeAdContent) -> Unit = {},
        onFailure: (Exception) -> Unit = {},
        onClick: () -> Unit = {},
        onImpression: () -> Unit = {}
    )

    /**
     * Destroys the native ad and releases resources.
     * Call this method when the ad is no longer needed to prevent memory leaks.
     */
    public fun destroy()
}