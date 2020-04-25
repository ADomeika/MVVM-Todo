package dev.domeika.todo.models;

interface ITodo {

    Long getTodoId();

    void setTodoId(Long taskId);

    String getTitle();

    void setTitle(String title);

    String getDescription();

    void setDescription(String description);

    boolean getIsComplete();

    void setIsComplete(boolean isComplete);
}