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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

public actual class NativeAdScope(
    private val nativeAd: NativeAd,
    private val nativeAdView: NativeAdView
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
                    nativeAdView.headlineView?.performClick()
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
                    nativeAdView.bodyView?.performClick()
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
                    nativeAdView.callToActionView?.performClick()
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
            AndroidView(
                factory = { context ->
                    MediaView(context).apply {
                        this.mediaContent = mediaContent
                        nativeAdView.mediaView = this
                    }
                },
                modifier = modifier.clickable {
                    nativeAdView.mediaView?.performClick()
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
            icon.drawable?.let { drawable ->
                val bitmap = drawable.toBitmap()
                
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Ad Icon",
                    modifier = modifier.clickable {
                        nativeAdView.iconView?.performClick()
                    },
                    contentScale = contentScale
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
                    nativeAdView.advertiserView?.performClick()
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
                    nativeAdView.starRatingView?.performClick()
                },
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                repeat(5) { index ->
                    BasicText(
                        text = if (index < rating.toInt()) "★" else "☆",
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