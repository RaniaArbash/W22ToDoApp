package com.example.todoapp;

public class ToDo {

    String task;
    String date;

    public ToDo(String task, String date) {
        this.task = task;
        this.date = date;
    }


    public String toString(){
        return task +" / "+date;
    }

    public static ToDo fromString(String stringTask){
        ToDo todo = new ToDo("","");
        for (int i = 0; i<stringTask.toCharArray().length; i++){
            if (stringTask.toCharArray()[i] == '/'){
                String task = stringTask.substring(0, i - 1  );
                String date = stringTask.substring(i + 1,stringTask.toCharArray().length );
                todo = new ToDo(task,date);
            }
        }
        return todo;
    }

}
