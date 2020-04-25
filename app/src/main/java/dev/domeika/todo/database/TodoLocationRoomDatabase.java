package dev.domeika.todo.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dev.domeika.todo.models.TodoLocation;

@Database(entities = {TodoLocation.class}, version = 1)
public abstract class TodoLocationRoomDatabase extends RoomDatabase {
    private final static String DATABASE_NAME = "todo_location_database";

    abstract ITodoLocationDao todoLocationDao();

    private static volatile TodoLocationRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static TodoLocationRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TodoLocationRoomDatabase.class) {
                INSTANCE = Room
                        .databaseBuilder(context.getApplicationContext(),
                                TodoLocationRoomDatabase.class,
                                DATABASE_NAME)
                        .addCallback(sRoomDatabaseCallback)
                        .build();
            }
        }
        return INSTANCE;
    }

    private static Callback sRoomDatabaseCallback = new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    ITodoLocationDao dao = INSTANCE.todoLocationDao();
                    dao.destroy();
                }
            });
        }
    };
}
