package com.gmardon.todolist;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gmardon on 02/02/18.
 */
public class Task implements Parcelable {
    private int id;
    private String name;
    private String description;
    private String dueDate;
    private boolean isDone;

    public Task(String name, String description, String dueDate, boolean isDone) {
        this.id = 0;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.isDone = isDone;
    }

    public Task(int id, String name, String description, String dueDate, boolean isDone) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.isDone = isDone;
    }

    protected Task(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        dueDate = in.readString();
        isDone = in.readByte() == 1;
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public Task() {

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

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(dueDate);
        dest.writeByte((byte) (isDone ? 1 : 0));
    }
}
