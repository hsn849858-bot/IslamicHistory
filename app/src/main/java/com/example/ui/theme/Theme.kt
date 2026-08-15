package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.data.model.AppThemePalette

private fun buildColorScheme(palette: AppThemePalette, isDark: Boolean, isAmoled: Boolean): ColorScheme {
  val (primaryColor, secondaryColor) = when (palette) {
    AppThemePalette.EMERALD_GOLD -> if (isDark) EmeraldPrimaryDark to GoldSecondaryDark else EmeraldPrimaryLight to GoldSecondaryLight
    AppThemePalette.DAMASCUS_AZURE -> if (isDark) DamascusPrimaryDark to DamascusSecondaryDark else DamascusPrimaryLight to DamascusSecondaryLight
    AppThemePalette.ANDALUSIAN_RUBY -> if (isDark) AndalusianPrimaryDark to AndalusianSecondaryDark else AndalusianPrimaryLight to AndalusianSecondaryLight
    AppThemePalette.SELJUK_SAPPHIRE -> if (isDark) SeljukPrimaryDark to SeljukSecondaryDark else SeljukPrimaryLight to SeljukSecondaryLight
    AppThemePalette.DESERT_OCHRE -> if (isDark) DesertPrimaryDark to DesertSecondaryDark else DesertPrimaryLight to DesertSecondaryLight
  }

  return if (isDark) {
    val bg = if (isAmoled) AMOLEDBackground else BackgroundDark
    val surface = if (isAmoled) AMOLEDSurface else SurfaceDark
    darkColorScheme(
      primary = primaryColor,
      onPrimary = Color.Black,
      primaryContainer = primaryColor.copy(alpha = 0.25f),
      onPrimaryContainer = primaryColor,
      secondary = secondaryColor,
      onSecondary = Color.Black,
      secondaryContainer = secondaryColor.copy(alpha = 0.25f),
      onSecondaryContainer = secondaryColor,
      tertiary = IslamicGold,
      background = bg,
      surface = surface,
      surfaceVariant = if (isAmoled) Color(0xFF141916) else SurfaceVariantDark,
      onBackground = OnSurfaceDark,
      onSurface = OnSurfaceDark,
      onSurfaceVariant = OnSurfaceVariantDark,
      outline = OutlineDark,
      outlineVariant = Color(0x33D4AF37)
    )
  } else {
    lightColorScheme(
      primary = primaryColor,
      onPrimary = Color.White,
      primaryContainer = primaryColor.copy(alpha = 0.15f),
      onPrimaryContainer = primaryColor,
      secondary = secondaryColor,
      onSecondary = Color.White,
      secondaryContainer = secondaryColor.copy(alpha = 0.15f),
      onSecondaryContainer = secondaryColor,
      tertiary = IslamicGold,
      background = BackgroundLight,
      surface = SurfaceLight,
      surfaceVariant = SurfaceVariantLight,
      onBackground = OnSurfaceLight,
      onSurface = OnSurfaceLight,
      onSurfaceVariant = OnSurfaceVariantLight,
      outline = OutlineLight,
      outlineVariant = Color(0x22B8860B)
    )
  }
}

@Composable
fun IslamicHistoryTheme(
  themePalette: AppThemePalette = AppThemePalette.EMERALD_GOLD,
  darkTheme: Boolean = isSystemInDarkTheme(),
  amoledMode: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme = buildColorScheme(themePalette, darkTheme, amoledMode)
  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
