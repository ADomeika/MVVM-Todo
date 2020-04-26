package dev.domeika.todo.ui.main;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import dev.domeika.todo.database.TodoLocationRepository;
import dev.domeika.todo.database.TodoRepository;
import dev.domeika.todo.models.Todo;
import dev.domeika.todo.models.TodoLocation;

public class MainViewModel extends AndroidViewModel {
    private TodoLocationRepository mTodoLocationRepository;
    private TodoRepository mTodoRepository;
    private LiveData<List<Todo>> liveDataTodos;
    private List<Todo> mTodos = new ArrayList();
    private Todo mTodo;
    private TodoLocation mTodoLocation;

    public MainViewModel(Application application) {
        super(application);

        mTodoLocationRepository = new TodoLocationRepository(application);
        mTodoRepository = new TodoRepository(application);
        liveDataTodos = mTodoRepository.getLiveDataTasks();
    }

    LiveData<List<Todo>> getLiveDataTodos() {
        return liveDataTodos;
    }

    public void setTodo(Todo todo) {
        mTodo = todo;
    }

    public Todo getTodo() {
        return mTodo;
    }

    public void setTodos(List<Todo> todos) {
        mTodos = todos;
    }

    List<Todo> getTodos() {
        return mTodos;
    }

    void insert(Todo todo, TodoLocation todoLocation) {
        mTodoLocationRepository.insert(todoLocation);
        mTodoRepository.insert(todo);
        mTodos.add(todo);
    }

    void update(Todo todo) {
        mTodoRepository.update(todo);
    }

    void delete(Todo todo, TodoLocation todoLocation) {
        mTodoLocationRepository.delete(todoLocation);
        mTodoRepository.delete(todo);
        mTodos.remove(todo);
    }

    public void index() {
        mTodoRepository.index();
    }

    public void show(Long id) {
        mTodoRepository.show(id);
    }

    void setTodoLocation(TodoLocation location) {
        mTodoLocation = location;
    }

    TodoLocation getTodoLocation() {
        return mTodoLocation;
    }
}
