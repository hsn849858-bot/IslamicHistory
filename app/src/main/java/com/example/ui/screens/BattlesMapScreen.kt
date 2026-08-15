package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Battle
import com.example.data.model.BattleRegion
import com.example.ui.AppUiState
import com.example.ui.HistoryViewModel
import com.example.ui.components.IslamicHeader
import com.example.ui.theme.BattleRed
import com.example.ui.theme.IslamicGold
import com.example.ui.theme.VictoryGreen

@Composable
fun BattlesMapScreen(
  uiState: AppUiState,
  viewModel: HistoryViewModel,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableStateOf(0) } // 0: الخريطة التفاعلية, 1: قائمة المعارك
  var hoveredBattle by remember { mutableStateOf<Battle?>(null) }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
  ) {
    IslamicHeader(
      title = "خريطة المعارك والفتوحات الإسلامية",
      subtitle = "استكشف جغرافيا الفتوحات الكبرى من الأندلس غرباً إلى الصين شرقاً",
      badgeText = "${uiState.filteredBattles.size} معركة تاريخية موثقة"
    )

    // View Mode Tabs (خريطة العالم / القائمة)
    TabRow(
      selectedTabIndex = selectedTab,
      containerColor = MaterialTheme.colorScheme.surface,
      contentColor = MaterialTheme.colorScheme.primary,
      modifier = Modifier.fillMaxWidth()
    ) {
      Tab(
        selected = selectedTab == 0,
        onClick = { selectedTab = 0 },
        text = {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Map, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("الخريطة التفاعلية", fontWeight = FontWeight.Bold)
          }
        },
        modifier = Modifier.testTag("map_view_tab")
      )
      Tab(
        selected = selectedTab == 1,
        onClick = { selectedTab = 1 },
        text = {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.List, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("سجل المعارك المفصل", fontWeight = FontWeight.Bold)
          }
        },
        modifier = Modifier.testTag("list_view_tab")
      )
    }

    // Region Filter Chips
    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
      items(BattleRegion.values()) { region ->
        val isSelected = uiState.selectedBattleRegion == region
        FilterChip(
          selected = isSelected,
          onClick = { viewModel.setBattleRegion(region) },
          label = {
            Text(
              text = region.titleArabic,
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
              )
            )
          },
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
          ),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.testTag("region_chip_${region.name}")
        )
      }
    }

    if (selectedTab == 0) {
      // Interactive Canvas World Map
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
          .padding(horizontal = 12.dp, vertical = 4.dp)
      ) {
        Column(modifier = Modifier.fillMaxSize()) {
          // Map Canvas Box
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .weight(1f),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
              containerColor = Color(0xFF0F171E)
            ),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, IslamicGold.copy(alpha = 0.5f))
          ) {
            Box(modifier = Modifier.fillMaxSize()) {
              IslamicWorldCanvasMap(
                battles = uiState.filteredBattles,
                selectedBattle = uiState.selectedBattle ?: hoveredBattle,
                onBattleClick = { battle ->
                  hoveredBattle = battle
                  viewModel.selectBattle(battle)
                },
                modifier = Modifier.fillMaxSize()
              )

              // Map Legend
              Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xCC0A1015),
                border = androidx.compose.foundation.BorderStroke(1.dp, IslamicGold.copy(alpha = 0.3f)),
                modifier = Modifier
                  .align(Alignment.TopStart)
                  .padding(10.dp)
              ) {
                Column(modifier = Modifier.padding(8.dp)) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(IslamicGold))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                      text = "مواقع المعارك (انقر للمعاينة)",
                      style = MaterialTheme.typography.labelSmall.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                      )
                    )
                  }
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          // Quick Selected Battle Preview Card
          val activeBattle = uiState.selectedBattle ?: hoveredBattle ?: uiState.filteredBattles.firstOrNull()
          if (activeBattle != null) {
            Card(
              modifier = Modifier
                .fillMaxWidth()
                .clickable { viewModel.selectBattle(activeBattle) }
                .testTag("map_quick_battle_card"),
              shape = RoundedCornerShape(16.dp),
              colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
              ),
              border = androidx.compose.foundation.BorderStroke(1.dp, IslamicGold.copy(alpha = 0.4f))
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Box(
                  modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(VictoryGreen.copy(alpha = 0.15f))
                    .border(1.dp, VictoryGreen, CircleShape),
                  contentAlignment = Alignment.Center
                ) {
                  Text(text = "⚔️", fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = activeBattle.name,
                    style = MaterialTheme.typography.titleSmall.copy(
                      fontWeight = FontWeight.Bold,
                      color = MaterialTheme.colorScheme.onSurface
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                  )
                  Text(
                    text = "القائد: ${activeBattle.leaderName} • ${activeBattle.yearGregorian}م (${activeBattle.yearHijri}هـ)",
                    style = MaterialTheme.typography.bodySmall.copy(
                      color = MaterialTheme.colorScheme.primary,
                      fontWeight = FontWeight.Medium
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                  )
                }

                Surface(
                  shape = RoundedCornerShape(10.dp),
                  color = MaterialTheme.colorScheme.primary
                ) {
                  Text(
                    text = "التفاصيل",
                    style = MaterialTheme.typography.labelSmall.copy(
                      color = MaterialTheme.colorScheme.onPrimary,
                      fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                  )
                }
              }
            }
          }
        }
      }
    } else {
      // Battles List View
      LazyColumn(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
          .testTag("battles_lazy_list"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        items(
          items = uiState.filteredBattles,
          key = { it.id }
        ) { battle ->
          BattleListItemCard(
            battle = battle,
            onClick = { viewModel.selectBattle(battle) }
          )
        }
      }
    }
  }

  // Battle Detail Sheet
  if (uiState.selectedBattle != null) {
    BattleDetailDialog(
      battle = uiState.selectedBattle,
      onDismiss = { viewModel.selectBattle(null) }
    )
  }
}

@Composable
fun BattleListItemCard(
  battle: Battle,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .clickable(onClick = onClick)
      .testTag("battle_item_${battle.id}"),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    border = androidx.compose.foundation.BorderStroke(
      1.dp,
      MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(50.dp)
          .clip(CircleShape)
          .background(
            Brush.radialGradient(
              colors = listOf(
                MaterialTheme.colorScheme.primaryContainer,
                MaterialTheme.colorScheme.surfaceVariant
              )
            )
          )
          .border(1.dp, IslamicGold.copy(alpha = 0.4f), CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Text(text = "⚔️", fontSize = 24.sp)
      }

      Spacer(modifier = Modifier.width(12.dp))

      Column(modifier = Modifier.weight(1f)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = battle.name,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
        }

        Text(
          text = "بقيادة: ${battle.leaderName}",
          style = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
          )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
          ) {
            Text(
              text = "📅 ${battle.yearGregorian}م (${battle.yearHijri}هـ)",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
              ),
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }

          Spacer(modifier = Modifier.width(6.dp))

          Surface(
            shape = RoundedCornerShape(6.dp),
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
          ) {
            Text(
              text = battle.region.titleArabic,
              style = MaterialTheme.typography.labelSmall.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
              ),
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
        }
      }

      Icon(
        imageVector = Icons.Default.ChevronLeft,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
        modifier = Modifier.size(24.dp)
      )
    }
  }
}

/**
 * Custom Canvas Islamic World Map
 * Coordinates transform:
 * Longitude: -10° (Andalus/Atlantic) to 75° (Sindh/Central Asia) -> mapped to X
 * Latitude: 50°N (France/Europe) to 15°N (Yemen/Arabian Sea) -> mapped to Y
 */
@Composable
fun IslamicWorldCanvasMap(
  battles: List<Battle>,
  selectedBattle: Battle?,
  onBattleClick: (Battle) -> Unit,
  modifier: Modifier = Modifier
) {
  val minLon = -12.0
  val maxLon = 76.0
  val minLat = 18.0
  val maxLat = 50.0

  Box(modifier = modifier) {
    Canvas(
      modifier = Modifier
        .fillMaxSize()
        .pointerInput(battles) {
          detectTapGestures { offset ->
            val w = size.width
            val h = size.height

            // Find nearest battle marker to tap
            var closestBattle: Battle? = null
            var minDistance = Float.MAX_VALUE

            for (battle in battles) {
              val normX = ((battle.longitude - minLon) / (maxLon - minLon)).toFloat().coerceIn(0f, 1f)
              val normY = (1f - ((battle.latitude - minLat) / (maxLat - minLat)).toFloat()).coerceIn(0f, 1f)

              val markerX = normX * w
              val markerY = normY * h

              val dx = offset.x - markerX
              val dy = offset.y - markerY
              val dist = kotlin.math.sqrt(dx * dx + dy * dy)

              if (dist < 40.dp.toPx() && dist < minDistance) {
                minDistance = dist
                closestBattle = battle
              }
            }

            if (closestBattle != null) {
              onBattleClick(closestBattle)
            }
          }
        }
    ) {
      val w = size.width
      val h = size.height

      // 1. Draw Sea Background (Dark Slate Blue)
      drawRect(
        color = Color(0xFF0C1923),
        topLeft = Offset.Zero,
        size = size
      )

      // 2. Draw Subtle Latitude / Longitude Grid lines
      val gridLinesX = 6
      for (i in 1..gridLinesX) {
        val x = w * (i.toFloat() / (gridLinesX + 1))
        drawLine(
          color = Color(0x15FFFFFF),
          start = Offset(x, 0f),
          end = Offset(x, h),
          strokeWidth = 1f
        )
      }
      val gridLinesY = 5
      for (i in 1..gridLinesY) {
        val y = h * (i.toFloat() / (gridLinesY + 1))
        drawLine(
          color = Color(0x15FFFFFF),
          start = Offset(0f, y),
          end = Offset(w, y),
          strokeWidth = 1f
        )
      }

      // 3. Draw Stylized Landmasses (Iberia, North Africa, Arabia, Levant, Anatolia, Persia, Central Asia)
      val landColor = Color(0xFF1B2832)
      val coastColor = IslamicGold.copy(alpha = 0.35f)

      fun geoToScreen(lon: Double, lat: Double): Offset {
        val normX = ((lon - minLon) / (maxLon - minLon)).toFloat().coerceIn(0f, 1f)
        val normY = (1f - ((lat - minLat) / (maxLat - minLat)).toFloat()).coerceIn(0f, 1f)
        return Offset(normX * w, normY * h)
      }

      // Iberian Peninsula (الأندلس)
      val iberiaPath = Path().apply {
        val p1 = geoToScreen(-9.0, 43.0)
        moveTo(p1.x, p1.y)
        val p2 = geoToScreen(-1.0, 43.5)
        lineTo(p2.x, p2.y)
        val p3 = geoToScreen(3.0, 42.0)
        lineTo(p3.x, p3.y)
        val p4 = geoToScreen(0.0, 38.0)
        lineTo(p4.x, p4.y)
        val p5 = geoToScreen(-5.5, 36.0)
        lineTo(p5.x, p5.y)
        val p6 = geoToScreen(-9.0, 37.0)
        lineTo(p6.x, p6.y)
        close()
      }
      drawPath(iberiaPath, color = landColor)
      drawPath(iberiaPath, color = coastColor, style = Stroke(width = 1.5f))

      // North Africa (المغرب العربي ومصر)
      val northAfricaPath = Path().apply {
        val p1 = geoToScreen(-10.0, 32.0)
        moveTo(p1.x, p1.y)
        val p2 = geoToScreen(-5.0, 35.8)
        lineTo(p2.x, p2.y)
        val p3 = geoToScreen(10.0, 37.0)
        lineTo(p3.x, p3.y)
        val p4 = geoToScreen(15.0, 31.0)
        lineTo(p4.x, p4.y)
        val p5 = geoToScreen(25.0, 32.0)
        lineTo(p5.x, p5.y)
        val p6 = geoToScreen(32.0, 31.5)
        lineTo(p6.x, p6.y)
        val p7 = geoToScreen(34.0, 27.5)
        lineTo(p7.x, p7.y)
        val p8 = geoToScreen(35.0, 22.0)
        lineTo(p8.x, p8.y)
        val p9 = geoToScreen(-10.0, 20.0)
        lineTo(p9.x, p9.y)
        close()
      }
      drawPath(northAfricaPath, color = landColor)
      drawPath(northAfricaPath, color = coastColor, style = Stroke(width = 1.5f))

      // Arabian Peninsula (الجزيرة العربية)
      val arabiaPath = Path().apply {
        val p1 = geoToScreen(35.0, 28.5)
        moveTo(p1.x, p1.y)
        val p2 = geoToScreen(42.0, 18.0)
        lineTo(p2.x, p2.y)
        val p3 = geoToScreen(54.0, 18.0)
        lineTo(p3.x, p3.y)
        val p4 = geoToScreen(59.0, 23.0)
        lineTo(p4.x, p4.y)
        val p5 = geoToScreen(55.0, 26.0)
        lineTo(p5.x, p5.y)
        val p6 = geoToScreen(48.0, 30.0)
        lineTo(p6.x, p6.y)
        val p7 = geoToScreen(38.0, 32.0)
        lineTo(p7.x, p7.y)
        close()
      }
      drawPath(arabiaPath, color = landColor)
      drawPath(arabiaPath, color = coastColor, style = Stroke(width = 1.5f))

      // Anatolia & Balkans (الأناضول والبلقان)
      val anatoliaPath = Path().apply {
        val p1 = geoToScreen(18.0, 46.0)
        moveTo(p1.x, p1.y)
        val p2 = geoToScreen(28.0, 41.5)
        lineTo(p2.x, p2.y)
        val p3 = geoToScreen(40.0, 41.5)
        lineTo(p3.x, p3.y)
        val p4 = geoToScreen(43.0, 38.0)
        lineTo(p4.x, p4.y)
        val p5 = geoToScreen(33.0, 36.5)
        lineTo(p5.x, p5.y)
        val p6 = geoToScreen(27.0, 37.0)
        lineTo(p6.x, p6.y)
        val p7 = geoToScreen(22.0, 40.0)
        lineTo(p7.x, p7.y)
        close()
      }
      drawPath(anatoliaPath, color = landColor)
      drawPath(anatoliaPath, color = coastColor, style = Stroke(width = 1.5f))

      // Levant, Iraq, Persia, Central Asia & India (المشرق الإسلامي)
      val mashriqPath = Path().apply {
        val p1 = geoToScreen(35.0, 36.0)
        moveTo(p1.x, p1.y)
        val p2 = geoToScreen(48.0, 38.0)
        lineTo(p2.x, p2.y)
        val p3 = geoToScreen(60.0, 45.0)
        lineTo(p3.x, p3.y)
        val p4 = geoToScreen(75.0, 44.0)
        lineTo(p4.x, p4.y)
        val p5 = geoToScreen(74.0, 24.0)
        lineTo(p5.x, p5.y)
        val p6 = geoToScreen(62.0, 25.0)
        lineTo(p6.x, p6.y)
        val p7 = geoToScreen(50.0, 30.0)
        lineTo(p7.x, p7.y)
        val p8 = geoToScreen(35.0, 31.0)
        lineTo(p8.x, p8.y)
        close()
      }
      drawPath(mashriqPath, color = landColor)
      drawPath(mashriqPath, color = coastColor, style = Stroke(width = 1.5f))

      // 4. Draw Battle Markers
      for (battle in battles) {
        val normX = ((battle.longitude - minLon) / (maxLon - minLon)).toFloat().coerceIn(0f, 1f)
        val normY = (1f - ((battle.latitude - minLat) / (maxLat - minLat)).toFloat()).coerceIn(0f, 1f)

        val posX = normX * w
        val posY = normY * h

        val isSelected = selectedBattle?.id == battle.id

        // Glow circle
        if (isSelected) {
          drawCircle(
            color = IslamicGold.copy(alpha = 0.35f),
            radius = 22.dp.toPx(),
            center = Offset(posX, posY)
          )
          drawCircle(
            color = IslamicGold.copy(alpha = 0.7f),
            radius = 14.dp.toPx(),
            center = Offset(posX, posY)
          )
        }

        // Inner solid pin marker
        drawCircle(
          color = if (isSelected) Color.White else IslamicGold,
          radius = if (isSelected) 8.dp.toPx() else 6.dp.toPx(),
          center = Offset(posX, posY)
        )
        drawCircle(
          color = if (isSelected) VictoryGreen else BattleRed,
          radius = if (isSelected) 5.dp.toPx() else 3.5.dp.toPx(),
          center = Offset(posX, posY)
        )
      }
    }
  }
}
