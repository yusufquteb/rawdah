package myfirstwords.mynationdreams;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "daily_activity")
public class DailyActivity {
    @PrimaryKey
    @NonNull
    public String date = "";       // "YYYY-MM-DD"
    public int wordsLearned = 0;
    public int minutesSpent = 0;
    public int streakDay = 0;
}
