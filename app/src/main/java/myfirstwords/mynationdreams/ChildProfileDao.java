package myfirstwords.mynationdreams;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ChildProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ChildProfile profile);

    @Update
    void update(ChildProfile profile);

    @Query("SELECT * FROM child_profile WHERE id = 1 LIMIT 1")
    ChildProfile getProfile();
}
