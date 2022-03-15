package com.example.todoapp;

import java.util.ArrayList;

public class ToDoManager {
    ArrayList<ToDo> listOfTodos = new ArrayList<>(0);
    public void addNewTodo(ToDo newTask){
        listOfTodos.add(newTask);
    }
}
