package myfirstwords.mynationdreams;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "child_profile")
public class ChildProfile {
    @PrimaryKey
    public int id = 1;
    public String name = "";
    public String gender = "boy"; // "boy" or "girl"
    public int ageGroup = 1;      // 1=3-4y, 2=5-6y, 3=7-8y
    public int dailyGoal = 10;
    public long createdAt = 0;
}
