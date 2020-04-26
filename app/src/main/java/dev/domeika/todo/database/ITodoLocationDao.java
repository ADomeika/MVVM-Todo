package dev.domeika.todo.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import dev.domeika.todo.models.TodoLocation;

@Dao
public interface ITodoLocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TodoLocation todoLocation);

    @Query("DELETE FROM TodoLocation")
    void destroy();

    @Delete
    void delete(TodoLocation todoLocation);
}
