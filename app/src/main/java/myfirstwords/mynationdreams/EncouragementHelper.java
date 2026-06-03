package myfirstwords.mynationdreams;

import android.content.Context;
import java.util.Random;

public class EncouragementHelper {

    private static final Random random = new Random();

    public static String getCorrectMessage(Context ctx) {
        String[] msgs = ctx.getResources().getStringArray(R.array.encouragements_correct);
        return msgs[random.nextInt(msgs.length)];
    }

    public static String getWrongMessage(Context ctx) {
        String[] msgs = ctx.getResources().getStringArray(R.array.encouragements_wrong);
        return msgs[random.nextInt(msgs.length)];
    }

    public static String getStartMessage(Context ctx) {
        String[] msgs = ctx.getResources().getStringArray(R.array.encouragements_start);
        return msgs[random.nextInt(msgs.length)];
    }

    public static String getGreeting(Context ctx, String name, boolean isBoy) {
        String base = isBoy ? "مرحباً يا بطل " : "مرحباً يا أميرة ";
        return base + name + "!";
    }

    public static String getCompletionMessage(String categoryName) {
        String[] messages = {
            "أكملت " + categoryName + " بنجاح!",
            "واو! أنهيت " + categoryName + "!",
            "رائع! تعلمت " + categoryName + " كلها!",
            "بطل! أكملت " + categoryName + "!"
        };
        return messages[random.nextInt(messages.length)];
    }

    public static String getStreakMessage(int days) {
        if (days >= 7) return "أسبوع كامل! أنت بطل حقيقي! 🏆";
        if (days >= 5) return days + " أيام متتالية! رائع! 🔥";
        if (days >= 3) return days + " أيام متتالية! استمر! ⭐";
        if (days == 2) return "يومان متتاليان! ممتاز!";
        return "يوم جديد للتعلم!";
    }
}
