package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.HistoricalQuote
import com.example.data.model.QuizQuestion
import com.example.data.repository.QuizAndQuotesData
import com.example.ui.AppUiState
import com.example.ui.HistoryViewModel
import com.example.ui.components.IslamicHeader
import com.example.ui.theme.BattleRed
import com.example.ui.theme.IslamicGold
import com.example.ui.theme.VictoryGreen

@Composable
fun QuizAndQuotesScreen(
  uiState: AppUiState,
  viewModel: HistoryViewModel,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableStateOf(0) } // 0: مسابقة التاريخ, 1: حكم وأقوال خالدة
  val context = LocalContext.current

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
  ) {
    IslamicHeader(
      title = "الاختبار والأقوال الخالدة",
      subtitle = "اختبر معلوماتك التاريخية واطلع على درر وحكم عظماء الأمة",
      badgeText = "مسابقات وحكم"
    )

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
            Icon(imageVector = Icons.Default.Quiz, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("اختبار التاريخ", fontWeight = FontWeight.Bold)
          }
        },
        modifier = Modifier.testTag("quiz_tab")
      )
      Tab(
        selected = selectedTab == 1,
        onClick = { selectedTab = 1 },
        text = {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.FormatQuote, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("أقوال وحكم", fontWeight = FontWeight.Bold)
          }
        },
        modifier = Modifier.testTag("quotes_tab")
      )
    }

    if (selectedTab == 0) {
      // Quiz Section
      InteractiveQuizSection(
        uiState = uiState,
        viewModel = viewModel,
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
          .padding(16.dp)
      )
    } else {
      // Quotes Section
      HistoricalQuotesSection(
        quotes = QuizAndQuotesData.quotes,
        onCopy = { text, author ->
          val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
          val clip = ClipData.newPlainText("Historical Quote", "\"$text\"\n— $author")
          clipboard.setPrimaryClip(clip)
          Toast.makeText(context, "تم نسخ المقولة بنجاح", Toast.LENGTH_SHORT).show()
        },
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
      )
    }
  }
}

@Composable
fun InteractiveQuizSection(
  uiState: AppUiState,
  viewModel: HistoryViewModel,
  modifier: Modifier = Modifier
) {
  val qState = uiState.quizState
  val questions = uiState.quizQuestions

  if (questions.isEmpty()) return

  if (qState.isQuizCompleted) {
    // Quiz Completed Summary Card
    Card(
      modifier = modifier.fillMaxWidth(),
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      border = androidx.compose.foundation.BorderStroke(1.5.dp, IslamicGold.copy(alpha = 0.5f))
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        Box(
          modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
            .background(IslamicGold.copy(alpha = 0.2f))
            .border(2.dp, IslamicGold, CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Text(text = "🏆", fontSize = 36.sp)
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
          text = "أحسنت! أكملت الاختبار",
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onSurface
          )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = "نتيجتك: ${qState.score} من أصل ${questions.size}",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
          )
        )

        Spacer(modifier = Modifier.height(6.dp))

        val percentage = (qState.score.toFloat() / questions.size) * 100
        val appraisal = when {
          percentage >= 90 -> "ممتاز! معرفة تاريخية استثنائية وباهرة! 🌟"
          percentage >= 70 -> "جيد جداً! لديك إلمام واسع بالتاريخ الإسلامي! 👏"
          else -> "محاولة طيبة! ننصحك بمراجعة بطاقات الشخصيات والمعارك للمزيد من الفائدة. 📚"
        }

        Text(
          text = appraisal,
          style = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
          )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
          onClick = { viewModel.resetQuiz() },
          shape = RoundedCornerShape(14.dp),
          colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
          modifier = Modifier.testTag("restart_quiz_button")
        ) {
          Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
          Spacer(modifier = Modifier.width(6.dp))
          Text("إعادة الاختبار", fontWeight = FontWeight.Bold)
        }
      }
    }
  } else {
    // Active Question
    val currentQuestion = questions[qState.currentQuestionIndex]
    val progress = (qState.currentQuestionIndex + 1).toFloat() / questions.size

    Card(
      modifier = modifier.fillMaxWidth(),
      shape = RoundedCornerShape(22.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(18.dp)
      ) {
        // Progress & Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
          ) {
            Text(
              text = "سؤال ${qState.currentQuestionIndex + 1} من ${questions.size}",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
              ),
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }

          Surface(
            shape = RoundedCornerShape(8.dp),
            color = IslamicGold.copy(alpha = 0.15f)
          ) {
            Text(
              text = "النقاط: ${qState.score}",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
              ),
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        LinearProgressIndicator(
          progress = { progress },
          modifier = Modifier
            .fillMaxWidth()
            .height(6.dp)
            .clip(RoundedCornerShape(3.dp)),
          color = MaterialTheme.colorScheme.primary,
          trackColor = MaterialTheme.colorScheme.surfaceVariant
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Question Text
        Text(
          text = currentQuestion.question,
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            lineHeight = 24.sp,
            color = MaterialTheme.colorScheme.onSurface
          )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Options
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          currentQuestion.options.forEachIndexed { index, option ->
            val isSelected = qState.selectedOptionIndex == index
            val isCorrect = currentQuestion.correctOptionIndex == index

            val containerColor = when {
              !qState.isAnswerSubmitted && isSelected -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
              qState.isAnswerSubmitted && isCorrect -> VictoryGreen.copy(alpha = 0.2f)
              qState.isAnswerSubmitted && isSelected && !isCorrect -> BattleRed.copy(alpha = 0.2f)
              else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
            }

            val borderColor = when {
              !qState.isAnswerSubmitted && isSelected -> MaterialTheme.colorScheme.primary
              qState.isAnswerSubmitted && isCorrect -> VictoryGreen
              qState.isAnswerSubmitted && isSelected && !isCorrect -> BattleRed
              else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
            }

            Surface(
              shape = RoundedCornerShape(12.dp),
              color = containerColor,
              border = androidx.compose.foundation.BorderStroke(1.5.dp, borderColor),
              modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = !qState.isAnswerSubmitted) {
                  viewModel.selectQuizOption(index)
                }
                .testTag("quiz_option_$index")
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Box(
                  modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
                    .border(1.5.dp, if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline, CircleShape),
                  contentAlignment = Alignment.Center
                ) {
                  if (isSelected) {
                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.White))
                  }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                  text = option,
                  style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface
                  )
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Explanation if submitted
        AnimatedVisibility(visible = qState.isAnswerSubmitted) {
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(modifier = Modifier.padding(12.dp)) {
              Icon(
                imageVector = Icons.Default.Lightbulb,
                contentDescription = null,
                tint = IslamicGold,
                modifier = Modifier.size(20.dp).padding(top = 2.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = currentQuestion.explanation,
                style = MaterialTheme.typography.bodySmall.copy(
                  lineHeight = 20.sp,
                  color = MaterialTheme.colorScheme.onSurface
                )
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Action Button (تأكيد الإجابة / التالي)
        if (!qState.isAnswerSubmitted) {
          Button(
            onClick = { viewModel.submitQuizAnswer() },
            enabled = qState.selectedOptionIndex != null,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("submit_answer_button")
          ) {
            Text("تأكيد الإجابة", fontWeight = FontWeight.Bold)
          }
        } else {
          Button(
            onClick = { viewModel.nextQuizQuestion() },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("next_question_button")
          ) {
            Text(
              if (qState.currentQuestionIndex + 1 == questions.size) "عرض النتيجة النهائية" else "السؤال التالي",
              fontWeight = FontWeight.Bold
            )
          }
        }
      }
    }
  }
}

@Composable
fun HistoricalQuotesSection(
  quotes: List<HistoricalQuote>,
  onCopy: (String, String) -> Unit,
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier
      .fillMaxWidth()
      .testTag("quotes_lazy_list"),
    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    items(quotes) { quote ->
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, IslamicGold.copy(alpha = 0.35f))
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = IslamicGold.copy(alpha = 0.12f)
            ) {
              Text(
                text = quote.category,
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
              )
            }

            IconButton(
              onClick = { onCopy(quote.text, quote.author) },
              modifier = Modifier.size(32.dp)
            ) {
              Icon(
                imageVector = Icons.Default.ContentCopy,
                contentDescription = "نسخ المقولة",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(18.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = "\"${quote.text}\"",
            style = MaterialTheme.typography.bodyLarge.copy(
              fontWeight = FontWeight.Medium,
              fontStyle = FontStyle.Italic,
              lineHeight = 24.sp,
              color = MaterialTheme.colorScheme.onSurface
            )
          )

          Spacer(modifier = Modifier.height(10.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "— ${quote.author}",
              style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
              )
            )
          }

          Text(
            text = "📌 ${quote.context}",
            style = MaterialTheme.typography.bodySmall.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
            ),
            modifier = Modifier.padding(top = 4.dp)
          )
        }
      }
    }
  }
}
