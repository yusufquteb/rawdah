package myfirstwords.mynationdreams;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsHelper {
    private static final String PREFS_NAME = "rawdah_prefs";
    private static final String KEY_SETUP_DONE = "setup_done";
    private static final String KEY_CHILD_NAME = "child_name";
    private static final String KEY_CHILD_GENDER = "child_gender";
    private static final String KEY_CHILD_AGE = "child_age";
    private static final String KEY_DAILY_GOAL = "daily_goal";
    private static final String KEY_STREAK = "streak_days";
    private static final String KEY_LAST_OPEN_DATE = "last_open_date";
    private static final String KEY_TOTAL_STARS = "total_stars";
    private static final String KEY_SESSION_START = "session_start";

    private final SharedPreferences prefs;

    public PrefsHelper(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public boolean isSetupDone() {
        return prefs.getBoolean(KEY_SETUP_DONE, false);
    }

    public void setSetupDone(boolean done) {
        prefs.edit().putBoolean(KEY_SETUP_DONE, done).apply();
    }

    public void saveChildProfile(String name, String gender, int ageGroup, int dailyGoal) {
        prefs.edit()
            .putString(KEY_CHILD_NAME, name)
            .putString(KEY_CHILD_GENDER, gender)
            .putInt(KEY_CHILD_AGE, ageGroup)
            .putInt(KEY_DAILY_GOAL, dailyGoal)
            .putBoolean(KEY_SETUP_DONE, true)
            .apply();
    }

    public String getChildName() {
        return prefs.getString(KEY_CHILD_NAME, "صديقي");
    }

    public String getChildGender() {
        return prefs.getString(KEY_CHILD_GENDER, "boy");
    }

    public int getChildAge() {
        return prefs.getInt(KEY_CHILD_AGE, 1);
    }

    public int getDailyGoal() {
        return prefs.getInt(KEY_DAILY_GOAL, 10);
    }

    public int getStreakDays() {
        return prefs.getInt(KEY_STREAK, 0);
    }

    public void setStreakDays(int days) {
        prefs.edit().putInt(KEY_STREAK, days).apply();
    }

    public String getLastOpenDate() {
        return prefs.getString(KEY_LAST_OPEN_DATE, "");
    }

    public void setLastOpenDate(String date) {
        prefs.edit().putString(KEY_LAST_OPEN_DATE, date).apply();
    }

    public int getTotalStars() {
        return prefs.getInt(KEY_TOTAL_STARS, 0);
    }

    public void addStars(int stars) {
        int current = getTotalStars();
        prefs.edit().putInt(KEY_TOTAL_STARS, current + stars).apply();
    }

    public void startSession() {
        prefs.edit().putLong(KEY_SESSION_START, System.currentTimeMillis()).apply();
    }

    public long getSessionStartTime() {
        return prefs.getLong(KEY_SESSION_START, System.currentTimeMillis());
    }

    public boolean isBoy() {
        return "boy".equals(getChildGender());
    }
}
