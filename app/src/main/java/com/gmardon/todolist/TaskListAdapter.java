package com.gmardon.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gmardon on 04/02/18.
 */

public class TaskListAdapter extends ArrayAdapter<Task> {

    private static class ViewHolder {
        private TextView taskId;
        private CheckBox taskIsDone;
        private TextView taskTitle;
        private TextView taskDescription;
        private TextView taskDueDate;
    }

    private ViewHolder viewHolder;
    private Database database;

    public TaskListAdapter(final Database database, Context context, int textViewResourceId, ArrayList<Task> items) {
        super(context, textViewResourceId, items);
        this.database = database;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.task_list_entry, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.taskId = convertView.findViewById(R.id.task_list_id);
            viewHolder.taskIsDone = convertView.findViewById(R.id.task_list_is_done);
            viewHolder.taskTitle = convertView.findViewById(R.id.task_list_title);
            viewHolder.taskDescription = convertView.findViewById(R.id.task_list_description);
            viewHolder.taskDueDate = convertView.findViewById(R.id.task_list_due_date);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Task item = getItem(position);
        if (item != null) {
            viewHolder.taskId.setText(String.format("%d", item.getId()));
            viewHolder.taskIsDone.setChecked(item.isDone());
            CheckBox taskCheckbox = convertView.findViewById(R.id.task_list_is_done);
            int id = Integer.valueOf(String.valueOf(((TextView) convertView.findViewById(R.id.task_list_id)).getText()));
            taskCheckbox.setOnCheckedChangeListener((view, isChecked) -> {
                Task task = database.getTaskById(id);
                task.setDone(isChecked);
                database.updateTask(task);
            });
            viewHolder.taskTitle.setText(String.format("%s", item.getName()));
            viewHolder.taskDescription.setText(String.format("%s", item.getDescription()));
            viewHolder.taskDueDate.setText(String.format("%s", item.getDueDate()));
        }
        return convertView;
    }
}