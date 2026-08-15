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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.IslamicEmpire
import com.example.ui.theme.IslamicGold

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun DynastyDetailDialog(
  empire: IslamicEmpire,
  onDismiss: () -> Unit
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = MaterialTheme.colorScheme.surface,
    shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
    modifier = Modifier.testTag("dynasty_detail_sheet")
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
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = Color(empire.flagColorHex).copy(alpha = 0.2f),
          border = androidx.compose.foundation.BorderStroke(1.dp, IslamicGold.copy(alpha = 0.5f))
        ) {
          Text(
            text = empire.durationYears,
            style = MaterialTheme.typography.labelMedium.copy(
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
          )
        }

        IconButton(
          onClick = onDismiss,
          modifier = Modifier.testTag("close_dynasty_dialog")
        ) {
          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "إغلاق",
            tint = MaterialTheme.colorScheme.onSurface
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Hero Banner
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
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
              .size(60.dp)
              .clip(CircleShape)
              .background(Color(empire.flagColorHex).copy(alpha = 0.3f))
              .border(1.dp, IslamicGold, CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Text(text = empire.emblemSymbol, fontSize = 28.sp)
          }

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = empire.name,
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.ExtraBold,
              color = MaterialTheme.colorScheme.onSurface,
              textAlign = TextAlign.Center
            )
          )

          Text(
            text = empire.arabicTitle,
            style = MaterialTheme.typography.bodyMedium.copy(
              color = MaterialTheme.colorScheme.primary,
              fontWeight = FontWeight.SemiBold,
              textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(top = 2.dp)
          )

          Spacer(modifier = Modifier.height(10.dp))

          // Key Stats Badges
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
                text = "🌍 أقصى اتساع: ${empire.peakAreaSqKm}",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Capitals & Territorial Span
      SectionCard(
        title = "العواصم والامتداد الجغرافي",
        icon = "🏛️"
      ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
          Text(
            text = "📍 العواصم: ${empire.capitalCities.joinToString(" ← ")}",
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = FontWeight.SemiBold,
              color = MaterialTheme.colorScheme.primary
            )
          )
          Text(
            text = "🗺️ الامتداد: ${empire.territorialSpan}",
            style = MaterialTheme.typography.bodyMedium.copy(
              lineHeight = 22.sp,
              color = MaterialTheme.colorScheme.onSurface
            )
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Golden Age
      SectionCard(
        title = "العصر الذهبي وأوج الازدهار",
        icon = "✨"
      ) {
        Surface(
          shape = RoundedCornerShape(10.dp),
          color = IslamicGold.copy(alpha = 0.1f),
          border = androidx.compose.foundation.BorderStroke(1.dp, IslamicGold.copy(alpha = 0.35f)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            text = empire.goldenAgeEra,
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = FontWeight.SemiBold,
              color = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier.padding(12.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Prominent Rulers
      SectionCard(
        title = "أبرز الحكام والخلفاء",
        icon = "👑"
      ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
          empire.prominentRulers.forEach { ruler ->
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(text = "⚜️", fontSize = 14.sp, modifier = Modifier.padding(end = 6.dp))
                Text(
                  text = ruler,
                  style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                  )
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Major Conquests & Key Commanders
      SectionCard(
        title = "أبرز الفتوحات والحروب والقادة",
        icon = "⚔️"
      ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Text(
            text = "أبرز المعارك والفتوحات:",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
          )
          FlowRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            empire.majorConquestsAndWars.forEach { war ->
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
              ) {
                Text(
                  text = war,
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                  ),
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = "أبرز القادة العسكريين:",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
          )
          FlowRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            empire.keyCommanders.forEach { commander ->
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = IslamicGold.copy(alpha = 0.12f)
              ) {
                Text(
                  text = "🗡️ $commander",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                  ),
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Civilizational Achievements
      SectionCard(
        title = "المآثر والمنجزات الحضارية والعلمية",
        icon = "📚"
      ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
          empire.civilizationalAchievements.forEach { achievement ->
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.Top
            ) {
              Text(
                text = "🔹",
                fontSize = 14.sp,
                modifier = Modifier.padding(end = 6.dp, top = 2.dp)
              )
              Text(
                text = achievement,
                style = MaterialTheme.typography.bodyMedium.copy(
                  lineHeight = 22.sp,
                  color = MaterialTheme.colorScheme.onSurface
                )
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Fall Factors & Context
      SectionCard(
        title = "عوامل التراجع وسقوط الدولة والدروس المستفادة",
        icon = "⏳"
      ) {
        Text(
          text = empire.fallFactorsAndContext,
          style = MaterialTheme.typography.bodyMedium.copy(
            lineHeight = 22.sp,
            color = MaterialTheme.colorScheme.onSurface
          )
        )
      }
    }
  }
}
