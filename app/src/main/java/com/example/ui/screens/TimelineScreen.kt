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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.HistoricalMilestone
import com.example.data.repository.QuizAndQuotesData
import com.example.ui.AppScreen
import com.example.ui.AppUiState
import com.example.ui.HistoryViewModel
import com.example.ui.components.IslamicHeader
import com.example.ui.theme.IslamicGold
import com.example.ui.theme.VictoryGreen

@Composable
fun TimelineScreen(
  uiState: AppUiState,
  viewModel: HistoryViewModel,
  modifier: Modifier = Modifier
) {
  val milestones = QuizAndQuotesData.milestones

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
  ) {
    IslamicHeader(
      title = "الخط الزمني للحضارة الإسلامية",
      subtitle = "أبرز المحطات الفارقة والتحولات الكبرى من الهجرة النبوية عبر القرون",
      badgeText = "${milestones.size} محطة تاريخية حاسمة"
    )

    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
        .testTag("timeline_lazy_list"),
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
    ) {
      itemsIndexed(milestones) { index, milestone ->
        val isLast = index == milestones.size - 1
        TimelineMilestoneItem(
          milestone = milestone,
          isLast = isLast,
          onClick = {
            if (milestone.figureOrBattleId != null) {
              // Try finding as battle or figure
              val battle = uiState.filteredBattles.find { it.id == milestone.figureOrBattleId }
              if (battle != null) {
                viewModel.setScreen(AppScreen.BATTLES_MAP)
                viewModel.selectBattle(battle)
              } else {
                val figure = uiState.filteredFigures.find { it.id == milestone.figureOrBattleId }
                if (figure != null) {
                  viewModel.setScreen(AppScreen.FIGURES)
                  viewModel.selectFigure(figure)
                }
              }
            }
          }
        )
      }
    }
  }
}

@Composable
fun TimelineMilestoneItem(
  milestone: HistoricalMilestone,
  isLast: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .testTag("timeline_item_${milestone.yearGregorian}"),
    verticalAlignment = Alignment.Top
  ) {
    // Left Timeline Vertical Line and Node
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.width(36.dp)
    ) {
      // Node
      Box(
        modifier = Modifier
          .size(24.dp)
          .clip(CircleShape)
          .background(IslamicGold)
          .border(2.dp, MaterialTheme.colorScheme.surface, CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Box(
          modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surface)
        )
      }

      if (!isLast) {
        // Vertical Connector
        Box(
          modifier = Modifier
            .width(2.dp)
            .height(110.dp)
            .background(
              Brush.verticalGradient(
                colors = listOf(IslamicGold, IslamicGold.copy(alpha = 0.2f))
              )
            )
        )
      }
    }

    Spacer(modifier = Modifier.width(10.dp))

    // Milestone Card
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 12.dp)
        .clickable(onClick = onClick),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surface
      ),
      border = androidx.compose.foundation.BorderStroke(
        1.dp,
        MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
      ),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(14.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
          ) {
            Text(
              text = "📅 ${milestone.yearGregorian}م / ${milestone.yearHijri}هـ",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
              ),
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
            )
          }

          Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
          ) {
            Text(
              text = milestone.category,
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              ),
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = milestone.title,
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = milestone.summary,
          style = MaterialTheme.typography.bodySmall.copy(
            lineHeight = 20.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        )
      }
    }
  }
}
