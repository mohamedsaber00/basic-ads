package app.lexilabs.basic.ads

import android.app.Activity
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import app.lexilabs.basic.logging.Log
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd

public actual class NativeAdHandler actual constructor(activity: Any?) {

    private val tag = "NativeAd"
    private val context: Activity
    private val _state: MutableState<AdState> = mutableStateOf(AdState.NONE)
    private val _nativeAdContent: MutableState<NativeAdContent?> = mutableStateOf(null)

    public var nativeAd: NativeAd? = null
        private set

    /**
     * Determines the [AdState] of the [NativeAdHandler]
     */
    public actual val state: AdState by _state

    /**
     * The loaded native ad content, available when [state] is [AdState.READY]
     */
    public actual val nativeAdContent: NativeAdContent? by _nativeAdContent

    init {
        require(activity != null) {
            _state.value = AdState.FAILING
            "Android NativeAds requires Activity Context to be a non-null"
        }
        require(activity is Activity) {
            _state.value = AdState.FAILING
            "`activity` variable must be of the Android `Activity` type"
        }
        context = activity
    }

    @RequiresPermission("android.permission.INTERNET")
    public actual fun load(
        adUnitId: String,
        onLoad: (NativeAdContent) -> Unit,
        onFailure: (Exception) -> Unit,
        onClick: () -> Unit,
        onImpression: () -> Unit
    ) {
        _state.value = AdState.LOADING
        Log.d(tag, "loadNativeAd: Loading")

        val adLoader = AdLoader.Builder(context, adUnitId)
            .forNativeAd { ad ->
                nativeAd = ad
                val content = NativeAdContent(
                    headline = ad.headline,
                    body = ad.body,
                    callToAction = ad.callToAction,
                    advertiser = ad.advertiser,
                    rating = if (ad.starRating != null) ad.starRating!!.toDouble() else null,
                    hasIcon = ad.icon != null,
                    hasMediaContent = ad.mediaContent != null,
                    hasVideo = ad.mediaContent?.hasVideoContent() == true
                )
                _nativeAdContent.value = content
                _state.value = AdState.READY
                Log.d(tag, "loadNativeAd: Success")
                onLoad(content)
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    super.onAdFailedToLoad(error)
                    _state.value = AdState.FAILING
                    Log.d(tag, "loadNativeAd: Failure: $error")
                    onFailure(AdException(error.message))
                }

                override fun onAdImpression() {
                    super.onAdImpression()
                    _state.value = AdState.SHOWING
                    Log.d(tag, "loadNativeAd: Impression")
                    onImpression()
                }

                override fun onAdClicked() {
                    super.onAdClicked()
                    Log.d(tag, "loadNativeAd: Clicked")
                    onClick()
                }
            })
            .build()

        adLoader.loadAd(AdRequest.Builder().build())
    }

    public actual fun destroy() {
        nativeAd?.destroy()
        nativeAd = null
        _nativeAdContent.value = null
        _state.value = AdState.NONE
        Log.d(tag, "destroyNativeAd: Destroyed")
    }
}