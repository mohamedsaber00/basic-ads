package app.lexilabs.basic.ads.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import app.lexilabs.basic.ads.AdState
import app.lexilabs.basic.ads.AdUnitId
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds
import app.lexilabs.basic.ads.NativeAdHandler
import app.lexilabs.basic.ads.NativeAdScope
import app.lexilabs.basic.ads.NativeAdTemplate
import cocoapods.Google_Mobile_Ads_SDK.GADNativeAdView
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class, DependsOnGoogleMobileAds::class)
@Composable
public actual fun NativeAd(
    nativeAdHandler: NativeAdHandler,
    modifier: Modifier,
    content: @Composable NativeAdScope.() -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val nativeAd = nativeAdHandler.nativeAd

    // Manage the native ad lifecycle
    DisposableEffect(lifecycleOwner, nativeAdHandler) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_DESTROY -> nativeAdHandler.destroy()
                else -> { /* Do nothing for other events */ }
            }
        }
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
            nativeAdHandler.destroy()
        }
    }

    if (nativeAd != null && nativeAdHandler.state == AdState.READY) {
        // For iOS, we need to create a native ad view and integrate with Compose
        // This is a simplified version - in a full implementation, we would need
        // more sophisticated integration between Compose and GADNativeAdView
        UIKitView(
            factory = {
                val nativeAdView = GADNativeAdView()
                nativeAdView.setNativeAd(nativeAd)
                nativeAdView
            },
            modifier = modifier,
            update = { nativeAdView ->
                // Update the native ad view if needed
                if (nativeAdView.nativeAd != nativeAd) {
                    nativeAdView.setNativeAd(nativeAd)
                }
            }
        )
        
        // Note: In a complete implementation, we would need to render the Compose content
        // within or alongside the native ad view. This requires more complex integration
        // between UIKit and Compose that is beyond the scope of this basic implementation.
    }
}

@OptIn(DependsOnGoogleMobileAds::class)
@Composable
public actual fun NativeAd(
    nativeAdHandler: NativeAdHandler,
    template: NativeAdTemplate,
    modifier: Modifier
) {
    when (template) {
        NativeAdTemplate.SMALL -> {
            NativeAd(nativeAdHandler, modifier) {
                SmallNativeAdTemplate()
            }
        }
        NativeAdTemplate.MEDIUM -> {
            NativeAd(nativeAdHandler, modifier) {
                MediumNativeAdTemplate()
            }
        }
        NativeAdTemplate.LARGE -> {
            NativeAd(nativeAdHandler, modifier) {
                LargeNativeAdTemplate()
            }
        }
    }
}

@OptIn(DependsOnGoogleMobileAds::class)
@Composable
public actual fun NativeAd(
    adUnitId: String,
    template: NativeAdTemplate,
    activity: Any?,
    modifier: Modifier,
    onAdLoaded: () -> Unit,
    onAdFailedToLoad: (Exception) -> Unit
) {
    val nativeAdHandler by rememberNativeAd(
        activity = activity,
        adUnitId = adUnitId,
        onLoad = { onAdLoaded() },
        onFailure = onAdFailedToLoad
    )

    NativeAd(
        nativeAdHandler = nativeAdHandler,
        template = template,
        modifier = modifier
    )
}

// Template implementations
@Composable
private fun NativeAdScope.SmallNativeAdTemplate() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Headline(maxLines = 1)
            Spacer(modifier = Modifier.height(4.dp))
            Advertiser()
        }
        Spacer(modifier = Modifier.width(8.dp))
        CallToActionButton()
    }
}

@Composable
private fun NativeAdScope.MediumNativeAdTemplate() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Headline(maxLines = 2)
                    Spacer(modifier = Modifier.height(4.dp))
                    Advertiser()
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Body(maxLines = 3)
            
            Spacer(modifier = Modifier.height(12.dp))
            
            MediaContent(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            CallToActionButton(
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun NativeAdScope.LargeNativeAdTemplate() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Headline(maxLines = 2)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Advertiser(modifier = Modifier.weight(1f))
                        StarRating()
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Body(maxLines = 4)
            
            Spacer(modifier = Modifier.height(12.dp))
            
            MediaContent(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            CallToActionButton(
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}