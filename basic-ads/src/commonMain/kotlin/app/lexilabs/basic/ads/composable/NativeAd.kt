package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.lexilabs.basic.ads.AdUnitId
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds
import app.lexilabs.basic.ads.NativeAdHandler
import app.lexilabs.basic.ads.NativeAdScope
import app.lexilabs.basic.ads.NativeAdTemplate

/**
 * Loads and displays a Native Ad using a custom layout with complete control over component placement.
 *
 * This composable provides the most flexibility for native ad presentation, allowing you to
 * arrange individual ad components (headline, body, media, etc.) in any layout configuration.
 *
 * **Usage Example:**
 * ```kotlin
 * val nativeAdHandler by rememberNativeAd(activity)
 * 
 * NativeAd(nativeAdHandler) {
 *     Card {
 *         Column(modifier = Modifier.padding(16.dp)) {
 *             Row {
 *                 Icon(modifier = Modifier.size(48.dp))
 *                 Spacer(modifier = Modifier.width(12.dp))
 *                 Column(modifier = Modifier.weight(1f)) {
 *                     Headline(maxLines = 2)
 *                     Advertiser()
 *                 }
 *             }
 *             MediaContent(
 *                 modifier = Modifier
 *                     .fillMaxWidth()
 *                     .height(200.dp)
 *                     .clip(RoundedCornerShape(8.dp))
 *             )
 *             CallToActionButton(modifier = Modifier.fillMaxWidth())
 *         }
 *     }
 * }
 * ```
 *
 * @param nativeAdHandler The [NativeAdHandler] that manages the ad loading and state
 * @param modifier Modifier to be applied to the native ad container
 * @param content Lambda with [NativeAdScope] receiver that defines the ad layout using individual components
 */
@DependsOnGoogleMobileAds
@Composable
public expect fun NativeAd(
    nativeAdHandler: NativeAdHandler,
    modifier: Modifier = Modifier,
    content: @Composable NativeAdScope.() -> Unit
)

/**
 * Loads and displays a Native Ad using a predefined template.
 *
 * This composable provides quick setup with professionally designed templates
 * that work well in most scenarios without requiring custom layout implementation.
 *
 * **Usage Example:**
 * ```kotlin
 * val nativeAdHandler by rememberNativeAd(activity)
 * 
 * // Use a medium template for balanced content display
 * NativeAd(
 *     nativeAdHandler = nativeAdHandler,
 *     template = NativeAdTemplate.MEDIUM
 * )
 * 
 * // Use a large template for prominent placement
 * NativeAd(
 *     nativeAdHandler = nativeAdHandler,
 *     template = NativeAdTemplate.LARGE
 * )
 * ```
 *
 * @param nativeAdHandler The [NativeAdHandler] that manages the ad loading and state
 * @param template The predefined layout template to use
 * @param modifier Modifier to be applied to the native ad container
 */
@DependsOnGoogleMobileAds
@Composable
public expect fun NativeAd(
    nativeAdHandler: NativeAdHandler,
    template: NativeAdTemplate = NativeAdTemplate.MEDIUM,
    modifier: Modifier = Modifier
)

/**
 * Loads and displays a Native Ad with automatic ad loading and template-based layout.
 *
 * This composable handles both ad loading and display, making it the easiest way to
 * integrate native ads. However, it provides less control over loading states and timing.
 *
 * **Usage Example:**
 * ```kotlin
 * // Simple integration with automatic loading
 * NativeAd(
 *     adUnitId = "your-native-ad-unit-id",
 *     template = NativeAdTemplate.MEDIUM,
 *     activity = activity,
 *     onAdLoaded = {
 *         println("Native ad loaded successfully")
 *     },
 *     onAdFailedToLoad = { exception ->
 *         println("Failed to load native ad: ${exception.message}")
 *     }
 * )
 * ```
 *
 * @param adUnitId Your Native Ad AdUnitId [String] from AdMob
 * @param template The predefined layout template to use
 * @param activity The Android `Activity` or iOS context required for ad loading
 * @param modifier Modifier to be applied to the native ad container
 * @param onAdLoaded Callback executed when the ad loads successfully
 * @param onAdFailedToLoad Callback executed when ad loading fails
 * @see AdUnitId.autoSelect
 * @see AdUnitId.NATIVE_DEFAULT
 */
@DependsOnGoogleMobileAds
@Composable
public expect fun NativeAd(
    adUnitId: String = AdUnitId.NATIVE_DEFAULT,
    template: NativeAdTemplate = NativeAdTemplate.MEDIUM,
    activity: Any?,
    modifier: Modifier = Modifier,
    onAdLoaded: () -> Unit = {},
    onAdFailedToLoad: (Exception) -> Unit = {}
)