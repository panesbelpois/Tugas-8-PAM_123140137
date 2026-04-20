package com.newsreader.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val RedVelvetLightColorScheme = lightColorScheme(
    primary            = Primary,
    onPrimary          = OnPrimary,
    primaryContainer   = PrimaryContainer,
    secondary          = Secondary,
    background         = Background,
    surface            = Surface,
    surfaceVariant     = SurfaceVariant,
    onBackground       = OnBackground,
    onSurface          = OnSurface,
    onSurfaceVariant   = TextSecondary,
    outlineVariant     = DividerColor,
    scrim              = PrimaryDark,
)

private val RedVelvetDarkColorScheme = darkColorScheme(
    primary            = PrimaryLight, // Slightly brighter maroon for dark mode
    onPrimary          = OnBackgroundDark,
    primaryContainer   = PrimaryContainerDark,
    secondary          = Secondary,
    background         = BackgroundDark,
    surface            = SurfaceDark,
    surfaceVariant     = SurfaceVariantDark,
    onBackground       = OnBackgroundDark,
    onSurface          = OnSurfaceDark,
    onSurfaceVariant   = androidx.compose.ui.graphics.Color(0xFFAF9094),
    outlineVariant     = androidx.compose.ui.graphics.Color(0xFF4A3436),
    scrim              = androidx.compose.ui.graphics.Color(0xFF140202),
)

@Composable
fun NewsReaderTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) RedVelvetDarkColorScheme else RedVelvetLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = AppTypography,
        content     = content
    )
}