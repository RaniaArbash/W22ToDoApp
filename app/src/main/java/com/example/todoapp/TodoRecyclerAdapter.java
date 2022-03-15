package com.example.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
public class TodoRecyclerAdapter extends
        RecyclerView.Adapter<TodoRecyclerAdapter.TodoViewHolder> {
    ArrayList<ToDo> listOfTodos;
    Context context;

    public TodoRecyclerAdapter(ArrayList<ToDo> listOfTodos, Context context) {
        this.listOfTodos = listOfTodos;
        this.context = context;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.todo_row_item, parent,false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        holder.dateText.setText(listOfTodos.get(position).date);
        holder.taskText.setText(listOfTodos.get(position).task);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setBackgroundColor(context.getColor(R.color.black));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfTodos.size();
    }

    public static class TodoViewHolder extends RecyclerView.ViewHolder{
        TextView taskText;
        TextView dateText;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            taskText = itemView.findViewById(R.id.task);
            dateText = itemView.findViewById(R.id.date);


        }

    }





}
