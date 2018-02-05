package com.gmardon.todolist;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gmardon on 04/02/2018.
 */
enum TaskModeEnum {
    Create, Edit
}

public class TaskActivity extends AppCompatActivity {
    private Task task;
    private String pageTitle;
    private String actionText;
    private TaskModeEnum mode;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        database = new Database(this);
        ArrayList<Task> tasks = getIntent().getParcelableArrayListExtra("task");
        if (tasks != null && tasks.size() == 1) {
            this.task = tasks.get(0);
            ((TextView) findViewById(R.id.task_id)).setText(String.valueOf(this.task.getId()));
            ((TextView) findViewById(R.id.task_name)).setText(this.task.getName());
            ((TextView) findViewById(R.id.task_description)).setText(this.task.getDescription());
            ((CheckBox) findViewById(R.id.task_is_done)).setChecked(this.task.isDone());
            Log.println(Log.ERROR, "ON_RECEIVE_TASK_FOR_EDITION-IS-DONE?", String.valueOf(this.task.isDone()));

            //((TextView) findViewById(R.id.task_name)).setText(this.task.getName());
            this.mode = TaskModeEnum.Edit;
            this.pageTitle = "Edit task";
            this.actionText = "Edit";
        } else {
            this.pageTitle = "Create task";
            this.actionText = "Create";
            this.mode = TaskModeEnum.Create;
            this.task = new Task();
        }
        this.setTitle(this.pageTitle);
        ((Button) findViewById(R.id.action_task)).setText(this.actionText);
    }

    public void actionTask(View view) {
        View parent = (View) view.getParent();
        switch (this.mode) {
            case Create:
                Task task = new Task(
                        ((TextView) parent.findViewById(R.id.task_name)).getText().toString(),
                        ((TextView) parent.findViewById(R.id.task_description)).getText().toString(),
                        "",
                        ((CheckBox) parent.findViewById(R.id.task_is_done)).isChecked());
                database.insertNewTask(task);
                setResult(Activity.RESULT_OK);
                finish();
                break;
            case Edit:
                this.task.setName(String.valueOf(((TextView) parent.findViewById(R.id.task_name)).getText()));
                this.task.setDescription(String.valueOf(((TextView) parent.findViewById(R.id.task_description)).getText()));
                this.task.setDone(((CheckBox) parent.findViewById(R.id.task_is_done)).isChecked());

                Log.println(Log.ERROR, "AFTER_EDIT-TASK-ACTION-IS-DONE?", String.valueOf(this.task.isDone()));
                //this.task.setDone(String.valueOf(((TextView) parent.findViewById(R.id.task_id)).getText()));
                database.updateTask(this.task);
                setResult(Activity.RESULT_OK);
                finish();
                break;
        }
    }
}
