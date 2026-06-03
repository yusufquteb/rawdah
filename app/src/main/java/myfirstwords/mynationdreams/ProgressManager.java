package myfirstwords.mynationdreams;

import android.content.Context;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProgressManager {

    private final AppDatabase db;
    private final PrefsHelper prefs;
    private static final SimpleDateFormat DATE_FMT =
        new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    public ProgressManager(Context context) {
        db = AppDatabase.getInstance(context);
        prefs = new PrefsHelper(context);
    }

    public void recordWordViewed(String category, String wordKey) {
        String key = category + "_" + wordKey;
        WordProgress wp = db.progressDao().getWordProgress(key);
        if (wp == null) {
            wp = new WordProgress();
            wp.category = category;
            wp.wordKey = key;
        }
        wp.timesViewed++;
        wp.lastSeen = System.currentTimeMillis();
        if (wp.timesViewed >= 2) wp.isLearned = true;
        db.progressDao().insertWord(wp);
        updateDailyActivity();
    }

    private void updateDailyActivity() {
        String today = DATE_FMT.format(new Date());
        DailyActivity da = db.progressDao().getDailyActivity(today);
        if (da == null) {
            da = new DailyActivity();
            da.date = today;
            updateStreak(da);
        }
        da.wordsLearned++;
        long startMs = prefs.getSessionStartTime();
        da.minutesSpent = (int) ((System.currentTimeMillis() - startMs) / 60000);
        db.progressDao().insertDaily(da);
    }

    private void updateStreak(DailyActivity today) {
        String lastDate = prefs.getLastOpenDate();
        String todayStr = DATE_FMT.format(new Date());
        if (lastDate.isEmpty()) {
            today.streakDay = 1;
        } else {
            try {
                Date last = DATE_FMT.parse(lastDate);
                Date now = DATE_FMT.parse(todayStr);
                long diffDays = (now.getTime() - last.getTime()) / (1000 * 60 * 60 * 24);
                if (diffDays == 1) {
                    today.streakDay = prefs.getStreakDays() + 1;
                } else if (diffDays > 1) {
                    today.streakDay = 1;
                } else {
                    today.streakDay = prefs.getStreakDays();
                }
            } catch (Exception e) {
                today.streakDay = 1;
            }
        }
        prefs.setStreakDays(today.streakDay);
        prefs.setLastOpenDate(todayStr);
    }

    public int getTotalLearnedWords() {
        return db.progressDao().getTotalLearnedWords();
    }

    public int getStreakDays() {
        return prefs.getStreakDays();
    }

    public int getCategoryStars(String category) {
        int total = db.progressDao().getTotalCountForCategory(category);
        int learned = db.progressDao().getLearnedCountForCategory(category);
        if (total == 0) return 0;
        float pct = (float) learned / total;
        if (pct >= 1.0f) return 3;
        if (pct >= 0.7f) return 2;
        if (pct >= 0.3f) return 1;
        return 0;
    }

    public int getTodayWords() {
        String today = DATE_FMT.format(new Date());
        DailyActivity da = db.progressDao().getDailyActivity(today);
        return da != null ? da.wordsLearned : 0;
    }

    public int getTodayMinutes() {
        String today = DATE_FMT.format(new Date());
        DailyActivity da = db.progressDao().getDailyActivity(today);
        return da != null ? da.minutesSpent : 0;
    }

    public List<DailyActivity> getLastSevenDays() {
        return db.progressDao().getLastSevenDays();
    }
}
