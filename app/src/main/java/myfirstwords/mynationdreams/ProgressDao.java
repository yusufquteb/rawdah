package myfirstwords.mynationdreams;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ProgressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWord(WordProgress progress);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDaily(DailyActivity activity);

    @Query("SELECT * FROM word_progress WHERE category = :category")
    List<WordProgress> getProgressForCategory(String category);

    @Query("SELECT COUNT(*) FROM word_progress WHERE isLearned = 1")
    int getTotalLearnedWords();

    @Query("SELECT COUNT(*) FROM word_progress WHERE category = :category AND isLearned = 1")
    int getLearnedCountForCategory(String category);

    @Query("SELECT COUNT(*) FROM word_progress WHERE category = :category")
    int getTotalCountForCategory(String category);

    @Query("SELECT * FROM daily_activity WHERE date = :date LIMIT 1")
    DailyActivity getDailyActivity(String date);

    @Query("SELECT * FROM daily_activity ORDER BY date DESC LIMIT 7")
    List<DailyActivity> getLastSevenDays();

    @Query("SELECT * FROM word_progress WHERE wordKey = :key LIMIT 1")
    WordProgress getWordProgress(String key);

    @Query("SELECT COUNT(*) FROM word_progress WHERE isLearned = 1 AND lastSeen > :since")
    int getLearnedTodaySince(long since);
}
