package dev.domeika.todo.database;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import dev.domeika.todo.models.Todo;
import dev.domeika.todo.models.TodoLocation;

public class TodoRepository {
    private ITodoDao mTodoDao;
    private LiveData<List<Todo>> mLiveDataTodos;

    public TodoRepository(Application application) {
        TodoRoomDatabase todoRoomDatabase = TodoRoomDatabase.getDatabase(application);
        mTodoDao = todoRoomDatabase.todoDao();
        mLiveDataTodos = mTodoDao.index();
    }

    public LiveData<List<Todo>> getLiveDataTasks() {
        return mLiveDataTodos;
    }

    public void insert(final Todo todo) {
        TodoRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTodoDao.insert(todo);
            }
        });
    }

    public void delete(final Todo todo) {
        TodoRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTodoDao.delete(todo);
            }
        });
    }

    public void update(final Todo todo) {
        TodoRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTodoDao.update(todo);
            }
        });
    }

    public void index() {
        TodoRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTodoDao.index();
            }
        });
    }

    public void show(final Long id) {
        TodoRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTodoDao.show(id);
            }
        });
    }
}
