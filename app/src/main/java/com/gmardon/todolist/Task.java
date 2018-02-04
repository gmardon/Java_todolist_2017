package com.gmardon.todolist;

/**
 * Created by gmardon on 02/02/18.
 */

public class Task {
    private int id;
    private String name;
    private String description;
    private String dueDate;

    public Task(String name, String description, String dueDate) {
        this.id = 0;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
    }

    public Task(int id, String name, String description, String dueDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
