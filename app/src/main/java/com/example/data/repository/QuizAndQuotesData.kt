package com.example.data.repository

import com.example.data.model.HistoricalMilestone
import com.example.data.model.HistoricalQuote
import com.example.data.model.QuizQuestion

object QuizAndQuotesData {
  val quizQuestions: List<QuizQuestion> = listOf(
    QuizQuestion(
      id = 1,
      question = "من هو القائد المسلم الذي لُقب بـ\"سيف الله المسلول\" وخاض أكثر من مئة معركة دون هزيمة؟",
      options = listOf("سعد بن أبي وقاص", "خالد بن الوليد", "طارق بن زياد", "القعقاع بن عمرو"),
      correctOptionIndex = 1,
      explanation = "لُقّب خالد بن الوليد رضي الله عنه بـ\"سيف الله المسلول\" من قبل النبي ﷺ يوم مؤتة بعد أن أنقذ جيش المسلمين بحنكة عسكرية عبقرية.",
      category = "القادة العسكريون"
    ),
    QuizQuestion(
      id = 2,
      question = "في أي عام تم فتح القسطنطينية على يد السلطان العثماني محمد الفاتح؟",
      options = listOf("1453م (857هـ)", "1492م (897هـ)", "1258م (656هـ)", "1071م (463هـ)"),
      correctOptionIndex = 0,
      explanation = "فُتحت القسطنطينية يوم الثلاثاء 29 مايو 1453م (20 جمادى الأولى 857هـ) محققة البشارة النبوية الشريفة.",
      category = "الفتوحات والمعارك"
    ),
    QuizQuestion(
      id = 3,
      question = "من هو العالم المسلم الذي اخترع علم الجبر وتُشتق من اسمه كلمة الخوارزميات (Algorithms)؟",
      options = listOf("ابن الهيثم", "محمد بن موسى الخوارزمي", "ابن سينا", "جابر بن حيان"),
      correctOptionIndex = 1,
      explanation = "العالم العبقري محمد بن موسى الخوارزمي هو مؤسس علم الجبر ومبتكر نظام الخوارزميات المعتمد في كل علوم الحاسوب والبرمجة الحديثة.",
      category = "العلماء والمفكرون"
    ),
    QuizQuestion(
      id = 4,
      question = "ما هي المعركة التاريخية التي انتصر فيها المسلمون بقيادة قطز وبيبرس وأوقفت الغزو المغولي التتاري عام 1260م؟",
      options = listOf("معركة حطين", "معركة اليرموك", "معركة عين جالوت", "معركة ملازكرد"),
      correctOptionIndex = 2,
      explanation = "معركة عين جالوت في 25 رمضان 658هـ (1260م) كانت أول هزيمة ساحقة للمغول في تاريخهم وأنقذت العالم الإسلامي ومصر من الدمار الشامل.",
      category = "الفتوحات والمعارك"
    ),
    QuizQuestion(
      id = 5,
      question = "من هو الخليفة الأموي الذي بنى قبة الصخرة المشرفة في القدس وسك أول دينار إسلامي وعرّب الدواوين؟",
      options = listOf("معاوية بن أبي سفيان", "عبد الملك بن مروان", "الوليد بن عبد الملك", "عمر بن عبد العزيز"),
      correctOptionIndex = 1,
      explanation = "الخليفة عبد الملك بن مروان شيد قبة الصخرة عام 691م وسك أول دينار إسلامي خالص عام 77هـ وجعل اللغة العربية اللغة الرسمية لكافة الدواوين.",
      category = "الخلفاء العظام"
    ),
    QuizQuestion(
      id = 6,
      question = "أي سلطان سلجوقي قاد جيش المسلمين في معركة ملازكرد (1071م) وهزم وأسر الإمبراطور البيزنطي رومانوس؟",
      options = listOf("طغرل بك", "ألب أرسلان", "ملكشاه", "سليمان القانوني"),
      correctOptionIndex = 1,
      explanation = "السلطان ألب أرسلان (الأسد الشجاع) هزم الجيش البيزنطي الضخم في ملازكرد وفتح أبواب الأناضول (تركيا) للاستيطان الإسلامي.",
      category = "السلاطين العظام"
    ),
    QuizQuestion(
      id = 7,
      question = "من هو القائد الأندلسي والمرابطي الذي عبر من المغرب وأنقذ الأندلس في معركة الزلاقة الكبرى عام 1086م؟",
      options = listOf("موسى بن نصير", "يوسف بن تاشفين", "طارق بن زياد", "عبد الرحمن الداخل"),
      correctOptionIndex = 1,
      explanation = "أمير المسلمين يوسف بن تاشفين قائد المرابطين هزم ملك قشتالة ألفونسو السادس في معركة الزلاقة وأنقذ الأندلس من السقوط لأربعة قرون إضافية.",
      category = "القادة المنقذون"
    ),
    QuizQuestion(
      id = 8,
      question = "ما هو الكتاب الطبي الذي ألّفه ابن سينا وظل المرجع الأول للطب في جامعات أوروبا لأكثر من 600 سنة؟",
      options = listOf("الحاوي في الطب", "القانون في الطب", "المناظر", "الشفاء"),
      correctOptionIndex = 1,
      explanation = "كتاب \"القانون في الطب\" للشيخ الرئيس ابن سينا كان الدستور الطبي العالمي الإلزامي في جامعات أوروبا حتى القرن السابع عشر الميلادي.",
      category = "العلماء والمفكرون"
    ),
    QuizQuestion(
      id = 9,
      question = "ما هي المدينة التي أسسها القائد عقبة بن نافع لتكون أول قاعدة عسكرية وثقافية للإسلام في شمال إفريقيا؟",
      options = listOf("القاهرة", "القيروان", "فاس", "مراكش"),
      correctOptionIndex = 1,
      explanation = "أسس عقبة بن نافع مدينة القيروان في تونس عام 670م (50هـ) وبنى بها جامع القيروان الكبير ليكون منارة للإسلام في المغرب.",
      category = "الفتوحات والمعارك"
    ),
    QuizQuestion(
      id = 10,
      question = "من هو رائد علم البصريات الحديث ومخترع مبدأ القمرة المظلمة (الكاميرا) ومؤسس المنهج العلمي التجريبي؟",
      options = listOf("الحسن بن الهيثم", "البيروني", "ابن رشد", "الكندي"),
      correctOptionIndex = 0,
      explanation = "الحسن بن الهيثم ألّف \"كتاب المناظر\" وأثبت آلية الرؤية الصحيحة واخترع البيت المظلم (Camera Obscura) التي أصل تسمية الكاميرا الحالية.",
      category = "العلماء والمفكرون"
    )
  )

  val quotes: List<HistoricalQuote> = listOf(
    HistoricalQuote(
      text = "لو كان بيني وبين الناس شعرة ما انقطعت؛ إذا مدّوها خلّيتها، وإذا خلّوها مددتها.",
      author = "معاوية بن أبي سفيان",
      context = "فلسفة الدبلوماسية والحلم السياسي في إدارة الدولة والخلافة",
      category = "الحكمة والسياسة"
    ),
    HistoricalQuote(
      text = "انثروا القمح على رؤوس الجبال حتى لا يقال جاع طير في بلاد المسلمين.",
      author = "عمر بن عبد العزيز (عمر الثاني)",
      context = "في ذروة العدل والرخاء الاقتصادي والتكافل في الدولة الأموية",
      category = "العدل والرحمة"
    ),
    HistoricalQuote(
      text = "كيف أبتسم والمسجد الأقصى أسير؟",
      author = "صلاح الدين الأيوبي",
      context = "قبل معركة حطين وتحرير بيت المقدس من الصليبيين",
      category = "العزة والشرف"
    ),
    HistoricalQuote(
      text = "نحن لا نستسلم.. ننتصر أو نموت! وإن عمري سيكون أطول من عمر شانقي!",
      author = "عمر المختار (أسد الصحراء)",
      context = "أثناء محاكمته الصورية من قبل الاحتلال الإيطالي الفاشي",
      category = "الجهاد والكرامة"
    ),
    HistoricalQuote(
      text = "واإسلاماه! يا الله انصر عبدك قطز على التتار!",
      author = "سيف الدين قطز",
      context = "صيحته الملحمية في معركة عين جالوت لقلب موازين المعركة",
      category = "البطولة والشهامة"
    ),
    HistoricalQuote(
      text = "يا سحابة، أمطري حيث شئتِ، فإن خراجكِ سيأتيني!",
      author = "هارون الرشيد",
      context = "تعبير عن اتساع رقعة الخلافة العباسية وهيبتها وسيادتها العالمية",
      category = "المجد والقوة"
    ),
    HistoricalQuote(
      text = "لو أن لي مئة نفس لخرجت في سبيل الله نفساً بعد نفس، وما لزمتُ داري.",
      author = "خالد بن الوليد",
      context = "عن حبه للجهاد وحرصه على إعلاء كلمة الله والشهادة",
      category = "الفداء والتضحية"
    ),
    HistoricalQuote(
      text = "الظلم مؤذن بخراب العمران.",
      author = "عبد الرحمن بن خلدون",
      context = "القاعدة الذهبية في مقدمة ابن خلدون لتفسير سقوط الدول والحضارات",
      category = "الفكر والعمران"
    ),
    HistoricalQuote(
      text = "غلبة الحجة أحب إليّ من غلبة القدرة، فإن غلبة القدرة تزول بزوالها، وغلبة الحجة لا تبطل أبداً.",
      author = "عبد الله المأمون",
      context = "رؤيته في ترسيخ البحث العلمي والمناظرات الفكرية في بيت الحكمة",
      category = "العلم والمعرفة"
    )
  )

  val milestones: List<HistoricalMilestone> = listOf(
    HistoricalMilestone(
      yearGregorian = 622,
      yearHijri = 1,
      title = "الهجرة النبوية الشريفة",
      summary = "هجرة النبي ﷺ وصاحبه أبي بكر من مكة إلى المدينة المنورة وتأسيس الدولة الإسلامية وبداية التقويم الهجري.",
      category = "العهد النبوي"
    ),
    HistoricalMilestone(
      yearGregorian = 624,
      yearHijri = 2,
      title = "غزوة بدر الكبرى (يوم الفرقان)",
      summary = "أول نصر عسكري في تاريخ الإسلام وتحطيم كبرياء قريش.",
      category = "العهد النبوي",
      figureOrBattleId = "battle_of_badr"
    ),
    HistoricalMilestone(
      yearGregorian = 630,
      yearHijri = 8,
      title = "فتح مكة العظيم",
      summary = "دخول النبي ﷺ مكة فاتحاً مطهراً للكعبة من الأوثان معلناً: \"اذهبوا فأنتم الطلقاء\".",
      category = "العهد النبوي"
    ),
    HistoricalMilestone(
      yearGregorian = 632,
      yearHijri = 11,
      title = "وفاة النبي ﷺ وقيام الخلافة الراشدة",
      summary = "بيعة أبي بكر الصديق بالخلافة وبدء حروب الردة لتوحيد جزيرة العرب.",
      category = "الخلافة الراشدة",
      figureOrBattleId = "abu_bakr"
    ),
    HistoricalMilestone(
      yearGregorian = 636,
      yearHijri = 15,
      title = "معركتا اليرموك والقادسية",
      summary = "سقوط السيادة البيزنطية في الشام والساسانية في العراق في آن واحد وفتح القدس الشريف.",
      category = "الخلافة الراشدة",
      figureOrBattleId = "battle_of_yarmouk"
    ),
    HistoricalMilestone(
      yearGregorian = 661,
      yearHijri = 41,
      title = "عام الجماعة وتأسيس الدولة الأموية",
      summary = "تنازل الحسن بن علي لمعاوية بن أبي سفيان وتوحيد الأمة ونقل العاصمة إلى دمشق.",
      category = "الدولة الأموية",
      figureOrBattleId = "muawiyah_ibn_abi_sufyan"
    ),
    HistoricalMilestone(
      yearGregorian = 711,
      yearHijri = 92,
      title = "فتح الأندلس وبلاد السند",
      summary = "عبور طارق بن زياد وموسى بن نصير إلى أوروبا وفتح محمد بن القاسم لوادي السند.",
      category = "الدولة الأموية",
      figureOrBattleId = "tariq_ibn_ziyad"
    ),
    HistoricalMilestone(
      yearGregorian = 750,
      yearHijri = 132,
      title = "قيام الخلافة العباسية وبناء بغداد",
      summary = "سقوط الأمويين في معركة الزاب وتأسيس العباسيين لدولتهم وبناء بغداد كعاصمة للعلم.",
      category = "الدولة العباسية",
      figureOrBattleId = "harun_al_rashid"
    ),
    HistoricalMilestone(
      yearGregorian = 830,
      yearHijri = 215,
      title = "عصر النهضة العلمية وبيت الحكمة",
      summary = "تأسيس بيت الحكمة في بغداد وظهور الخوارزمي والرازي وتطور علم الجبر والطب والفلك.",
      category = "العصر الذهبي للعلوم",
      figureOrBattleId = "al_khwarizmi"
    ),
    HistoricalMilestone(
      yearGregorian = 1071,
      yearHijri = 463,
      title = "معركة ملازكرد وفتح الأناضول",
      summary = "انتصار ألب أرسلان السلجوقي وأسر الإمبراطور البيزنطي ودخول الأتراك للأناضول.",
      category = "الدولة السلجوقية",
      figureOrBattleId = "alp_arslan"
    ),
    HistoricalMilestone(
      yearGregorian = 1187,
      yearHijri = 583,
      title = "معركة حطين وتحرير القدس الشريف",
      summary = "انتصار صلاح الدين الأيوبي على الصليبيين وتحرير بيت المقدس بعد 88 عاماً من الاحتلال.",
      category = "الدولة الأيوبية",
      figureOrBattleId = "salah_al_din"
    ),
    HistoricalMilestone(
      yearGregorian = 1260,
      yearHijri = 658,
      title = "معركة عين جالوت وإنقاذ الحضارة",
      summary = "سحق قطز وبيبرس لجيش التتار والمغول في فلسطين وإيقاف التدمير المغولي للعالم.",
      category = "دولة المماليك",
      figureOrBattleId = "saif_al_din_qutuz"
    ),
    HistoricalMilestone(
      yearGregorian = 1453,
      yearHijri = 857,
      title = "فتح القسطنطينية (إسطنبول)",
      summary = "فتح السلطان محمد الفاتح للقسطنطينية ونهاية الإمبراطورية البيزنطية بعد 1123 عاماً.",
      category = "الدولة العثمانية",
      figureOrBattleId = "mehmed_the_conqueror"
    ),
    HistoricalMilestone(
      yearGregorian = 1526,
      yearHijri = 932,
      title = "معركة موهاكس وذروة العصر العثماني",
      summary = "انتصار سليمان القانوني وضم المجر والسيطرة المطلقة على البحر الأبيض المتوسط.",
      category = "الدولة العثمانية",
      figureOrBattleId = "suleiman_the_magnificent"
    )
  )
}
