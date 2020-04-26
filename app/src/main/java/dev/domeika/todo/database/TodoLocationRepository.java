package dev.domeika.todo.database;

import android.app.Application;
import dev.domeika.todo.models.TodoLocation;

public class TodoLocationRepository {
    private ITodoLocationDao mTodoLocationDao;

    public TodoLocationRepository(Application application) {
        TodoLocationRoomDatabase todoLocationRoomDatabase = TodoLocationRoomDatabase.getDatabase(application);
        mTodoLocationDao = todoLocationRoomDatabase.todoLocationDao();
    }

    public void insert(final TodoLocation todoLocation) {
        TodoRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTodoLocationDao.insert(todoLocation);
            }
        });
    }

    public void delete(final TodoLocation todoLocation) {
        TodoRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTodoLocationDao.delete(todoLocation);
            }
        });
    }
}
