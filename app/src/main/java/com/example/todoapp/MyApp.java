package com.example.todoapp;

import android.app.Application;

public class MyApp extends Application {

    ToDoManager appManager = new ToDoManager();
    FileStorageManager storageManager = new FileStorageManager();
}
