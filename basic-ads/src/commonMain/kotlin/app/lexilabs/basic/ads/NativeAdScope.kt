package app.lexilabs.basic.ads

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

/**
 * Scope for building custom native ad layouts with individual components.
 *
 * This scope provides access to all individual components of a native ad, allowing
 * complete customization of layout and styling. Each component corresponds to a
 * specific part of the native ad content.
 *
 * **Usage Example:**
 * ```kotlin
 * NativeAd(nativeAdHandler) {
 *     Column {
 *         Row {
 *             Icon(modifier = Modifier.size(48.dp))
 *             Spacer(modifier = Modifier.width(8.dp))
 *             Column(modifier = Modifier.weight(1f)) {
 *                 Headline(maxLines = 2)
 *                 Advertiser()
 *             }
 *         }
 *         MediaContent(
 *             modifier = Modifier
 *                 .fillMaxWidth()
 *                 .height(200.dp)
 *         )
 *         CallToActionButton(
 *             modifier = Modifier.fillMaxWidth()
 *         )
 *     }
 * }
 * ```
 */
@DependsOnGoogleMobileAds
public expect class NativeAdScope {

    /**
     * Displays the ad headline text.
     *
     * @param modifier Modifier to be applied to the headline text
     * @param style Text style for the headline
     * @param color Color of the headline text
     * @param maxLines Maximum number of lines for the headline
     * @param overflow How to handle text overflow
     */
    @Composable
    public fun Headline(
        modifier: Modifier = Modifier,
        style: TextStyle = TextStyle.Default,
        color: Color = Color.Unspecified,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Clip
    )

    /**
     * Displays the ad body text.
     *
     * @param modifier Modifier to be applied to the body text
     * @param style Text style for the body
     * @param color Color of the body text
     * @param maxLines Maximum number of lines for the body
     */
    @Composable
    public fun Body(
        modifier: Modifier = Modifier,
        style: TextStyle = TextStyle.Default,
        color: Color = Color.Unspecified,
        maxLines: Int = Int.MAX_VALUE
    )

    /**
     * Displays the call-to-action button.
     *
     * @param modifier Modifier to be applied to the button
     * @param contentPadding Inner padding of the button
     * @param content Custom content for the button, defaults to the ad's call-to-action text
     */
    @Composable
    public fun CallToActionButton(
        modifier: Modifier = Modifier,
        contentPadding: PaddingValues = PaddingValues(12.dp),
        content: @Composable RowScope.() -> Unit = { /* Default content will be provided by platform implementation */ }
    )

    /**
     * Displays the ad media content (image or video).
     *
     * @param modifier Modifier to be applied to the media content
     * @param contentScale How to scale the media content within its bounds
     */
    @Composable
    public fun MediaContent(
        modifier: Modifier = Modifier,
        contentScale: ContentScale = ContentScale.Crop
    )

    /**
     * Displays the ad icon.
     *
     * @param modifier Modifier to be applied to the icon
     * @param contentScale How to scale the icon within its bounds
     */
    @Composable
    public fun Icon(
        modifier: Modifier = Modifier,
        contentScale: ContentScale = ContentScale.Fit
    )

    /**
     * Displays the advertiser name.
     *
     * @param modifier Modifier to be applied to the advertiser text
     * @param style Text style for the advertiser
     * @param prefix Text to display before the advertiser name (e.g., "by ")
     */
    @Composable
    public fun Advertiser(
        modifier: Modifier = Modifier,
        style: TextStyle = TextStyle.Default,
        prefix: String = "by "
    )

    /**
     * Displays the star rating.
     *
     * @param modifier Modifier to be applied to the rating display
     * @param starColor Color of the stars
     */
    @Composable
    public fun StarRating(
        modifier: Modifier = Modifier,
        starColor: Color = Color.Yellow
    )
}