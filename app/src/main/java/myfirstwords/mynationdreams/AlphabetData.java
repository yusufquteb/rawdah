package myfirstwords.mynationdreams;

import java.util.ArrayList;
import java.util.List;

public class AlphabetData {

    public static class LetterEntry {
        public String letter;          // الحرف
        public String letterName;      // اسم الحرف
        public String animalNameAr;    // اسم الحيوان بالعربية
        public String animalNameEn;    // اسم الحيوان بالإنجليزية
        public String animalFolder;    // مجلد صورة الحيوان في assets/animals/images/
        public String soundFile;       // ملف صوت الحيوان في onomatopoeia/
        public String songLyric;       // كلمات الأغنية
        public String letterImageFolder; // مجلد صورة الحرف
        public boolean isArabic;
    }

    // ══════════════════════════════════════════════════════════════
    // الحروف العربية مع أغنية الحروف
    // ══════════════════════════════════════════════════════════════
    public static List<LetterEntry> getArabicAlphabet() {
        List<LetterEntry> list = new ArrayList<>();

        // أ - أسد
        list.add(make("أ", "ألف", "أسد", "Lion",
            "lion", "lion",
            "ألف أسد، زئيره يملأ الغابة",
            "alphabet/images/001", true));

        // ب - بطة
        list.add(make("ب", "باء", "بطة", "Duck",
            "duck", "duck",
            "باء بطة، تعوم في الماء وتغني",
            "alphabet/images/002", true));

        // ت - تمساح
        list.add(make("ت", "تاء", "تمساح", "Crocodile",
            "crocodile", "crocodile",
            "تاء تمساح، يعيش في النيل العظيم",
            "alphabet/images/003", true));

        // ث - ثعلب (من أغنية الحروف)
        list.add(make("ث", "ثاء", "ثعلب", "Fox",
            "fox", "fox",
            "ثاء ثعلب، صاد دجاجة، هو مكار وقت الحاجة",
            "alphabet/images/004", true));

        // ج - جمل
        list.add(make("ج", "جيم", "جمل", "Camel",
            "camel", "camel",
            "جيم جمل، يسير في الصحراء ولا يتعب",
            "alphabet/images/005", true));

        // ح - حصان
        list.add(make("ح", "حاء", "حصان", "Horse",
            "horse", "horse",
            "حاء حصان، يجري ويطير كالريح",
            "alphabet/images/006", true));

        // خ - خروف
        list.add(make("خ", "خاء", "خروف", "Sheep",
            "sheep", "sheep",
            "خاء خروف، صوفه دافئ وناعم",
            "alphabet/images/007", true));

        // د - دب
        list.add(make("د", "دال", "دب", "Bear",
            "bear", "bear",
            "دال دب كبير، يأكل العسل اللذيذ",
            "alphabet/images/008", true));

        // ذ - ذئب
        list.add(make("ذ", "ذال", "ذئب", "Wolf",
            "wolf", "wolf",
            "ذال ذئب، يعوي في الليل بصوت قوي",
            "alphabet/images/009", true));

        // ر - رنة (غزال)
        list.add(make("ر", "راء", "غزال", "Deer",
            "deer", "deer",
            "راء ريم، الغزال الجميل يقفز بخفة",
            "alphabet/images/010", true));

        // ز - زرافة
        list.add(make("ز", "زاي", "زرافة", "Giraffe",
            "giraffe", "giraffe",
            "زاي زرافة، عنقها يصل للسحاب",
            "alphabet/images/011", true));

        // س - سمكة
        list.add(make("س", "سين", "سمكة", "Fish",
            "fish", "fish",
            "سين سمكة، تسبح في البحر بسرعة",
            "alphabet/images/012", true));

        // ش - شمبانزي
        list.add(make("ش", "شين", "شمبانزي", "Chimpanzee",
            "chimpanzee", "chimpanzee",
            "شين شمبانزي، يلعب على الأشجار ويقفز",
            "alphabet/images/013", true));

        // ص - صقر (نسر)
        list.add(make("ص", "صاد", "نسر", "Eagle",
            "eagle", "vulture",
            "صاد صقر، يحلق عالياً في السماء",
            "alphabet/images/014", true));

        // ض - ضفدع
        list.add(make("ض", "ضاد", "ضفدع", "Frog",
            "frog", "frog",
            "ضاد ضفدع، ينط ويغني بجانب النهر",
            "alphabet/images/015", true));

        // ط - طاووس
        list.add(make("ط", "طاء", "طاووس", "Peacock",
            "peacock", "peacock",
            "طاء طاووس، يفرد ذيله الجميل الملوّن",
            "alphabet/images/016", true));

        // ظ - ظليم (نعامة)
        list.add(make("ظ", "ظاء", "نعامة", "Ostrich",
            "ostrich", "ostrich",
            "ظاء ظليم، النعامة تجري أسرع من الريح",
            "alphabet/images/017", true));

        // ع - عندليب
        list.add(make("ع", "عين", "عندليب", "Nightingale",
            "nightingale", "nightingale",
            "عين عندليب، يغني أجمل أغنية في الحديقة",
            "alphabet/images/018", true));

        // غ - غزال
        list.add(make("غ", "غين", "غزال", "Deer",
            "deer", "deer",
            "غين غزال رشيق، يعدو في السهول الخضراء",
            "alphabet/images/019", true));

        // ف - فيل
        list.add(make("ف", "فاء", "فيل", "Elephant",
            "elephant", "elephant",
            "فاء فيل كبير، أطول أنف في الحيوانات",
            "alphabet/images/020", true));

        // ق - قط
        list.add(make("ق", "قاف", "قطة", "Cat",
            "cat", "cat",
            "قاف قطة صغيرة، تموء وتلعب بالخيط",
            "alphabet/images/021", true));

        // ك - كوالا
        list.add(make("ك", "كاف", "كوالا", "Koala",
            "koala", "koala",
            "كاف كوالا، ينام على أشجار الأوكالبتوس",
            "alphabet/images/022", true));

        // ل - ليمور
        list.add(make("ل", "لام", "ليمور", "Lemur",
            "lemur", "monkey",
            "لام ليمور، عيونه كبيرة ومضيئة في الليل",
            "alphabet/images/023", true));

        // م - قرد (مرح)
        list.add(make("م", "ميم", "قرد مرح", "Monkey",
            "monkey", "monkey",
            "ميم قرد مرح، يتأرجح على الأشجار بفرح",
            "alphabet/images/024", true));

        // ن - نمر
        list.add(make("ن", "نون", "نمر", "Tiger",
            "tiger", "tiger",
            "نون نمر، ملك الأدغال يمشي بكبرياء",
            "alphabet/images/025", true));

        // هـ - فرس النهر
        list.add(make("هـ", "هاء", "فرس النهر", "Hippopotamus",
            "hippopotamus", "hippopotamus",
            "هاء هيبو، فرس النهر يحب الماء كثيراً",
            "alphabet/images/026", true));

        // و - وحيد القرن
        list.add(make("و", "واو", "وحيد القرن", "Rhinoceros",
            "rhinoceros", "rhinoceros",
            "واو وحيد القرن، له قرن قوي على أنفه",
            "alphabet/images/027", true));

        // ي - يمامة
        list.add(make("ي", "ياء", "يمامة", "Pigeon",
            "pigeon", "stork",
            "ياء يمامة، تطير في السماء وتحمل السلام",
            "alphabet/images/028", true));

        return list;
    }

    // ══════════════════════════════════════════════════════════════
    // الحروف الإنجليزية مع حيوان لكل حرف
    // ══════════════════════════════════════════════════════════════
    public static List<LetterEntry> getEnglishAlphabet() {
        List<LetterEntry> list = new ArrayList<>();

        list.add(make("A", "A", "تمساح", "Alligator",
            "crocodile", "crocodile",
            "A is for Alligator! A-A-Alligator snaps!",
            "alphabet-e/images/001", false));

        list.add(make("B", "B", "دب", "Bear",
            "bear", "bear",
            "B is for Bear! B-B-Bear loves honey!",
            "alphabet-e/images/002", false));

        list.add(make("C", "C", "قطة", "Cat",
            "cat", "cat",
            "C is for Cat! C-C-Cat goes meow!",
            "alphabet-e/images/003", false));

        list.add(make("D", "D", "كلب", "Dog",
            "dog", "dog",
            "D is for Dog! D-D-Dog goes woof!",
            "alphabet-e/images/004", false));

        list.add(make("E", "E", "فيل", "Elephant",
            "elephant", "elephant",
            "E is for Elephant! E-E-Elephant is so big!",
            "alphabet-e/images/005", false));

        list.add(make("F", "F", "ثعلب", "Fox",
            "fox", "fox",
            "F is for Fox! F-F-Fox is very clever!",
            "alphabet-e/images/006", false));

        list.add(make("G", "G", "زرافة", "Giraffe",
            "giraffe", "giraffe",
            "G is for Giraffe! G-G-Giraffe has a long neck!",
            "alphabet-e/images/007", false));

        list.add(make("H", "H", "حصان", "Horse",
            "horse", "horse",
            "H is for Horse! H-H-Horse runs fast!",
            "alphabet-e/images/008", false));

        list.add(make("I", "I", "إيغوانا", "Iguana",
            "iguana", "lizard",
            "I is for Iguana! I-I-Iguana loves the sun!",
            "alphabet-e/images/009", false));

        list.add(make("J", "J", "قنديل البحر", "Jellyfish",
            "jellyfish", "fish",
            "J is for Jellyfish! J-J-Jellyfish floats!",
            "alphabet-e/images/010", false));

        list.add(make("K", "K", "كنغر", "Kangaroo",
            "kangaroo", "kangaroo",
            "K is for Kangaroo! K-K-Kangaroo jumps high!",
            "alphabet-e/images/011", false));

        list.add(make("L", "L", "أسد", "Lion",
            "lion", "lion",
            "L is for Lion! L-L-Lion is the king!",
            "alphabet-e/images/012", false));

        list.add(make("M", "M", "قرد", "Monkey",
            "monkey", "monkey",
            "M is for Monkey! M-M-Monkey climbs trees!",
            "alphabet-e/images/013", false));

        list.add(make("N", "N", "بلبل", "Nightingale",
            "nightingale", "nightingale",
            "N is for Nightingale! N-N-Nightingale sings!",
            "alphabet-e/images/014", false));

        list.add(make("O", "O", "نعامة", "Ostrich",
            "ostrich", "ostrich",
            "O is for Ostrich! O-O-Ostrich runs so fast!",
            "alphabet-e/images/015", false));

        list.add(make("P", "P", "ببغاء", "Parrot",
            "parrot", "parrot",
            "P is for Parrot! P-P-Parrot can talk!",
            "alphabet-e/images/016", false));

        list.add(make("Q", "Q", "سمان", "Quail",
            "quail", "quail",
            "Q is for Quail! Q-Q-Quail is very small!",
            "alphabet-e/images/017", false));

        list.add(make("R", "R", "أرنب", "Rabbit",
            "rabbit", "rabbit",
            "R is for Rabbit! R-R-Rabbit hops along!",
            "alphabet-e/images/018", false));

        list.add(make("S", "S", "ثعبان", "Snake",
            "snake", "snake",
            "S is for Snake! S-S-Snake goes ssss!",
            "alphabet-e/images/019", false));

        list.add(make("T", "T", "نمر", "Tiger",
            "tiger", "tiger",
            "T is for Tiger! T-T-Tiger has stripes!",
            "alphabet-e/images/020", false));

        list.add(make("U", "U", "قنفذ البحر", "Urchin",
            "urchin", "hedgehog",
            "U is for Urchin! U-U-Urchin is spiky!",
            "alphabet-e/images/021", false));

        list.add(make("V", "V", "نسر", "Vulture",
            "vulture", "vulture",
            "V is for Vulture! V-V-Vulture flies high!",
            "alphabet-e/images/022", false));

        list.add(make("W", "W", "ذئب", "Wolf",
            "wolf", "wolf",
            "W is for Wolf! W-W-Wolf howls at the moon!",
            "alphabet-e/images/023", false));

        list.add(make("X", "X", "ثعلب (في كلمة foX)", "Fox (foX)",
            "fox", "fox",
            "X is in foX! The sneaky FOX has an X!",
            "alphabet-e/images/024", false));

        list.add(make("Y", "Y", "ياك", "Yak",
            "yak", "yak",
            "Y is for Yak! Y-Y-Yak lives in the mountains!",
            "alphabet-e/images/025", false));

        list.add(make("Z", "Z", "حمار وحشي", "Zebra",
            "zebra", "zebra",
            "Z is for Zebra! Z-Z-Zebra has black and white stripes!",
            "alphabet-e/images/026", false));

        return list;
    }

    private static LetterEntry make(String letter, String letterName,
                                     String animalAr, String animalEn,
                                     String animalFolder, String soundFile,
                                     String songLyric, String letterFolder,
                                     boolean isArabic) {
        LetterEntry e = new LetterEntry();
        e.letter = letter;
        e.letterName = letterName;
        e.animalNameAr = animalAr;
        e.animalNameEn = animalEn;
        e.animalFolder = animalFolder;
        e.soundFile = soundFile;
        e.songLyric = songLyric;
        e.letterImageFolder = letterFolder;
        e.isArabic = isArabic;
        return e;
    }

    // ══════════════════════════════════════════════════════════════
    // بيانات الفئات للشاشة الرئيسية
    // ══════════════════════════════════════════════════════════════
    public static class CategoryInfo {
        public String id;
        public String nameAr;
        public String emoji;
        public int colorRes;
        public int bgColorRes;
        public String assetFolder;
    }

    public static List<CategoryInfo> getAllCategories() {
        List<CategoryInfo> list = new ArrayList<>();

        list.add(cat("animals", "الحيوانات", "🐾",
            R.color.cat_animals, R.color.cat_animals_bg, "animals"));
        list.add(cat("alphabet", "الحروف العربية", "أ",
            R.color.cat_alphabet_ar, R.color.cat_alphabet_ar_bg, "alphabet"));
        list.add(cat("alphabet-e", "الحروف الإنجليزية", "A",
            R.color.cat_alphabet_en, R.color.cat_alphabet_en_bg, "alphabet-e"));
        list.add(cat("numbers", "الأرقام", "١٢٣",
            R.color.cat_numbers, R.color.cat_numbers_bg, "numbers"));
        list.add(cat("colors", "الألوان", "🎨",
            R.color.cat_colors, R.color.cat_colors_bg, "colors"));
        list.add(cat("shapes", "الأشكال", "◆",
            R.color.cat_shapes, R.color.cat_shapes_bg, "shapes"));
        list.add(cat("body", "جسم الإنسان", "🫀",
            R.color.cat_body, R.color.cat_body_bg, "body"));
        list.add(cat("vehicles", "المواصلات", "🚗",
            R.color.cat_vehicles, R.color.cat_vehicles_bg, "vehicles"));
        list.add(cat("tools", "الأدوات", "🔧",
            R.color.cat_tools, R.color.cat_tools_bg, "tools"));
        list.add(cat("food", "الطعام", "🍎",
            R.color.cat_food, R.color.cat_food_bg, "food"));
        list.add(cat("school", "المدرسة", "📚",
            R.color.cat_school, R.color.cat_school_bg, "school"));
        list.add(cat("clothes", "الملابس", "👕",
            R.color.cat_clothes, R.color.cat_clothes_bg, "clothes"));
        list.add(cat("bath", "الحمام", "🛁",
            R.color.cat_bath, R.color.cat_bath_bg, "bath"));
        list.add(cat("kitchen", "المطبخ", "🍳",
            R.color.cat_kitchen, R.color.cat_kitchen_bg, "kitchen"));
        list.add(cat("furniture", "الأثاث", "🪑",
            R.color.cat_furniture, R.color.cat_furniture_bg, "furniture"));
        list.add(cat("seasons", "فصول السنة", "🌸",
            R.color.cat_seasons, R.color.cat_seasons_bg, "seasons"));
        list.add(cat("months", "الشهور", "📅",
            R.color.cat_months, R.color.cat_months_bg, "months"));
        list.add(cat("week days", "أيام الأسبوع", "📆",
            R.color.cat_weekdays, R.color.cat_weekdays_bg, "week days"));

        return list;
    }

    private static CategoryInfo cat(String id, String nameAr, String emoji,
                                     int colorRes, int bgColorRes, String folder) {
        CategoryInfo c = new CategoryInfo();
        c.id = id;
        c.nameAr = nameAr;
        c.emoji = emoji;
        c.colorRes = colorRes;
        c.bgColorRes = bgColorRes;
        c.assetFolder = folder;
        return c;
    }

    // بيانات الأصوات للعبة أصوات الحيوانات
    public static class AnimalSoundItem {
        public String animalNameAr;
        public String animalNameEn;
        public String imageFolder;
        public String soundFile;
    }

    public static List<AnimalSoundItem> getAnimalSoundsForGame() {
        List<AnimalSoundItem> list = new ArrayList<>();
        String[][] data = {
            {"أسد", "Lion", "lion", "lion"},
            {"بطة", "Duck", "duck", "duck"},
            {"كلب", "Dog", "dog", "dog"},
            {"قطة", "Cat", "cat", "cat"},
            {"بقرة", "Cow", "cow", "cow"},
            {"حصان", "Horse", "horse", "horse"},
            {"ضفدع", "Frog", "frog", "frog"},
            {"بومة", "Owl", "owl", "owl"},
            {"فيل", "Elephant", "elephant", "elephant"},
            {"قرد", "Monkey", "monkey", "monkey"},
            {"نمر", "Tiger", "tiger", "tiger"},
            {"ذئب", "Wolf", "wolf", "wolf"},
            {"خروف", "Sheep", "sheep", "sheep"},
            {"دب", "Bear", "bear", "bear"},
            {"ثعلب", "Fox", "fox", "fox"},
            {"ببغاء", "Parrot", "parrot", "parrot"},
            {"نسر", "Vulture", "vulture", "vulture"},
            {"ثعبان", "Snake", "snake", "snake"},
            {"حمار وحشي", "Zebra", "zebra", "zebra"},
            {"كنغر", "Kangaroo", "kangaroo", "kangaroo"},
            {"زرافة", "Giraffe", "giraffe", "giraffe"},
            {"خنزير", "Pig", "pig", "pig"},
            {"بجعة", "Swan", "swan", "stork"},
            {"دجاجة", "Chicken", "chicken", "chicken"},
            {"حمار", "Donkey", "donkey", "donkey"},
        };
        for (String[] d : data) {
            AnimalSoundItem item = new AnimalSoundItem();
            item.animalNameAr = d[0];
            item.animalNameEn = d[1];
            item.imageFolder = d[2];
            item.soundFile = d[3];
            list.add(item);
        }
        return list;
    }
}
