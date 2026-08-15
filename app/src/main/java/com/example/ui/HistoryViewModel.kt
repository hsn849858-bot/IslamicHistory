package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.AppThemePalette
import com.example.data.model.Battle
import com.example.data.model.BattleRegion
import com.example.data.model.FigureCategory
import com.example.data.model.HistoricalFigure
import com.example.data.model.IslamicEmpire
import com.example.data.model.QuizQuestion
import com.example.data.repository.BattlesData
import com.example.data.repository.DynastiesData
import com.example.data.repository.HistoricalFiguresRepository
import com.example.data.repository.QuizAndQuotesData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

enum class AppScreen(val titleArabic: String, val iconName: String) {
  FIGURES("الشخصيات", "person"),
  BATTLES_MAP("خريطة المعارك", "map"),
  DYNASTIES("الدول والخلافات", "account_balance"),
  TIMELINE("الخط الزمني", "timeline"),
  QUIZ("الاختبار والأقوال", "quiz"),
  SETTINGS("المظهر والإعدادات", "settings")
}

data class QuizState(
  val currentQuestionIndex: Int = 0,
  val selectedOptionIndex: Int? = null,
  val isAnswerSubmitted: Boolean = false,
  val score: Int = 0,
  val isQuizCompleted: Boolean = false,
  val answeredQuestionsCount: Int = 0
)

data class AppUiState(
  val currentScreen: AppScreen = AppScreen.FIGURES,
  val searchQuery: String = "",
  val selectedCategory: FigureCategory = FigureCategory.ALL,
  val filteredFigures: List<HistoricalFigure> = emptyList(),
  val selectedFigure: HistoricalFigure? = null,
  val bookmarkedFigureIds: Set<String> = emptySet(),
  val onlyBookmarks: Boolean = false,
  
  // Battles & Map state
  val selectedBattleRegion: BattleRegion = BattleRegion.ALL,
  val filteredBattles: List<Battle> = emptyList(),
  val selectedBattle: Battle? = null,
  val mapZoomLevel: Float = 1.0f,
  
  // Dynasties
  val empires: List<IslamicEmpire> = emptyList(),
  val selectedEmpire: IslamicEmpire? = null,
  
  // Quiz
  val quizQuestions: List<QuizQuestion> = emptyList(),
  val quizState: QuizState = QuizState(),
  
  // Settings & Theme
  val themePalette: AppThemePalette = AppThemePalette.EMERALD_GOLD,
  val isDarkTheme: Boolean = true,
  val isAmoledMode: Boolean = false,
  val fontSizeMultiplier: Float = 1.0f
)

class HistoryViewModel(
  private val figuresRepository: HistoricalFiguresRepository = HistoricalFiguresRepository()
) : ViewModel() {

  private val _uiState = MutableStateFlow(
    AppUiState(
      filteredFigures = figuresRepository.getAllFigures(),
      filteredBattles = BattlesData.battles,
      empires = DynastiesData.empires,
      quizQuestions = QuizAndQuotesData.quizQuestions
    )
  )
  val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

  init {
    figuresRepository.getBookmarkedIdsFlow().onEach { bookmarks ->
      _uiState.value = _uiState.value.copy(bookmarkedFigureIds = bookmarks)
      applyFiguresFilter()
    }.launchIn(viewModelScope)
  }

  fun setScreen(screen: AppScreen) {
    _uiState.value = _uiState.value.copy(currentScreen = screen)
  }

  fun setSearchQuery(query: String) {
    _uiState.value = _uiState.value.copy(searchQuery = query)
    applyFiguresFilter()
  }

  fun setCategory(category: FigureCategory) {
    _uiState.value = _uiState.value.copy(selectedCategory = category)
    applyFiguresFilter()
  }

  fun toggleOnlyBookmarks(only: Boolean) {
    _uiState.value = _uiState.value.copy(onlyBookmarks = only)
    applyFiguresFilter()
  }

  fun selectFigure(figure: HistoricalFigure?) {
    _uiState.value = _uiState.value.copy(selectedFigure = figure)
  }

  fun toggleBookmark(figureId: String) {
    figuresRepository.toggleBookmark(figureId)
  }

  private fun applyFiguresFilter() {
    val currentQuery = _uiState.value.searchQuery
    val currentCategory = _uiState.value.selectedCategory
    val onlyBookmarked = _uiState.value.onlyBookmarks
    val bookmarks = _uiState.value.bookmarkedFigureIds

    val catFilter = if (currentCategory == FigureCategory.ALL) null else currentCategory
    var figures = figuresRepository.searchFigures(currentQuery, catFilter)
    if (onlyBookmarked) {
      figures = figures.filter { bookmarks.contains(it.id) }
    }
    _uiState.value = _uiState.value.copy(filteredFigures = figures)
  }

  // Battle map methods
  fun setBattleRegion(region: BattleRegion) {
    _uiState.value = _uiState.value.copy(
      selectedBattleRegion = region,
      filteredBattles = if (region == BattleRegion.ALL) BattlesData.battles else BattlesData.battles.filter { it.region == region }
    )
  }

  fun selectBattle(battle: Battle?) {
    _uiState.value = _uiState.value.copy(selectedBattle = battle)
  }

  // Dynasties methods
  fun selectEmpire(empire: IslamicEmpire?) {
    _uiState.value = _uiState.value.copy(selectedEmpire = empire)
  }

  // Quiz methods
  fun selectQuizOption(index: Int) {
    if (_uiState.value.quizState.isAnswerSubmitted) return
    _uiState.value = _uiState.value.copy(
      quizState = _uiState.value.quizState.copy(selectedOptionIndex = index)
    )
  }

  fun submitQuizAnswer() {
    val qState = _uiState.value.quizState
    if (qState.selectedOptionIndex == null || qState.isAnswerSubmitted) return
    
    val currentQ = _uiState.value.quizQuestions[qState.currentQuestionIndex]
    val isCorrect = qState.selectedOptionIndex == currentQ.correctOptionIndex
    val newScore = if (isCorrect) qState.score + 1 else qState.score
    
    _uiState.value = _uiState.value.copy(
      quizState = qState.copy(
        isAnswerSubmitted = true,
        score = newScore,
        answeredQuestionsCount = qState.answeredQuestionsCount + 1
      )
    )
  }

  fun nextQuizQuestion() {
    val qState = _uiState.value.quizState
    val nextIndex = qState.currentQuestionIndex + 1
    if (nextIndex < _uiState.value.quizQuestions.size) {
      _uiState.value = _uiState.value.copy(
        quizState = qState.copy(
          currentQuestionIndex = nextIndex,
          selectedOptionIndex = null,
          isAnswerSubmitted = false
        )
      )
    } else {
      _uiState.value = _uiState.value.copy(
        quizState = qState.copy(isQuizCompleted = true)
      )
    }
  }

  fun resetQuiz() {
    _uiState.value = _uiState.value.copy(
      quizState = QuizState()
    )
  }

  // Theme & Appearance
  fun setThemePalette(palette: AppThemePalette) {
    _uiState.value = _uiState.value.copy(themePalette = palette)
  }

  fun toggleDarkTheme(isDark: Boolean) {
    _uiState.value = _uiState.value.copy(isDarkTheme = isDark)
  }

  fun toggleAmoledMode(isAmoled: Boolean) {
    _uiState.value = _uiState.value.copy(isAmoledMode = isAmoled)
  }

  fun setFontSizeMultiplier(multiplier: Float) {
    _uiState.value = _uiState.value.copy(fontSizeMultiplier = multiplier)
  }
}
