package dev.domeika.todo.ui.main;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import dev.domeika.todo.database.Todo;
import dev.domeika.todo.database.TodoRepository;

public class MainViewModel extends AndroidViewModel {
    private TodoRepository mTodoRepository;
    private LiveData<List<Todo>> liveDataTodos;
    private List<Todo> mTodos = new ArrayList();
    private Todo mTodo;

    public MainViewModel(Application application) {
        super(application);

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

    public List<Todo> getTodos() {
        return mTodos;
    }

    void insert(Todo todo) {
        mTodoRepository.insert(todo);
        mTodos.add(todo);
    }

    public void update(Todo todo) {
        mTodoRepository.update(todo);
    }

    void delete(Todo todo) {
        mTodoRepository.delete(todo);
        mTodos.remove(todo);
    }

    public void index() {
        mTodoRepository.index();
    }

    public void show(Long id) {
        mTodoRepository.show(id);
    }
}
