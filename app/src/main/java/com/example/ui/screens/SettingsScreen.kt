package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppThemePalette
import com.example.ui.AppUiState
import com.example.ui.HistoryViewModel
import com.example.ui.components.IslamicHeader
import com.example.ui.theme.AndalusianPrimaryLight
import com.example.ui.theme.DamascusPrimaryLight
import com.example.ui.theme.DesertPrimaryLight
import com.example.ui.theme.EmeraldPrimaryLight
import com.example.ui.theme.GoldSecondaryLight
import com.example.ui.theme.IslamicGold
import com.example.ui.theme.SeljukPrimaryLight

@Composable
fun SettingsScreen(
  uiState: AppUiState,
  viewModel: HistoryViewModel,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
  ) {
    IslamicHeader(
      title = "المظهر والإعدادات العامة",
      subtitle = "تخصيص ألوان الواجهة، الخطوط، وتفضيلات القراءة الليلية",
      badgeText = "التخصيص والإحصائيات"
    )

    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
        .testTag("settings_lazy_list"),
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      // 1. Islamic Palette Theme Selector
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(imageVector = Icons.Default.Palette, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "طراز الألوان والزخرفة الإسلامية",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
              )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
              AppThemePalette.values().forEach { palette ->
                val isSelected = uiState.themePalette == palette
                val (primaryCol, secCol) = when (palette) {
                  AppThemePalette.EMERALD_GOLD -> EmeraldPrimaryLight to GoldSecondaryLight
                  AppThemePalette.DAMASCUS_AZURE -> DamascusPrimaryLight to IslamicGold
                  AppThemePalette.ANDALUSIAN_RUBY -> AndalusianPrimaryLight to GoldSecondaryLight
                  AppThemePalette.SELJUK_SAPPHIRE -> SeljukPrimaryLight to IslamicGold
                  AppThemePalette.DESERT_OCHRE -> DesertPrimaryLight to GoldSecondaryLight
                }

                Surface(
                  shape = RoundedCornerShape(14.dp),
                  color = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                  else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                  border = androidx.compose.foundation.BorderStroke(
                    1.5.dp,
                    if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
                  ),
                  modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.setThemePalette(palette) }
                    .testTag("palette_option_${palette.name}")
                ) {
                  Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    // Color Preview Circles
                    Row(modifier = Modifier.padding(end = 12.dp)) {
                      Box(modifier = Modifier.size(20.dp).clip(CircleShape).background(primaryCol))
                      Spacer(modifier = Modifier.width(4.dp))
                      Box(modifier = Modifier.size(20.dp).clip(CircleShape).background(secCol))
                    }

                    Column(modifier = Modifier.weight(1f)) {
                      Text(
                        text = palette.titleArabic,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                      )
                      Text(
                        text = palette.colorDescription,
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                      )
                    }

                    if (isSelected) {
                      Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "محدد",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                      )
                    }
                  }
                }
              }
            }
          }
        }
      }

      // 2. Dark Mode & AMOLED Mode
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(imageVector = Icons.Default.DarkMode, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "إعدادات الرؤية والوضع الليلي",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
              )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Dark Mode Switch
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text(
                  text = "الوضع الداكن (Dark Mode)",
                  style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                )
                Text(
                  text = "مريح للعينين أثناء القراءة الطويلة",
                  style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
              }
              Switch(
                checked = uiState.isDarkTheme,
                onCheckedChange = { viewModel.toggleDarkTheme(it) },
                colors = SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.testTag("dark_mode_switch")
              )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // AMOLED Mode Switch
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text(
                  text = "الوضع الليلي الفائق (AMOLED Pure Black)",
                  style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                )
                Text(
                  text = "خلفية سوداء نقية لتوفير طاقة الشاشة",
                  style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
              }
              Switch(
                checked = uiState.isAmoledMode,
                onCheckedChange = { viewModel.toggleAmoledMode(it) },
                enabled = uiState.isDarkTheme,
                colors = SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.testTag("amoled_mode_switch")
              )
            }
          }
        }
      }

      // 3. Encyclopedia Overview Stats Card
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          border = androidx.compose.foundation.BorderStroke(1.dp, IslamicGold.copy(alpha = 0.3f))
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(imageVector = Icons.Default.Info, contentDescription = null, tint = IslamicGold)
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "إحصائيات الموسوعة الشاملة",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
              )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceEvenly
            ) {
              StatCounterBox(value = "${uiState.filteredFigures.size}+", label = "شخصية تاريخية")
              StatCounterBox(value = "${uiState.filteredBattles.size}+", label = "معركة موثقة")
              StatCounterBox(value = "${uiState.empires.size}", label = "دولة وخلافة")
            }
          }
        }
      }
    }
  }
}

@Composable
fun StatCounterBox(
  value: String,
  label: String,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(14.dp),
    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
    modifier = modifier.padding(4.dp)
  ) {
    Column(
      modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = value,
        style = MaterialTheme.typography.titleLarge.copy(
          fontWeight = FontWeight.ExtraBold,
          color = MaterialTheme.colorScheme.primary
        )
      )
      Text(
        text = label,
        style = MaterialTheme.typography.labelSmall.copy(
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      )
    }
  }
}
