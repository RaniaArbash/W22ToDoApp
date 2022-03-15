package com.example.todoapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    RecyclerView table;
    FloatingActionButton addNewTodo;
    ActivityResultLauncher<Intent> addTaskActivityReturnLauncher;
    ToDoManager manager;
    FileStorageManager storageManager;
    TodoRecyclerAdapter adapter;
    int color = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            color = savedInstanceState.getInt("mycolor");
        }
        setContentView(R.layout.activity_main);
        manager = ((MyApp)getApplication()).appManager;
        storageManager = ((MyApp)getApplication()).storageManager;
        manager.listOfTodos = storageManager.readAllTodosFromFile(MainActivity.this);
        addNewTodo = findViewById(R.id.addtask);
        addNewTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addNewTaskIntent = new Intent(getApplicationContext(),AddToDoActivity.class);
                addTaskActivityReturnLauncher.launch(addNewTaskIntent);
            }
        });

        addTaskActivityReturnLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                          if (result.getResultCode() == Activity.RESULT_OK){
                              Bundle bundle = result.getData().getExtras();
                              String t = bundle.getString("task");
                              String d = bundle.getString("date");

                              ToDo newTodo = new ToDo(t,d);
                              // save new todo in the file
                              storageManager.writeNewTasktoFile(MainActivity.this,newTodo);
                              manager.listOfTodos = storageManager.readAllTodosFromFile(MainActivity.this);
                              adapter.listOfTodos = manager.listOfTodos;
                              adapter.notifyDataSetChanged();// refresh the table                              color = 2;
                          }
                    }
                }
        );

        table = findViewById(R.id.recyclerview_todos);
        // get list of todos from the file
        adapter = new TodoRecyclerAdapter(manager.listOfTodos,this);
        table.setAdapter(adapter);
        table.setLayoutManager(new LinearLayoutManager(this));

    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState,
                                    @NonNull PersistableBundle
                                            outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("mycolor",color);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        color = savedInstanceState.getInt("mycolor");
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.todo_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);
         switch (item.getItemId()){
             case R.id.deleteTodos:
                 storageManager.deleteAllTasks(MainActivity.this);
                 manager.listOfTodos = storageManager.readAllTodosFromFile(MainActivity.this);
                 adapter.listOfTodos = manager.listOfTodos;
                 adapter.notifyDataSetChanged();// refresh the table                 break;
         }

         return true;
    }

    private void updateTheList(){
        manager.listOfTodos = storageManager.readAllTodosFromFile(MainActivity.this);
        adapter.notifyDataSetChanged();// refresh the table

    }
}