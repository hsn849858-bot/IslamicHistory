package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.HistoricalFigure
import com.example.ui.components.BookmarkButton
import com.example.ui.theme.IslamicGold

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FigureDetailDialog(
  figure: HistoricalFigure,
  isBookmarked: Boolean,
  onToggleBookmark: () -> Unit,
  onDismiss: () -> Unit
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = MaterialTheme.colorScheme.surface,
    shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
    modifier = Modifier.testTag("figure_detail_sheet")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 20.dp)
        .padding(bottom = 32.dp)
    ) {
      // Header Bar
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          BookmarkButton(
            isBookmarked = isBookmarked,
            onToggle = onToggleBookmark
          )
          Spacer(modifier = Modifier.width(8.dp))
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
          ) {
            Text(
              text = figure.category.titleArabic,
              style = MaterialTheme.typography.labelMedium.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
              ),
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            )
          }
        }

        IconButton(
          onClick = onDismiss,
          modifier = Modifier.testTag("close_dialog_button")
        ) {
          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "إغلاق",
            tint = MaterialTheme.colorScheme.onSurface
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Hero Card with Avatar and Title
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, IslamicGold.copy(alpha = 0.4f))
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Box(
            modifier = Modifier
              .size(64.dp)
              .clip(CircleShape)
              .background(
                Brush.radialGradient(
                  colors = listOf(IslamicGold, MaterialTheme.colorScheme.primary)
                )
              ),
            contentAlignment = Alignment.Center
          ) {
            Text(text = figure.roleIcon, fontSize = 32.sp)
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = figure.name,
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.ExtraBold,
              color = MaterialTheme.colorScheme.onSurface,
              textAlign = TextAlign.Center
            )
          )

          Text(
            text = figure.title,
            style = MaterialTheme.typography.bodyMedium.copy(
              color = MaterialTheme.colorScheme.primary,
              fontWeight = FontWeight.Bold,
              textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(top = 2.dp)
          )

          Spacer(modifier = Modifier.height(8.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = MaterialTheme.colorScheme.surface
            ) {
              Text(
                text = "📅 ${figure.birthDeathYears}",
                style = MaterialTheme.typography.labelMedium.copy(
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }
            if (figure.ruleYears != null) {
              Spacer(modifier = Modifier.width(8.dp))
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surface
              ) {
                Text(
                  text = "👑 ${figure.ruleYears}",
                  style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                  ),
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Lineage & Personality Section
      SectionCard(
        title = "النسب والمكانة والنشأة",
        icon = "📜"
      ) {
        Text(
          text = figure.lineageAndPersonality,
          style = MaterialTheme.typography.bodyMedium.copy(
            lineHeight = 22.sp,
            color = MaterialTheme.colorScheme.onSurface
          )
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Major Life Milestones & Sections
      figure.majorSections.forEach { section ->
        SectionCard(
          title = section.title,
          icon = section.icon
        ) {
          Text(
            text = section.content,
            style = MaterialTheme.typography.bodyMedium.copy(
              lineHeight = 22.sp,
              color = MaterialTheme.colorScheme.onSurface
            )
          )
        }
        Spacer(modifier = Modifier.height(12.dp))
      }

      // Major Battles if present
      if (figure.majorBattles.isNotEmpty()) {
        SectionCard(
          title = "أبرز المعارك والفتوحات",
          icon = "⚔️"
        ) {
          FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            figure.majorBattles.forEach { battle ->
              Surface(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(text = "🛡️", fontSize = 14.sp, modifier = Modifier.padding(end = 4.dp))
                  Text(
                    text = battle,
                    style = MaterialTheme.typography.labelMedium.copy(
                      fontWeight = FontWeight.SemiBold,
                      color = MaterialTheme.colorScheme.primary
                    )
                  )
                }
              }
            }
          }
        }
        Spacer(modifier = Modifier.height(12.dp))
      }

      // Famous Quotes
      if (figure.quotes.isNotEmpty()) {
        SectionCard(
          title = "أقوال ووصايا خالدة",
          icon = "💬"
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            figure.quotes.forEach { quote ->
              Surface(
                shape = RoundedCornerShape(12.dp),
                color = IslamicGold.copy(alpha = 0.08f),
                border = androidx.compose.foundation.BorderStroke(1.dp, IslamicGold.copy(alpha = 0.25f)),
                modifier = Modifier.fillMaxWidth()
              ) {
                Row(modifier = Modifier.padding(12.dp)) {
                  Icon(
                    imageVector = Icons.Default.FormatQuote,
                    contentDescription = null,
                    tint = IslamicGold,
                    modifier = Modifier
                      .size(20.dp)
                      .padding(end = 4.dp)
                  )
                  Text(
                    text = "\"$quote\"",
                    style = MaterialTheme.typography.bodyMedium.copy(
                      fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                      color = MaterialTheme.colorScheme.onSurface,
                      lineHeight = 20.sp
                    )
                  )
                }
              }
            }
          }
        }
        Spacer(modifier = Modifier.height(12.dp))
      }

      // Martyrdom or Death
      SectionCard(
        title = "الوفاة أو الاستشهاد",
        icon = "🕊️"
      ) {
        Text(
          text = figure.martyrdomOrDeath,
          style = MaterialTheme.typography.bodyMedium.copy(
            lineHeight = 22.sp,
            color = MaterialTheme.colorScheme.onSurface
          )
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Historical Impact
      SectionCard(
        title = "الأثر التاريخي والحضاري الخالد",
        icon = "🌟"
      ) {
        Text(
          text = figure.historicalImpact,
          style = MaterialTheme.typography.bodyMedium.copy(
            lineHeight = 22.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
          )
        )
      }
    }
  }
}

@Composable
fun SectionCard(
  title: String,
  icon: String,
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit
) {
  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    border = androidx.compose.foundation.BorderStroke(
      1.dp,
      MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
    )
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp)
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 8.dp)
      ) {
        Text(text = icon, fontSize = 18.sp, modifier = Modifier.padding(end = 6.dp))
        Text(
          text = title,
          style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
          )
        )
      }
      content()
    }
  }
}
