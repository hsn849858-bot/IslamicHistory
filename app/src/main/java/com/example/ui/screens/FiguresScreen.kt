package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.FigureCategory
import com.example.data.model.HistoricalFigure
import com.example.ui.AppUiState
import com.example.ui.HistoryViewModel
import com.example.ui.components.BookmarkButton
import com.example.ui.components.IslamicHeader
import com.example.ui.components.SearchAndFilterBar
import com.example.ui.theme.IslamicGold

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FiguresScreen(
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
      title = "أعلام وقادة التاريخ الإسلامي",
      subtitle = "سير الأبطال والخلفاء والعلماء وقادة الفتوحات الكبرى",
      badgeText = "${uiState.filteredFigures.size} شخصية تاريخية"
    )

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
      // Search Bar
      SearchAndFilterBar(
        query = uiState.searchQuery,
        onQueryChange = { viewModel.setSearchQuery(it) },
        placeholder = "ابحث بالاسم، اللقب، أو المعارك..."
      )

      Spacer(modifier = Modifier.height(8.dp))

      // Category Chips
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 4.dp)
      ) {
        items(FigureCategory.values()) { category ->
          val isSelected = uiState.selectedCategory == category
          FilterChip(
            selected = isSelected,
            onClick = { viewModel.setCategory(category) },
            label = {
              Text(
                text = "${category.badgeIcon} ${category.titleArabic}",
                style = MaterialTheme.typography.labelMedium.copy(
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
              )
            },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = MaterialTheme.colorScheme.primary,
              selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
              containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.testTag("category_chip_${category.name}")
          )
        }
      }

      // Bookmarks Filter Toggle
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = if (uiState.selectedCategory == FigureCategory.ALL) "جميع الشخصيات" else uiState.selectedCategory.titleArabic,
          style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
        )

        Surface(
          shape = RoundedCornerShape(20.dp),
          color = if (uiState.onlyBookmarks) IslamicGold.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
          border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (uiState.onlyBookmarks) IslamicGold else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
          ),
          modifier = Modifier
            .clickable { viewModel.toggleOnlyBookmarks(!uiState.onlyBookmarks) }
            .testTag("toggle_bookmarks_filter")
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.Bookmark,
              contentDescription = null,
              tint = if (uiState.onlyBookmarks) IslamicGold else MaterialTheme.colorScheme.onSurfaceVariant,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "المفضلة (${uiState.bookmarkedFigureIds.size})",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = if (uiState.onlyBookmarks) IslamicGold else MaterialTheme.colorScheme.onSurfaceVariant
              )
            )
          }
        }
      }
    }

    // Figures List
    if (uiState.filteredFigures.isEmpty()) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text(text = "🔍", fontSize = 48.sp)
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "لم يتم العثور على نتائج مطابقة",
            style = MaterialTheme.typography.bodyLarge.copy(
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          )
        }
      }
    } else {
      LazyColumn(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
          .testTag("figures_lazy_list"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        items(
          items = uiState.filteredFigures,
          key = { it.id }
        ) { figure ->
          val isBookmarked = uiState.bookmarkedFigureIds.contains(figure.id)
          FigureCardItem(
            figure = figure,
            isBookmarked = isBookmarked,
            onToggleBookmark = { viewModel.toggleBookmark(figure.id) },
            onClick = { viewModel.selectFigure(figure) }
          )
        }
      }
    }
  }

  // Detail Modal Bottom Sheet
  if (uiState.selectedFigure != null) {
    val isBookmarked = uiState.bookmarkedFigureIds.contains(uiState.selectedFigure.id)
    FigureDetailDialog(
      figure = uiState.selectedFigure,
      isBookmarked = isBookmarked,
      onToggleBookmark = { viewModel.toggleBookmark(uiState.selectedFigure.id) },
      onDismiss = { viewModel.selectFigure(null) }
    )
  }
}

@Composable
fun FigureCardItem(
  figure: HistoricalFigure,
  isBookmarked: Boolean,
  onToggleBookmark: () -> Unit,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .clickable(onClick = onClick)
      .testTag("figure_card_${figure.id}"),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    border = androidx.compose.foundation.BorderStroke(
      1.dp,
      if (isBookmarked) IslamicGold.copy(alpha = 0.5f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Role Icon Avatar
      Box(
        modifier = Modifier
          .size(52.dp)
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
        Text(text = figure.roleIcon, fontSize = 26.sp)
      }

      Spacer(modifier = Modifier.width(12.dp))

      // Main Information
      Column(modifier = Modifier.weight(1f)) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            text = figure.name,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
        }

        Text(
          text = figure.title,
          style = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
          ),
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
          ) {
            Text(
              text = "📅 ${figure.birthDeathYears}",
              style = MaterialTheme.typography.labelSmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
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
              text = figure.region,
              style = MaterialTheme.typography.labelSmall.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
              ),
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.width(8.dp))

      // Bookmark and Arrow
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        BookmarkButton(
          isBookmarked = isBookmarked,
          onToggle = onToggleBookmark
        )
        Icon(
          imageVector = Icons.Default.ChevronLeft,
          contentDescription = "عرض التفاصيل",
          tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
          modifier = Modifier
            .size(22.dp)
            .padding(top = 4.dp)
        )
      }
    }
  }
}
