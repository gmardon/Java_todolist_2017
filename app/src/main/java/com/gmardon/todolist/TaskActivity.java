package com.gmardon.todolist;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by gmardon on 04/02/2018.
 */
public class TaskActivity extends AppCompatActivity {
    private Task task;
    private String pageTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<Task> tasks = getIntent().getParcelableArrayListExtra("task");
        if (tasks != null && tasks.size() == 1) {
            this.task = tasks.get(0);
            this.pageTitle = "Edit task";
        } else {
            this.pageTitle = "Create task";
            this.task = new Task();
        }
    }
}
