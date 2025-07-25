package app.lexilabs.basic.ads

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import app.lexilabs.basic.logging.Log
import cocoapods.Google_Mobile_Ads_SDK.GADAdLoader
import cocoapods.Google_Mobile_Ads_SDK.GADAdLoaderAdTypeNative
import cocoapods.Google_Mobile_Ads_SDK.GADAdLoaderDelegateProtocol
import cocoapods.Google_Mobile_Ads_SDK.GADNativeAd
import cocoapods.Google_Mobile_Ads_SDK.GADNativeAdDelegateProtocol
import cocoapods.Google_Mobile_Ads_SDK.GADRequest
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSError
import platform.UIKit.UIViewController
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
public actual class NativeAdHandler actual constructor(activity: Any?) {

    private val tag = "NativeAd"
    private val _state: MutableState<AdState> = mutableStateOf(AdState.NONE)
    private val _nativeAdContent: MutableState<NativeAdContent?> = mutableStateOf(null)

    public var nativeAd: GADNativeAd? = null
        private set

    private var adLoader: GADAdLoader? = null

    /**
     * Determines the [AdState] of the [NativeAdHandler]
     */
    public actual val state: AdState by _state

    /**
     * The loaded native ad content, available when [state] is [AdState.READY]
     */
    public actual val nativeAdContent: NativeAdContent? by _nativeAdContent

    public actual fun load(
        adUnitId: String,
        onLoad: (NativeAdContent) -> Unit,
        onFailure: (Exception) -> Unit,
        onClick: () -> Unit,
        onImpression: () -> Unit
    ) {
        _state.value = AdState.LOADING
        Log.d(tag, "load:starting")

        val viewController = getCurrentViewController()
        checkNotNull(viewController) { "Root ViewController is null for Native Ads" }

        // Simplified delegate implementation
        val delegate = object : NSObject(), GADAdLoaderDelegateProtocol {
            override fun adLoader(adLoader: GADAdLoader, didFailToReceiveAdWithError: NSError) {
                Log.e(tag, "load:failure:$didFailToReceiveAdWithError")
                _state.value = AdState.FAILING
                onFailure(AdException(didFailToReceiveAdWithError.localizedDescription))
            }
        }

        adLoader = GADAdLoader(
            adUnitID = adUnitId,
            rootViewController = viewController,
            adTypes = listOf(GADAdLoaderAdTypeNative),
            options = null
        ).apply {
            setDelegate(delegate)
        }

        adLoader?.loadRequest(GADRequest())
    }

    public actual fun destroy() {
        nativeAd = null
        adLoader = null
        _nativeAdContent.value = null
        _state.value = AdState.NONE
        Log.d(tag, "destroy:completed")
    }
}