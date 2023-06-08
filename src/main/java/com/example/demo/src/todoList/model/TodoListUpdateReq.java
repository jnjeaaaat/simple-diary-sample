package com.example.demo.src.todoList.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TodoListUpdateReq {
    private String todoContents;
    private int priority;
    private String todoDate;
    private int budget;
}
