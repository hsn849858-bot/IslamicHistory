package com.example.data.model

enum class FigureCategory(val titleArabic: String, val badgeIcon: String) {
  ALL("الكل", "✨"),
  RASHIDUN_CALIPHS("الخلفاء الراشدون", "👑"),
  GREAT_COMMANDERS("القادة العسكريون", "⚔️"),
  GREAT_CALIPHS("الخلفاء العظام", "🏛️"),
  SAVIOR_LEADERS("القادة المنقذون", "🛡️"),
  SCHOLARS_AND_THINKERS("العلماء والمفكرون", "📜"),
  GREAT_SULTANS("السلاطين العظام", "⚜️"),
  HEROIC_LEADERS("قادة وأعلام إضافيون", "🌟")
}

data class HistoricalFigure(
  val id: String,
  val name: String,
  val title: String,
  val birthDeathYears: String,
  val ruleYears: String? = null,
  val category: FigureCategory,
  val lineageAndPersonality: String,
  val majorSections: List<FigureSection>,
  val majorBattles: List<String>,
  val quotes: List<String>,
  val martyrdomOrDeath: String,
  val historicalImpact: String,
  val roleIcon: String = "⚔️",
  val region: String = "الحجاز والشام"
)

data class FigureSection(
  val title: String,
  val content: String,
  val icon: String = "📌"
)

enum class BattleRegion(val titleArabic: String) {
  ALL("جميع الأقاليم"),
  ARABIA("شبه الجزيرة العربية"),
  LEVANT("بلاد الشام"),
  IRAQ_PERSIA("العراق وفارس"),
  EGYPT_NORTH_AFRICA("مصر وشمال أفريقيا"),
  ANDALUS("الأندلس وأوروبا"),
  CENTRAL_ASIA("آسيا الوسطى وخراسان"),
  ANATOLIA_BALKANS("الأناضول والبلقان"),
  INDIA_SINDH("السند وشبه القارة الهندية")
}

enum class BattleType(val titleArabic: String) {
  FIELD_BATTLE("معركة برية كبرى"),
  SIEGE("حصار وفتح مدينة"),
  NAVAL("معركة بحرية"),
  CONQUEST("حملة وفتح شامل")
}

data class Battle(
  val id: String,
  val name: String,
  val yearGregorian: Int,
  val yearHijri: Int,
  val periodEra: String,
  val leaderName: String,
  val opponentName: String,
  val region: BattleRegion,
  val battleType: BattleType,
  val latitude: Double,
  val longitude: Double,
  val locationName: String,
  val muslimForces: String,
  val opponentForces: String,
  val outcomeSummary: String,
  val strategicContext: String,
  val battleCourseAndTactics: String,
  val decisiveTurningPoint: String,
  val historicalConsequences: String,
  val keyHeroes: List<String> = emptyList()
)

data class IslamicEmpire(
  val id: String,
  val name: String,
  val arabicTitle: String,
  val startYear: Int,
  val endYear: Int,
  val durationYears: String,
  val capitalCities: List<String>,
  val peakAreaSqKm: String,
  val territorialSpan: String,
  val goldenAgeEra: String,
  val prominentRulers: List<String>,
  val majorConquestsAndWars: List<String>,
  val keyCommanders: List<String>,
  val civilizationalAchievements: List<String>,
  val fallFactorsAndContext: String,
  val flagColorHex: Long = 0xFF0C5E3D,
  val emblemSymbol: String = "🌙"
)

data class HistoricalMilestone(
  val yearGregorian: Int,
  val yearHijri: Int,
  val title: String,
  val summary: String,
  val category: String,
  val figureOrBattleId: String? = null
)

data class QuizQuestion(
  val id: Int,
  val question: String,
  val options: List<String>,
  val correctOptionIndex: Int,
  val explanation: String,
  val category: String
)

data class HistoricalQuote(
  val text: String,
  val author: String,
  val context: String,
  val category: String
)

enum class AppThemePalette(val titleArabic: String, val colorDescription: String) {
  EMERALD_GOLD("الزمردي والذهبي", "أخضر ملكي مع لمسات ذهبية إسلامية"),
  DAMASCUS_AZURE("الأزرق الدمشقي", "كوبالت وأزرق قاشاني أموي"),
  ANDALUSIAN_RUBY("القرمزي الأندلسي", "ياقوتي وأحمر قصر الحمراء"),
  SELJUK_SAPPHIRE("الكحلي السلجوقي", "أزرق سلجوقي وفيروزي عثماني"),
  DESERT_OCHRE("الرملي التراثي", "ألوان الصحراء والمخطوطات القديمة")
}
