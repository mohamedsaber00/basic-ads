package app.lexilabs.basic.ads

/**
 * Represents the content of a native advertisement.
 *
 * This data class encapsulates all the available content fields from a native ad that can be
 * displayed in the user interface. Each field is nullable to handle cases where certain ad
 * content may not be available.
 *
 * **Usage Example:**
 * ```kotlin
 * val nativeAdContent = NativeAdContent(
 *     headline = "Amazing Product!",
 *     body = "This is the best product you'll ever find.",
 *     callToAction = "Buy Now",
 *     advertiser = "Best Company",
 *     rating = 4.5,
 *     hasIcon = true,
 *     hasMediaContent = true,
 *     hasVideo = false
 * )
 * ```
 *
 * @param headline The main headline text of the ad
 * @param body The descriptive body text of the ad
 * @param callToAction The text for the call-to-action button (e.g., "Install", "Buy Now")
 * @param advertiser The name of the advertiser or company
 * @param rating The star rating of the advertised product/service (typically 1.0 to 5.0)
 * @param hasIcon Whether the ad includes an icon image
 * @param hasMediaContent Whether the ad includes media content (image or video)
 * @param hasVideo Whether the media content specifically includes video
 */
public data class NativeAdContent(
    val headline: String?,
    val body: String?,
    val callToAction: String?,
    val advertiser: String?,
    val rating: Double?,
    val hasIcon: Boolean,
    val hasMediaContent: Boolean,
    val hasVideo: Boolean
)