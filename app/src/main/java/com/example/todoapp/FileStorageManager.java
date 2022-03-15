package com.example.todoapp;

import android.app.Activity;
import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class FileStorageManager {
    static String fileName = "Tasks.txt";
    FileOutputStream fos;
    public void writeNewTasktoFile(Activity context, ToDo task){
         try {
             fos = context.openFileOutput(fileName, Context.MODE_APPEND);
             fos.write((task.toString()+"@").getBytes());
        } catch (FileNotFoundException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         } finally {
             try {
                 fos.close();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }

    }


    public void deleteAllTasks(Activity context){
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(("").getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public ArrayList<ToDo> readAllTodosFromFile(Activity context){
        ArrayList<ToDo> list = new ArrayList<>(0);
        StringBuffer stringBuffer = new StringBuffer();
        try{
        FileInputStream fis = context.openFileInput(fileName);
        InputStreamReader inputStreamReader =
                new InputStreamReader(fis, StandardCharsets.UTF_8);
       int read = 0;
        while ((read =  inputStreamReader.read())!= -1 ){
            stringBuffer.append((char)read);
        }
         list =   fromStringToArrayOfToDo(stringBuffer.toString()) ;
        System.out.println(stringBuffer.toString());
        } catch (IOException e) {
            // Error occurred when opening raw file for reading.
        } finally {

        }
        return list;

    }


    private ArrayList<ToDo> fromStringToArrayOfToDo(String fileContent){
      int index = 0;
      ArrayList<ToDo> listOfDodos = new ArrayList<ToDo>(0);
        for (int i = 0 ; i < fileContent.toCharArray().length; i++){
            if (fileContent.toCharArray()[i] == '@'){
                //"Task1/2022-04-18"
               String oneTask = fileContent.substring(index, i);
               listOfDodos.add(ToDo.fromString(oneTask));
               index = i + 1;
            }
        }
    return listOfDodos;
    }
}
