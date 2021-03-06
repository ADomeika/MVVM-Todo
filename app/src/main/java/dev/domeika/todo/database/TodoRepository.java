package dev.domeika.todo.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import dev.domeika.todo.models.Todo;
import dev.domeika.todo.models.TodoLocation;

public class TodoRepository {
    private TodoDao mTodoDao;
    private TodoLocationDao mTodoLocationDao;
    private LiveData<List<Todo>> mLiveDataTodos;

    public TodoRepository(Application application) {
        TodoRoomDatabase todoRoomDatabase = TodoRoomDatabase.getDatabase(application);
        mTodoDao = todoRoomDatabase.getTodoDao();
        mTodoLocationDao = todoRoomDatabase.getTodoLocationDao();

        mLiveDataTodos = mTodoDao.index();
    }

    public LiveData<List<Todo>> getLiveDataTodos() {
        return mLiveDataTodos;
    }

    // Insert Todo
    public void insertTodo(final Todo todo) {
        TodoRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTodoDao.insert(todo);
            }
        });
    }

    // Delete Todo
    public void deleteTodo(final Todo todo) {
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
