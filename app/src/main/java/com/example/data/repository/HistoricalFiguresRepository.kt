package com.example.data.repository

import com.example.data.model.FigureCategory
import com.example.data.model.HistoricalFigure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class HistoricalFiguresRepository {

  private val allFiguresList: List<HistoricalFigure> =
    FiguresData.figures + FiguresDataPart2.figures + FiguresDataPart3.figures

  private val bookmarkedIds = MutableStateFlow<Set<String>>(
    setOf("khalid_ibn_al_walid", "salah_al_din", "mehmed_the_conqueror", "al_khwarizmi")
  )

  fun getAllFigures(): List<HistoricalFigure> = allFiguresList

  fun getFigureById(id: String): HistoricalFigure? =
    allFiguresList.find { it.id == id }

  fun getFiguresByCategory(category: FigureCategory): List<HistoricalFigure> =
    allFiguresList.filter { it.category == category }

  fun searchFigures(query: String, category: FigureCategory? = null): List<HistoricalFigure> {
    val trimmed = query.trim()
    return allFiguresList.filter { figure ->
      val matchesCategory = category == null || figure.category == category
      val matchesQuery = trimmed.isEmpty() ||
          figure.name.contains(trimmed, ignoreCase = true) ||
          figure.title.contains(trimmed, ignoreCase = true) ||
          figure.region.contains(trimmed, ignoreCase = true) ||
          figure.lineageAndPersonality.contains(trimmed, ignoreCase = true) ||
          figure.majorBattles.any { it.contains(trimmed, ignoreCase = true) }
      matchesCategory && matchesQuery
    }
  }

  fun getBookmarkedIdsFlow(): Flow<Set<String>> = bookmarkedIds.asStateFlow()

  fun toggleBookmark(figureId: String) {
    val current = bookmarkedIds.value.toMutableSet()
    if (current.contains(figureId)) {
      current.remove(figureId)
    } else {
      current.add(figureId)
    }
    bookmarkedIds.value = current
  }

  fun isBookmarked(figureId: String): Boolean =
    bookmarkedIds.value.contains(figureId)
}
