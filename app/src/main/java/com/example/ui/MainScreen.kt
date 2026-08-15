package com.example.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.screens.BattlesMapScreen
import com.example.ui.screens.DynastiesScreen
import com.example.ui.screens.FiguresScreen
import com.example.ui.screens.QuizAndQuotesScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.TimelineScreen
import com.example.ui.theme.IslamicGold
import com.example.ui.theme.IslamicHistoryTheme

@Composable
fun MainScreen(
  viewModel: HistoryViewModel
) {
  val uiState by viewModel.uiState.collectAsState()

  IslamicHistoryTheme(
    themePalette = uiState.themePalette,
    darkTheme = uiState.isDarkTheme,
    amoledMode = uiState.isAmoledMode
  ) {
    // Force RTL layout direction for Arabic typography and orientation
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
      Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
          NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp,
            modifier = Modifier.testTag("main_bottom_nav_bar")
          ) {
            val navItems = listOf(
              Triple(AppScreen.FIGURES, "الشخصيات", Icons.Default.People),
              Triple(AppScreen.BATTLES_MAP, "الخريطة", Icons.Default.Map),
              Triple(AppScreen.DYNASTIES, "الدول", Icons.Default.AccountBalance),
              Triple(AppScreen.TIMELINE, "الخط الزمني", Icons.Default.Timeline),
              Triple(AppScreen.QUIZ, "المسابقات", Icons.Default.Quiz),
              Triple(AppScreen.SETTINGS, "الإعدادات", Icons.Default.Settings)
            )

            navItems.forEach { (screen, label, icon) ->
              val isSelected = uiState.currentScreen == screen
              NavigationBarItem(
                selected = isSelected,
                onClick = { viewModel.setScreen(screen) },
                icon = {
                  Icon(
                    imageVector = icon,
                    contentDescription = label,
                    modifier = Modifier.size(20.dp)
                  )
                },
                label = {
                  Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall.copy(
                      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                      fontSize = 10.sp
                    )
                  )
                },
                colors = NavigationBarItemDefaults.colors(
                  selectedIconColor = MaterialTheme.colorScheme.primary,
                  selectedTextColor = MaterialTheme.colorScheme.primary,
                  indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                  unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                  unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                ),
                modifier = Modifier.testTag("nav_item_${screen.name}")
              )
            }
          }
        }
      ) { innerPadding ->
        Box(
          modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ) {
          AnimatedContent(
            targetState = uiState.currentScreen,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "screen_transition"
          ) { targetScreen ->
            when (targetScreen) {
              AppScreen.FIGURES -> FiguresScreen(uiState = uiState, viewModel = viewModel)
              AppScreen.BATTLES_MAP -> BattlesMapScreen(uiState = uiState, viewModel = viewModel)
              AppScreen.DYNASTIES -> DynastiesScreen(uiState = uiState, viewModel = viewModel)
              AppScreen.TIMELINE -> TimelineScreen(uiState = uiState, viewModel = viewModel)
              AppScreen.QUIZ -> QuizAndQuotesScreen(uiState = uiState, viewModel = viewModel)
              AppScreen.SETTINGS -> SettingsScreen(uiState = uiState, viewModel = viewModel)
            }
          }
        }
      }
    }
  }
}
