package myfirstwords.mynationdreams;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "word_progress")
public class WordProgress {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String category = "";
    public String wordKey = "";   // e.g., "animals_cat"
    public int timesViewed = 0;
    public long lastSeen = 0;
    public boolean isLearned = false;
}
