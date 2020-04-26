package dev.domeika.todo.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Todo implements ITodo {
    @ColumnInfo
    @PrimaryKey(autoGenerate = true)
    public Long todoId = 1L;

    @ColumnInfo
    @NonNull
    private String title;

    @ColumnInfo
    private String description;

    @ColumnInfo
    private boolean isComplete = false;

    @Embedded
    private TodoLocation location;

    public Todo(String title, String description, TodoLocation location) {
        this.title = title;
        this.description = description;
        this.location = location;
    }

    public Long getTodoId() {
        return todoId;
    }

    public void setTodoId(Long id) {
        this.todoId = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsComplete() {
        return this.isComplete;
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public TodoLocation getLocation() {
        return location;
    }

    public void setLocation(TodoLocation location) {
        this.location = location;
    }
}
