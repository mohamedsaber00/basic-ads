package app.lexilabs.basic.ads

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.UIKitView
import cocoapods.Google_Mobile_Ads_SDK.GADMediaView
import cocoapods.Google_Mobile_Ads_SDK.GADNativeAd
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIImageView

@OptIn(ExperimentalForeignApi::class)
public actual class NativeAdScope(
    private val nativeAd: GADNativeAd
) {

    @Composable
    public actual fun Headline(
        modifier: Modifier,
        style: TextStyle,
        color: Color,
        maxLines: Int,
        overflow: TextOverflow
    ) {
        nativeAd.headline?.let { headline ->
            BasicText(
                text = headline,
                modifier = modifier.clickable {
                    // Click handling is managed by the native ad view
                },
                style = style.copy(color = color),
                maxLines = maxLines,
                overflow = overflow
            )
        }
    }

    @Composable
    public actual fun Body(
        modifier: Modifier,
        style: TextStyle,
        color: Color,
        maxLines: Int
    ) {
        nativeAd.body?.let { body ->
            BasicText(
                text = body,
                modifier = modifier.clickable {
                    // Click handling is managed by the native ad view
                },
                style = style.copy(color = color),
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

    @Composable
    public actual fun CallToActionButton(
        modifier: Modifier,
        contentPadding: PaddingValues,
        content: @Composable RowScope.() -> Unit
    ) {
        val callToActionText = nativeAd.callToAction ?: "Learn More"
        
        Box(
            modifier = modifier
                .background(
                    color = Color.Blue,
                    shape = RoundedCornerShape(4.dp)
                )
                .clickable {
                    // Click handling is managed by the native ad view
                }
                .padding(contentPadding)
        ) {
            Row {
                if (content === { /* Default content will be provided by platform implementation */ }) {
                    BasicText(
                        text = callToActionText,
                        style = TextStyle(color = Color.White)
                    )
                } else {
                    content()
                }
            }
        }
    }

    @Composable
    public actual fun MediaContent(
        modifier: Modifier,
        contentScale: ContentScale
    ) {
        nativeAd.mediaContent?.let { mediaContent ->
            UIKitView(
                factory = {
                    GADMediaView().apply {
                        setMediaContent(mediaContent)
                    }
                },
                modifier = modifier.clickable {
                    // Click handling is managed by the native ad view
                }
            )
        }
    }

    @Composable
    public actual fun Icon(
        modifier: Modifier,
        contentScale: ContentScale
    ) {
        nativeAd.icon?.let { icon ->
            icon.image?.let { uiImage ->
                UIKitView(
                    factory = {
                        UIImageView(uiImage)
                    },
                    modifier = modifier.clickable {
                        // Click handling is managed by the native ad view
                    }
                )
            }
        }
    }

    @Composable
    public actual fun Advertiser(
        modifier: Modifier,
        style: TextStyle,
        prefix: String
    ) {
        nativeAd.advertiser?.let { advertiser ->
            BasicText(
                text = "$prefix$advertiser",
                modifier = modifier.clickable {
                    // Click handling is managed by the native ad view
                },
                style = style
            )
        }
    }

    @Composable
    public actual fun StarRating(
        modifier: Modifier,
        starColor: Color
    ) {
        nativeAd.starRating?.let { rating ->
            Row(
                modifier = modifier.clickable {
                    // Click handling is managed by the native ad view
                },
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                repeat(5) { index ->
                    BasicText(
                        text = if (index < rating.doubleValue.toInt()) "★" else "☆",
                        style = TextStyle(
                            color = starColor,
                            fontSize = 16.sp
                        ),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}