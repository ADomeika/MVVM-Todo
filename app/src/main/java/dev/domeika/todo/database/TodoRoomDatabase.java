package dev.domeika.todo.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dev.domeika.todo.models.Todo;
import dev.domeika.todo.models.TodoLocation;

@Database(entities = {Todo.class, TodoLocation.class}, version = 1)
public abstract class TodoRoomDatabase extends RoomDatabase {
    private final static String DATABASE_NAME = "todo_database";

    abstract TodoDao getTodoDao();
    abstract TodoLocationDao getTodoLocationDao();

    private static volatile TodoRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static TodoRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TodoRoomDatabase.class) {
                INSTANCE = Room
                        .databaseBuilder(context.getApplicationContext(),
                                TodoRoomDatabase.class,
                                DATABASE_NAME)
                        .build();
            }
        }
        return INSTANCE;
    }
}
