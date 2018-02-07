package com.gmardon.todolist;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
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
    protected void onStart() {
        super.onStart();
        updateTaskList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        database = new Database(this);
        taskListView = findViewById(R.id.task_list);
    }

    private void updateTaskList() {
        ArrayList<Task> taskList = database.getTaskList();
        if (adapter == null) {
            adapter = new TaskListAdapter(database, this, R.layout.task_list_entry, taskList);
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
                Intent intent = new Intent(this, TaskActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView taskIdView = parent.findViewById(R.id.task_list_id);
        Task task = database.getTaskById(Integer.parseInt(String.valueOf(taskIdView.getText())));
        database.deleteTask(task);
        updateTaskList();
    }

    public void editTask(View view) {
        View parent = (View) view.getParent();
        TextView taskIdView = view.findViewById(R.id.task_list_id);
        Task task = database.getTaskById(Integer.parseInt(String.valueOf(taskIdView.getText())));
        Intent intent = new Intent(this, TaskActivity.class);
        intent.putParcelableArrayListExtra("task", new ArrayList<Parcelable>() {{
            add(task);
        }});
        startActivity(intent);
    }
}
