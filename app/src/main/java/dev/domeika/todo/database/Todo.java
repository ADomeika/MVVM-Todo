package dev.domeika.todo.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Todo implements ITodo {
    @ColumnInfo
    @PrimaryKey(autoGenerate = true)
    protected Long id = 1L;

    @ColumnInfo
    private String title;

    @ColumnInfo
    private String description;

    @ColumnInfo
    private boolean isComplete = false;

    public Todo(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Long getTodoId() {
        return id;
    }

    public void setTodoId(Long id) {
        this.id = id;
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
}
