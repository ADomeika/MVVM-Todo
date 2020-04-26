package dev.domeika.todo.database;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import dev.domeika.todo.models.Todo;
import dev.domeika.todo.models.TodoLocation;

public class TodoRepository {
    private ITodoDao mTodoDao;
    private ITodoLocationDao mTodoLocationDao;
    private LiveData<List<Todo>> mLiveDataTodos;

    public TodoRepository(Application application) {
        TodoRoomDatabase todoRoomDatabase = TodoRoomDatabase.getDatabase(application);
        mTodoDao = todoRoomDatabase.todoDao();
        mTodoLocationDao = todoRoomDatabase.todoLocationDao();

        mLiveDataTodos = mTodoDao.index();
    }

    public LiveData<List<Todo>> getLiveDataTasks() {
        return mLiveDataTodos;
    }

    // Insert Todo
    public void insert(final Todo todo) {
        TodoRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTodoDao.insert(todo);
            }
        });
    }

    // Delete Todo
    public void delete(final Todo todo) {
        TodoRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTodoDao.delete(todo);
            }
        });
    }

    // Update Todo
    public void update(final Todo todo) {
        TodoRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTodoDao.update(todo);
            }
        });
    }

    // Index Todos
    public void index() {
        TodoRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTodoDao.index();
            }
        });
    }

    // Show Todo
    public void show(final Long id) {
        TodoRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTodoDao.show(id);
            }
        });
    }

    // Insert Location
    public void insert(final TodoLocation todoLocation) {
        TodoRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTodoLocationDao.insert(todoLocation);
            }
        });
    }

    // Delete Location
    public void delete(final TodoLocation todoLocation) {
        TodoRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTodoLocationDao.delete(todoLocation);
            }
        });
    }
}
