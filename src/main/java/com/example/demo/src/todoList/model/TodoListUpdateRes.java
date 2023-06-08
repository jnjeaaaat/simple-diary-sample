package com.example.demo.src.todoList.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoListUpdateRes {
    private String todoContents;
    private int priority;
    private String todoDate;
    private int budget;
}
