package dev.domeika.todo.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ITodoDao {
    @Query("SELECT * FROM Todo ORDER BY id")
    LiveData<List<Todo>> index();

    @Query("SELECT * FROM Todo WHERE id = :id")
    Todo show(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Todo todo);

    @Update
    void update(Todo todo);

    @Delete
    void delete(Todo todo);

    @Query("DELETE FROM Todo")
    void destroy();
}
