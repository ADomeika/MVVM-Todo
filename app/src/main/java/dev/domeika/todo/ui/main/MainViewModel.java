package dev.domeika.todo.ui.main;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import dev.domeika.todo.database.TodoRepository;
import dev.domeika.todo.models.Todo;
import dev.domeika.todo.models.TodoLocation;

public class MainViewModel extends AndroidViewModel {
    private TodoRepository mTodoRepository;
    private LiveData<List<Todo>> liveDataTodos;
    private List<Todo> mTodos = new ArrayList();
    private Todo mTodo;
    private TodoLocation mTodoLocation;

    public MainViewModel(Application application) {
        super(application);

        mTodoRepository = new TodoRepository(application);
        liveDataTodos = mTodoRepository.getLiveDataTodos();
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
        mTodoRepository.insert(todoLocation);
        mTodoRepository.insertTodo(todo);
        mTodos.add(todo);
    }

    void update(Todo todo) {
        mTodoRepository.update(todo);
    }

    void delete(Todo todo, TodoLocation todoLocation) {
        mTodoRepository.delete(todoLocation);
        mTodoRepository.deleteTodo(todo);
        mTodos.remove(todo);
    }

    void setTodoLocation(TodoLocation location) {
        mTodoLocation = location;
    }

    TodoLocation getTodoLocation() {
        return mTodoLocation;
    }
}
