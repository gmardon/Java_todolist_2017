package com.gmardon.todolist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gmardon on 04/02/2018.
 */
public class TaskListActivity extends AppCompatActivity {
    Database database;
    ArrayAdapter<Task> adapter;
    ListView taskListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        database = new Database(this);
        taskListView = findViewById(R.id.task_list);
        updateTaskList();
    }

    private void updateTaskList() {
        ArrayList<Task> taskList = database.getTaskList();
        if (adapter == null) {
            adapter = new TaskListAdapter(this, R.layout.task_list_entry, taskList);
            taskListView.setAdapter(adapter);
        } else {
            adapter.clear();
            adapter.addAll(taskList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        //Change menu icon color
        Drawable icon = menu.getItem(0).getIcon();
        icon.mutate();
        icon.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                /*
                final EditText taskNameEditText = new EditText(this);
                final EditText taskDescriptionEditText = new EditText(this);
                Context context = this.getApplicationContext();
                LinearLayout layout = new LinearLayout(context);
                layout.addView(taskNameEditText);
                layout.addView(taskDescriptionEditText);

                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add New Task")
                        .setMessage("What do you want to do next?")
                        .setView(layout)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Task task = new Task(
                                        String.valueOf(taskNameEditText.getText()),
                                        String.valueOf(taskDescriptionEditText.getText()),
                                        "due date");
                                database.insertNewTask(task);
                                updateTaskList();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();*/
                Intent intent = new Intent(this, TaskActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        final TextView taskIdView = (TextView) parent.findViewById(R.id.task_id);
        Task task = database.getTaskList().stream()
                .filter((t) -> t.getId() == Integer.parseInt(String.valueOf(taskIdView.getText()))).findFirst().get();
        database.deleteTask(task);
        updateTaskList();
    }

    public void editTask(View view) {
        View parent = (View) view.getParent();
        final TextView taskIdView = (TextView) parent.findViewById(R.id.task_id);
        Task task = database.getTaskList().stream()
                .filter((t) -> t.getId() == Integer.parseInt(String.valueOf(taskIdView.getText()))).findFirst().get();
        Intent intent = new Intent(this, TaskActivity.class);
        intent.putParcelableArrayListExtra("tasks", new ArrayList<Parcelable>() {{
            add(task);
        }});
        startActivity(intent);
    }
}
