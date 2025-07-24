package app.lexilabs.basic.ads

/**
 * Pre-defined template layouts for native advertisements.
 *
 * These templates provide quick and easy ways to display native ads without requiring
 * custom layout implementation. Each template is optimized for different use cases
 * and screen real estate requirements.
 *
 * **Usage Example:**
 * ```kotlin
 * // Use a medium template for most scenarios
 * NativeAd(
 *     nativeAdHandler = adHandler,
 *     template = NativeAdTemplate.MEDIUM
 * )
 *
 * // Use a small template for compact spaces
 * NativeAd(
 *     nativeAdHandler = adHandler,
 *     template = NativeAdTemplate.SMALL
 * )
 * ```
 *
 * @see NativeAdTemplate.SMALL Compact horizontal layout with icon, headline, and CTA
 * @see NativeAdTemplate.MEDIUM Standard vertical layout with media content
 * @see NativeAdTemplate.LARGE Full-featured layout with all available content
 */
public enum class NativeAdTemplate {
    /**
     * Small template optimized for compact spaces.
     * 
     * **Layout:** Horizontal arrangement
     * **Contents:** Icon + Headline + Call-to-Action button
     * **Best for:** In-feed ads, list items, minimal space requirements
     */
    SMALL,

    /**
     * Medium template for balanced content display.
     * 
     * **Layout:** Vertical arrangement  
     * **Contents:** Icon + Headline + Body + Media + Advertiser + Call-to-Action
     * **Best for:** Content feeds, article integrations, standard placements
     */
    MEDIUM,

    /**
     * Large template with full content showcase.
     * 
     * **Layout:** Vertical arrangement with expanded media
     * **Contents:** All available content including ratings and expanded descriptions
     * **Best for:** Dedicated ad spaces, prominent placements, maximum engagement
     */
    LARGE
}